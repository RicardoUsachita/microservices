package microservicios.proveedores.Repositories;

import microservicios.proveedores.Entities.ProveedorE;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorR extends CrudRepository<ProveedorE, Long>{
@Query("SELECT p FROM ProveedorE p WHERE p.code = :code")
    ProveedorE findByCode(@Param("code") String code);



}
