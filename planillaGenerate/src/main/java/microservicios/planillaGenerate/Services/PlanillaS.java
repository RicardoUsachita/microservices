package microservicios.planillaGenerate.Services;

import microservicios.planillaGenerate.Entities.PlanillaE;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PlanillaS {
    public PlanillaE crearPlanilla(LocalDate fecha, String codigo, String nombre
            , Double kilos_leche, Integer dias, Double promedio, Double variacion_leche,
                                   Integer grasa, Double var_grasa, Integer solidos, Double var_solidos, Double pago_leche,
                                   Double pago_grasa, Double pago_solidos, Double bonificacion, Double desc_leche,
                                   Double desc_grasa, Double desc_solidos, Double pago_total, Double retencion, Double pago_final){
        PlanillaE planilla = new PlanillaE();
        planilla.setFecha(fecha.toString());
        planilla.setCodigo_Proveedor(codigo);
        planilla.setNombre_Proveedor(nombre);
        planilla.setKls_leche(kilos_leche.toString());
        planilla.setDias(dias.toString());
        planilla.setPromedio(promedio.toString());
        planilla.setVariacion_leche(variacion_leche.toString());
        planilla.setGrasa(grasa.toString());
        planilla.setVariacion_grasas(var_grasa.toString());
        planilla.setSolidos(solidos.toString());
        planilla.setVariacion_solidos(var_solidos.toString());
        planilla.setPago_leche(pago_leche.toString());
        planilla.setPago_grasa(pago_grasa.toString());
        planilla.setPago_solidos(pago_solidos.toString());
        planilla.setBonificacion_frecuencia(bonificacion.toString());
        planilla.setDescuento_leche(desc_leche.toString());
        planilla.setDescuento_grasa(desc_grasa.toString());
        planilla.setDescuento_solidos(desc_solidos.toString());
        planilla.setPago_total(pago_total.toString());
        planilla.setMonto_retencion(retencion.toString());
        planilla.setPago_final(pago_final.toString());

        return planilla;
    }

}
