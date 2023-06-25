package microservicios.planillaGenerate.Controllers;

import microservicios.planillaGenerate.Entities.PlanillaE;
import microservicios.planillaGenerate.Services.PagoS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/planilla")
public class PlanillaC {
    @Autowired
    PagoS pagoS;
    @GetMapping("/crearPlanilla")
    public ResponseEntity<PlanillaE> generarPlanilla(Model model, @RequestParam("codigo") String codigo){
        PlanillaE planilla = pagoS.calculoPlanilla(codigo);
        if (planilla == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(planilla);
    }
}
