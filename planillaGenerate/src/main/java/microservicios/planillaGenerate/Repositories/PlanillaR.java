package microservicios.planillaGenerate.Repositories;

import microservicios.planillaGenerate.Entities.PlanillaE;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanillaR extends CrudRepository<PlanillaE, Integer> {
}
