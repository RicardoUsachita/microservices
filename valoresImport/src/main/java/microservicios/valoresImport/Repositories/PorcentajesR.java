package microservicios.valoresImport.Repositories;

import microservicios.valoresImport.Entities.PorcentajesE;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public interface PorcentajesR extends CrudRepository<PorcentajesE, Integer> {
    PorcentajesE findByProveedorAndFecha(String proveedor, LocalDate fecha);
    PorcentajesE findByProveedor(String proveedor);

    ArrayList<PorcentajesE> findAll();

    @Query("select e from PorcentajesE e where e.proveedor = :codigo and e.id_archivo = :id_archivo")
    PorcentajesE findByProvAndIdAr(String codigo, Integer id_archivo);
}
