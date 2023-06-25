package microservicios.planillaGenerate.Services;

import com.fasterxml.jackson.databind.ObjectMapper;

import microservicios.planillaGenerate.Models.AcopioM;
import microservicios.planillaGenerate.Models.PorcentajesM;
import microservicios.planillaGenerate.Models.ProveedorM;
import microservicios.planillaGenerate.Repositories.PlanillaR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PagoS {
    @Autowired
    PlanillaR planillaR;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    RestTemplate restTemplate;

    public List<AcopioM> obtenerAcopioActual(String codiigo){
        List<AcopioM> acopio = restTemplate.getForObject("http://acopioImport/acopio/actual/"+ codiigo, List.class);
        System.out.println(acopio);
        return acopio;
    }
    public List<AcopioM> obtenerAcopioAnterior(String codiigo){
        List<AcopioM> acopio = restTemplate.getForObject("http://acopioImport/acopio/anterior/"+ codiigo, List.class);
        System.out.println(acopio);
        return acopio;
    }

    public List<PorcentajesM> obtenerPorcentajes(){
        List<PorcentajesM> porcentajes = restTemplate.getForObject("http://valoresImport/porcentajes", List.class);
        System.out.println(porcentajes);
        return porcentajes;
    }

    public PorcentajesM obtenerPorcentaje(String codigo,Integer id_archivo){
        PorcentajesM porcentaje = restTemplate.getForObject("http://valoresImport/porcentajes/"+ codigo
                + "/" + id_archivo, PorcentajesM.class);
        System.out.println(porcentaje);
        return porcentaje;
    }
    public ProveedorM obtenerProveedor(String codigo){
        ProveedorM proveedor = restTemplate.getForObject("http://proveedor/"+ codigo, ProveedorM.class);
        System.out.println(proveedor);
        return proveedor;
    }

    public void calculoPlanilla(String codigo){
        List<AcopioM> acopioActual = obtenerAcopioActual(codigo);
        List<AcopioM> acopioAnterior = obtenerAcopioAnterior(codigo);
        List<PorcentajesM> porcentajes = obtenerPorcentajes();
        Integer porcentajeId = getIdLastArchivo(porcentajes);

        PorcentajesM porcentajesActual = obtenerPorcentaje(codigo,porcentajeId);
        PorcentajesM porcentajesPasada = obtenerPorcentaje(codigo,porcentajeId-1);

        ProveedorM proveedor = obtenerProveedor(codigo);
        double kilos_leche = calculo_leche(acopioActual);
        double promedio = kilos_leche/15 ;
        double pagoPorLeche = obtenerPagoPorKilo(proveedor)*kilos_leche;

        //Variaciones

        double variacionLeche = variacionLeche(acopioActual,acopioAnterior);
        double variacionGrasa = variacionGrasa(porcentajesActual,porcentajesPasada);
        double variacionSolidos = variacionSolidos(porcentajesActual,porcentajesPasada);

        //Descuentos

        Integer dias = obtenerDias(acopioActual);




    }

    public double calculo_leche(List<AcopioM> acopios){
        return acopios.stream().mapToDouble(AcopioM::getKg_leche).sum();
    }

    public double obtenerPagoPorKilo(ProveedorM proveedor) {
        String categoria = proveedor.getCategoria();
        double pagoPorKilo = 0.0;
        if ("A".equals(categoria)) {
            pagoPorKilo = 700.0;
        } else if ("B".equals(categoria)) {
            pagoPorKilo = 550.0;
        } else if ("C".equals(categoria)) {
            pagoPorKilo = 400.0;
        } else if ("D".equals(categoria)) {
            pagoPorKilo = 250.0;
        }
        return pagoPorKilo;
    }

    public Integer getIdLastArchivo(List<PorcentajesM> porcentajes){
        Integer cant = 0;
        ArrayList<Integer> aux = new ArrayList<>();
        for (PorcentajesM porcentaje:porcentajes){
            if (!(aux.contains(porcentaje.getId_archivo()))){
                aux.add(porcentaje.getId_archivo());
            }
        }
        cant = aux.size();
        return cant;
    }

    public double bonificacionPorGrasa(PorcentajesM porcentajes){
        double grasas = porcentajes.getGrasas();
        if(grasas >= 46.0){
            return 120.0;
        }else if(grasas >= 21.0) {
            return 80.0;
        }else{
            return 30.0;
        }
    }

    public double bonificacionPorSolidos(PorcentajesM porcentajes){
        double solidos = porcentajes.getSolidos();
        if(solidos >= 36.0){
            return 150.0;
        }else if(solidos >= 19.0) {
            return 95.0;
        }else if(solidos >= 8.0) {
            return -90.0;
        }else {
            return -130.0;
        }
    }

    public double variacionLeche(List<AcopioM> acopioActual,List<AcopioM> acopioPasada){
        double lecheActual = calculo_leche(acopioActual);
        double lechePasada = calculo_leche(acopioPasada);
        return (lecheActual - lechePasada)/lechePasada;
    }
    public double variacionGrasa(PorcentajesM porcentajesActual,PorcentajesM porcentajesPasada){
        double grasaActual = porcentajesActual.getGrasas();
        double grasaPasada = porcentajesPasada.getGrasas();
        return (grasaActual - grasaPasada)/grasaPasada;
    }
    public double variacionSolidos(PorcentajesM porcentajesActual,PorcentajesM porcentajesPasada){
        double solidosActual = porcentajesActual.getSolidos();
        double solidosPasada = porcentajesPasada.getSolidos();
        return (solidosActual - solidosPasada)/solidosPasada;
    }

    public Double descuento_leche(double variacion){
        if(variacion <= -0.46){
            return 0.3;
        }else if(variacion <= -0.26){
            return 0.15;
        }else if(variacion <= -0.09) {
            return 0.07;
        }else{
            return 0.0;
        }
    }
    public Double descuentoVariacionGrasa(double variacion){
        if(variacion <= -0.41){
            return 0.3;
        }else if(variacion <= -0.26){
            return 0.20;
        }else if(variacion <= -0.16) {
            return 0.12;
        }else{
            return 0.0;
        }
    }

    public Double descuentoVariacionSolidos(double variacion){
        if(variacion <= -0.36){
            return 0.45;
        }else if(variacion <= -0.13){
            return 0.27;
        }else if(variacion <= -0.07) {
            return 0.18;
        }else{
            return 0.0;
        }
    }

    public Integer obtenerDias(List<AcopioM> quincena){
        ArrayList<LocalDate> dias = new ArrayList<>();
        for(AcopioM acopio : quincena){
            dias.add(acopio.getFecha());
        }
        Set<LocalDate> diasUnicos = new HashSet<>(dias);
        return diasUnicos.size();
    }

    public Double pagoFinal(double pago){
        if(pago > 950000){
            return pago - (pago * 0.13);
        }
        return pago;
    }

}
