package microservicios.acopioImport.Controllers;

import microservicios.acopioImport.Entities.AcopioE;
import microservicios.acopioImport.Services.AcopioS;
import microservicios.acopioImport.Services.SubirDataS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/acopio")
public class AcopioC {
    @Autowired
    AcopioS acopioS;
    @Autowired
    SubirDataS subirDataS;

    @PostMapping("/acopio")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes){
        subirDataS.guardar(file);
        attributes.addFlashAttribute("message","Archivo cargado correctamente");
        subirDataS.leerCsv("Acopio.csv");
        return "redirect:/acopio";
    }

    @GetMapping("/fileInformationAcopio")
    public String listar(Model model){
        ArrayList<AcopioE> datas = acopioS.getAcopio();
        model.addAttribute("datas",datas);
        return "fileInformationAcopio";
    }

    @GetMapping("/actual/{codigo}")
    public ResponseEntity<List<AcopioE>> getAcopioActual(@PathVariable String codigo){
        List<AcopioE> acopio = acopioS.ultimaQuincena(codigo);
        if(acopio.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(acopio);
    }
    @GetMapping("/anterior/{codigo}")
    public ResponseEntity<List<AcopioE>> getAcopioAnterior(@PathVariable String codigo){
        List<AcopioE> acopio = acopioS.quincenaAnterior(codigo);
        if(acopio.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(acopio);
    }

}
