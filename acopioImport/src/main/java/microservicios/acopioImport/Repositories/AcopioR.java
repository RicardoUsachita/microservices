package microservicios.acopioImport.Repositories;

import microservicios.acopioImport.Entities.AcopioE;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AcopioR extends CrudRepository<AcopioE, Integer> {
    @Query("SELECT a FROM AcopioE a WHERE a.fecha BETWEEN :inicio AND :fin")
    List<AcopioE> findAcopiosBetweenDates(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);

    @Query("SELECT c FROM AcopioE c WHERE c.id_quincena = (SELECT MAX(c.id_quincena) FROM AcopioE)")
    List<AcopioE> ultimaQuincena();

    @Query("SELECT a FROM AcopioE a")
    List<AcopioE> findAll();

    @Query("SELECT a FROM AcopioE a WHERE a.proveedor = :codigo and a.id_quincena = :id_archivo ")
    List<AcopioE> findByProvAndIdAr(@Param("codigo") String codigo, @Param("id_archivo") Integer id_archivo);

}
