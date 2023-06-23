package microservicios.valoresImport.Services;

import microservicios.valoresImport.Entities.PorcentajesE;
import microservicios.valoresImport.Repositories.PorcentajesR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PorcentajesS {
    @Autowired
    PorcentajesR porcentajesR;

    public PorcentajesE obtenerPorCodigoYId(String codigo, Integer id_archivo){
        return porcentajesR.findByProvAndIdAr(codigo, id_archivo);
    }


    public ArrayList<PorcentajesE> getPorcentajes(){
        ArrayList<PorcentajesE> porcentajes = new ArrayList<PorcentajesE>();
        porcentajesR.findAll().forEach(porcentajes::add);
        return porcentajes;
    }

    public int obtenerCantArchivos(ArrayList<PorcentajesE> porcentajes){
        int cant = 0;
        ArrayList<Integer> aux = new ArrayList<>();
        for (PorcentajesE porcentaje:porcentajes){
            if (!(aux.contains(porcentaje.getId_archivo()))){
                aux.add(porcentaje.getId_archivo());
            }
        }

        cant = aux.size();
        return cant;
    }

    public int obtenerSolidoActual(String codigo)
    {
        ArrayList<PorcentajesE> porcentajes = porcentajesR.findAll();
        int id = obtenerCantArchivos(porcentajes);
        PorcentajesE porcentaje_Actual_Filtrado = porcentajesR.findByProvAndIdAr(codigo, id);

        return porcentaje_Actual_Filtrado.getSolidos();
    }

    public int obtenerSolidoAnterior(String codigo)
    {
        ArrayList<PorcentajesE> porcentajes = porcentajesR.findAll();
        int id = obtenerCantArchivos(porcentajes);
        if(id == 1){
            return 0;
        }
        PorcentajesE porcentaje_Actual_Filtrado = porcentajesR.findByProvAndIdAr(codigo, id-1);

        return porcentaje_Actual_Filtrado.getSolidos();
    }

    public int obtenerGrasaActual(String codigo)
    {
        ArrayList<PorcentajesE> porcentajes = porcentajesR.findAll();
        int id = obtenerCantArchivos(porcentajes);
        PorcentajesE porcentaje_Actual_Filtrado = porcentajesR.findByProvAndIdAr(codigo, id);

        return porcentaje_Actual_Filtrado.getGrasas();
    }

    public int obtenerGrasaAnterior(String codigo)
    {
        ArrayList<PorcentajesE> porcentajes = porcentajesR.findAll();
        int id = obtenerCantArchivos(porcentajes);
        if(id == 1){
            return 0;
        }
        PorcentajesE porcentaje_Actual_Filtrado = porcentajesR.findByProvAndIdAr(codigo, id-1);

        return porcentaje_Actual_Filtrado.getGrasas();
    }

    public double bonificacionPorGrasa(PorcentajesE porcentajes){
        double grasas = porcentajes.getGrasas();
        if(grasas >= 46.0){
            return 120.0;
        }else if(grasas >= 21.0) {
            return 80.0;
        }else{
            return 30.0;
        }
    }

    public double bonificacionPorSolidos(PorcentajesE porcentajes){
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

    public double descuentoVariacionGrasa(String proveedor){
        double variacion = variacionGrasa(proveedor);
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

    public Double variacionGrasa(String proveedor){

        ArrayList<PorcentajesE> porcentajes = porcentajesR.findAll();
        int id = obtenerCantArchivos(porcentajes);
        if(id==1){
            return 0.0;
        }
        int cantidadActual = obtenerGrasaActual(proveedor);
        int cantidadAnterior = obtenerGrasaAnterior(proveedor);

        return (double) ((cantidadActual - cantidadAnterior)/cantidadAnterior);
    }

    public Double descuentoVariacionSolidos(String proveedor){
        double variacion = variacionSolidos(proveedor);
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

    public Double variacionSolidos(String proveedor){
        ArrayList<PorcentajesE> porcentajes = porcentajesR.findAll();
        int id = obtenerCantArchivos(porcentajes);
        if(id==1){
            return 0.0;
        }
        int cantidadActual = obtenerSolidoActual(proveedor);
        int cantidadAnterior = obtenerSolidoAnterior(proveedor);

        return (double) ((cantidadActual - cantidadAnterior)/cantidadAnterior);
    }


}
