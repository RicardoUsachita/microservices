package microservicios.planillaGenerate.Services;

import com.fasterxml.jackson.databind.ObjectMapper;

import microservicios.planillaGenerate.Entities.PlanillaE;
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
    PlanillaS planillaS;

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
        ProveedorM proveedor = restTemplate.getForObject("http://proveedor-service/"+ codigo, ProveedorM.class);
        System.out.println(proveedor);
        return proveedor;
    }

    public PlanillaE calculoPlanilla(String codigo){
        List<AcopioM> acopioActual = obtenerAcopioActual(codigo);
        List<AcopioM> acopioAnterior = obtenerAcopioAnterior(codigo);
        List<PorcentajesM> porcentajes = obtenerPorcentajes();
        Integer porcentajeId = getIdLastArchivo(porcentajes);

        PorcentajesM porcentajesActual = obtenerPorcentaje(codigo,porcentajeId);
        PorcentajesM porcentajesPasada = obtenerPorcentaje(codigo,porcentajeId-1);

        ProveedorM proveedor = obtenerProveedor(codigo);
        Double kilos_leche = calculo_leche(acopioActual);
        Double promedio = kilos_leche/15 ;
        Integer dias = obtenerDias(acopioActual);

        //Pagos
        Double pagoPorLeche = obtenerPagoPorKilo(proveedor)*kilos_leche;
        Double pagoPorGrasa = bonificacionPorGrasa(porcentajesActual);
        Double pagoPorSolidos = bonificacionPorSolidos(porcentajesActual);

        Double sumaPagos = pagoPorLeche + pagoPorGrasa + pagoPorSolidos;
        Double bonificacionFrecuencia = calcularBonificacion(acopioActual)*sumaPagos;

        //Variaciones

        Double variacionLeche = variacionLeche(acopioActual,acopioAnterior);
        Double variacionGrasa = variacionGrasa(porcentajesActual,porcentajesPasada);
        Double variacionSolidos = variacionSolidos(porcentajesActual,porcentajesPasada);

        //Descuentos

        Double descuentoLeche = descuento_leche(variacionLeche)*sumaPagos;
        Double descuentoGrasa = descuentoGrasa(variacionGrasa)*sumaPagos;
        Double descuentoSolidos = descuentoSolidos(variacionSolidos)*sumaPagos;

        Double pagoTotal = sumaPagos + bonificacionFrecuencia - descuentoLeche - descuentoGrasa - descuentoSolidos;
        Double pagoFinal;

        if(proveedor.getRetencion() == 1){
            pagoFinal = pagoFinal(pagoTotal);
        }else {
            pagoFinal = pagoTotal;
        }
        double retencion = pagoFinal - pagoTotal;

        PlanillaE planilla = planillaS.crearPlanilla(LocalDate.now(), codigo, proveedor.nombre, kilos_leche, dias, promedio, variacionLeche,
                porcentajesActual.grasas, variacionGrasa, porcentajesActual.solidos, variacionSolidos, pagoPorLeche, pagoPorGrasa,
                pagoPorSolidos, bonificacionFrecuencia, descuentoLeche, descuentoGrasa, descuentoSolidos, pagoTotal, retencion, pagoFinal);

        planillaR.save(planilla);
        return planilla;
    }

    public Double calculo_leche(List<AcopioM> acopios){
        return acopios.stream().mapToDouble(AcopioM::getKg_leche).sum();
    }

    public Double obtenerPagoPorKilo(ProveedorM proveedor) {
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

    public Double calcularBonificacion(List<AcopioM> acopiosQuincenales) {

        int enviosManana = 0;
        int enviosTarde = 0;

        for (AcopioM acopio : acopiosQuincenales) {
            if (acopio.getTurno().equals("M")) {
                enviosManana++;
            } else if (acopio.getTurno().equals("T")) {
                enviosTarde++;
            }
        }

        if (enviosManana > 10 && enviosTarde > 10) {
            return 0.2; // Bonificación del 20%
        } else if (enviosManana > 10) {
            return 0.12; // Bonificación del 12%
        } else if (enviosTarde > 10) {
            return 0.08; // Bonificación del 8%
        } else {
            return 0.0; // Sin bonificación
        }
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

    public Double bonificacionPorGrasa(PorcentajesM porcentajes){
        Integer grasas = porcentajes.getGrasas();
        if(grasas >= 46.0){
            return 120.0;
        }else if(grasas >= 21.0) {
            return 80.0;
        }else{
            return 30.0;
        }
    }

    public Double bonificacionPorSolidos(PorcentajesM porcentajes){
        Integer solidos = porcentajes.getSolidos();
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

    public Double variacionLeche(List<AcopioM> acopioActual,List<AcopioM> acopioPasada){
        Double lecheActual = calculo_leche(acopioActual);
        Double lechePasada = calculo_leche(acopioPasada);
        return (lecheActual - lechePasada)/lechePasada;
    }
    public Double variacionGrasa(PorcentajesM porcentajesActual,PorcentajesM porcentajesPasada){
        double grasaActual = porcentajesActual.getGrasas();
        double grasaPasada = porcentajesPasada.getGrasas();
        return (grasaActual - grasaPasada)/grasaPasada;
    }
    public Double variacionSolidos(PorcentajesM porcentajesActual,PorcentajesM porcentajesPasada){
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
    public Double descuentoGrasa(double variacion){
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

    public Double descuentoSolidos(double variacion){
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
