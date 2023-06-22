package microservicios.valoresImport.Controllers;

import microservicios.valoresImport.Entities.PorcentajesE;
import microservicios.valoresImport.Services.PorcentajesS;
import microservicios.valoresImport.Services.SubirDataS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;

@Controller
@RequestMapping
public class PorcentajesC {
    @Autowired
    PorcentajesS porcentajesS;
    @Autowired
    SubirDataS subirDataS;

    @GetMapping("/porcentajes")
    public String porcentajes() {
        return "porcentajes";
    }

    @PostMapping("/porcentajes")
    public String subirPorcentajes(@RequestParam("file") MultipartFile file, RedirectAttributes atributos) {
        subirDataS.guardar(file);
        atributos.addFlashAttribute("message", "Archivo cargado correctamente");
        subirDataS.leerCsv("Porcentajes.csv");
        return "redirect:/porcentajes";
    }

    @GetMapping("/fileInformationPorcentaje")
    public String listar(Model model) {
        ArrayList<PorcentajesE> datas = porcentajesS.getPorcentajes();
        model.addAttribute("datas", datas);
        return "fileInformationPorcentajes";
    }
}
