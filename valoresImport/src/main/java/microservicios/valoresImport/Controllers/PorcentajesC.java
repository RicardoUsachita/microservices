package microservicios.valoresImport.Controllers;

import microservicios.valoresImport.Entities.PorcentajesE;
import microservicios.valoresImport.Services.PorcentajesS;
import microservicios.valoresImport.Services.SubirDataS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/valoresImport")
public class PorcentajesC {
    @Autowired
    PorcentajesS porcentajesS;
    @Autowired
    SubirDataS subirDataS;



    @PostMapping()
    public ResponseEntity<Boolean> subirPorcentajes(@RequestParam("file") MultipartFile file, RedirectAttributes atributos) {
        subirDataS.guardar(file);
        atributos.addFlashAttribute("message", "Archivo cargado correctamente");
        subirDataS.leerCsv("Porcentajes.csv");
        return ResponseEntity.ok(true);
    }

    @GetMapping("/porcentajes")
    public ResponseEntity<List<PorcentajesE>> listar(Model model) {
        List<PorcentajesE> datas = porcentajesS.getPorcentajes();
        if (datas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(datas);
    }
    @GetMapping("/porcentajesCodigoId/{codigo}/{id}")
    public ResponseEntity<PorcentajesE> porcentajesProveedor(@PathVariable String codigo, @PathVariable int id){
        PorcentajesE porcentaje = porcentajesS.obtenerPorCodigoYId(codigo, id);
        if(porcentaje == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(porcentaje);
    }

    @GetMapping("/bonoGrasa")
    public double bonoGrasa(@RequestParam String codigo){
        ArrayList<PorcentajesE> porcentajes = porcentajesS.getPorcentajes();
        int id = porcentajesS.getIdLastArchivo(porcentajes);
        PorcentajesE porcentaje = porcentajesS.obtenerPorCodigoYId(codigo, id);
        return porcentajesS.bonificacionPorGrasa(porcentaje);
    }

    @GetMapping("/bonoSolido")
    public double bonoSolido(@RequestParam String codigo){
        ArrayList<PorcentajesE> porcentajes = porcentajesS.getPorcentajes();
        int id = porcentajesS.getIdLastArchivo(porcentajes);
        PorcentajesE porcentaje = porcentajesS.obtenerPorCodigoYId(codigo, id);
        return porcentajesS.bonificacionPorSolidos(porcentaje);
    }

    @GetMapping("/descuentoGrasa")
    public Double descuentoGrasa(@RequestParam String codigo){
        return porcentajesS.descuentoVariacionGrasa(codigo);
    }
    @GetMapping("/descuentoSolido")
    public Double descuentoSolido(@RequestParam String codigo){
        return porcentajesS.descuentoVariacionSolidos(codigo);
    }
}
