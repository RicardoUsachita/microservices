package microservicios.valoresImport.Services;

import microservicios.valoresImport.Entities.PorcentajesE;
import microservicios.valoresImport.Repositories.PorcentajesR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class PorcentajesS {
    @Autowired
    PorcentajesR porcentajesR;

    public PorcentajesE obtenerPorCodigoYFecha(String codigo, LocalDate fecha){
        return porcentajesR.findByProveedorAndFecha(codigo, fecha);
    }
    public ArrayList<PorcentajesE> getPorcentajes(){
        ArrayList<PorcentajesE> porcentajes = new ArrayList<PorcentajesE>();
        porcentajesR.findAll().forEach(porcentajes::add);
        return porcentajes;
    }
}
