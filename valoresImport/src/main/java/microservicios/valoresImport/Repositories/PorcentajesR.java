package microservicios.valoresImport.Repositories;

import microservicios.valoresImport.Entities.PorcentajesE;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PorcentajesR extends CrudRepository<PorcentajesE, Integer> {
    PorcentajesE findByProveedorAndFecha(String proveedor, LocalDate fecha);
    PorcentajesE findByProveedor(String proveedor);
}
