package microservicios.acopioImport.Services;

import microservicios.acopioImport.Entities.AcopioE;
import microservicios.acopioImport.Repositories.AcopioR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AcopioS {

    @Autowired
    AcopioR acopioR;




    public void guardarAcopioBD(AcopioE acopio){
        acopioR.save(acopio);
    }

    public void guardarAcopioDB(Integer id_quincena, String fecha, String turno, String codigo, Double kg_leche){
        AcopioE acopio = new AcopioE();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        acopio.setId_quincena(id_quincena);
        acopio.setFecha(LocalDate.parse(fecha, formatter));
        acopio.setTurno(turno);
        acopio.setProveedor(codigo);
        acopio.setKg_leche(kg_leche);

        acopioR.save(acopio);
    }

    public ArrayList<AcopioE> getAcopio(){
        ArrayList<AcopioE> acopio = new ArrayList<>();
        acopioR.findAll().forEach(acopio::add);
        return acopio;
    }
    public int getIdLastFile(List<AcopioE> acopios){
        int cant = 0;
        ArrayList<Integer> aux = new ArrayList<>();
        for (AcopioE acopio:acopios){
            if (!(aux.contains(acopio.getId_quincena()))){
                aux.add(acopio.getId_quincena());
            }
        }

        cant = aux.size();
        return cant;
    }
    public double calcularBonificacion(List<AcopioE> acopiosQuincenales) {

        int enviosManana = 0;
        int enviosTarde = 0;

        for (AcopioE acopio : acopiosQuincenales) {
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

    public List<AcopioE> ultimaQuincena(String codigo){
        List<AcopioE> acopios = acopioR.findAll();
        int id = getIdLastFile(acopios);
        return acopioR.findByProvAndIdAr(codigo, id);
    }
    public List<AcopioE> quincenaAnterior(String codigo){

        List<AcopioE> acopios = acopioR.findAll();
        int id = getIdLastFile(acopios);
        if(id == 1){
            return null;
        }
        return acopioR.findByProvAndIdAr(codigo, id-1);
    }
    public double calculo_leche(String proveedor){
        List<AcopioE> acopios = ultimaQuincena(proveedor);
        return acopios.stream().mapToDouble(AcopioE::getKg_leche).sum();
    }
    public double descuento_leche(String proveedor){
        double variacion = variacionLeche(proveedor);
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
    public double variacionLeche(String proveedor){
        List<AcopioE> acopios = ultimaQuincena(proveedor);
        List<AcopioE> acopiosAnt = quincenaAnterior(proveedor);
        if(acopiosAnt == null){
            return 0.0;
        }
        double leche = acopios.stream().mapToDouble(AcopioE::getKg_leche).sum();
        double lecheAnt = acopiosAnt.stream().mapToDouble(AcopioE::getKg_leche).sum();
        return (leche - lecheAnt)/lecheAnt;
    }


}
