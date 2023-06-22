package microservicios.acopioImport.Services;

import microservicios.acopioImport.Entities.AcopioE;
import microservicios.acopioImport.Repositories.AcopioR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
}
