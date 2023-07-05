package microservicios.valoresImport.Services;

import microservicios.valoresImport.Entities.PorcentajesE;
import microservicios.valoresImport.Repositories.PorcentajesR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@Service
public class SubirDataS {

    @Autowired
    PorcentajesR porcentajesR;

    Integer ID_archivo = 2;

    private final Logger log = LoggerFactory.getLogger(SubirDataS.class);
    public String guardar(MultipartFile file){
        String fileNames = file.getOriginalFilename();
        String mensaje = "";
        try{
            byte [] bytes = file.getBytes();
            Path path = Paths.get(fileNames);
            Files.write(path, bytes);
            mensaje = "Archivo guardado correctamente: " + file.getOriginalFilename();
            log.info(mensaje);
        }catch (Exception e){
            mensaje = "Error al subir el archivo: " + file.getOriginalFilename();
            log.error(mensaje, e.getMessage());
        }
        return mensaje;
    }

    public void leerCsv(String direccion){
        String texto = "";
        BufferedReader bf = null;
        //acopioReposirtory.deleteAll();
        try{
            bf = new BufferedReader(new FileReader(direccion));
            String temp = bf.readLine();
            String bfread;
            while((bfread = bf.readLine()) != null){
                texto = temp;
                String[] datos = bfread.split(";");
                guardarPorcentajesDB(ID_archivo, datos[0], Integer.parseInt(datos[1]), Integer.parseInt(datos[2]));
                temp = temp + "\n" + bfread;
            }
            texto = temp;
            ID_archivo++;
            System.out.println("Archivo leido correctamente: " + texto);
        }catch (Exception e){
            log.error("Error al leer el archivo: " + e.getMessage());
        }finally {
            if(bf != null)
                try{
                    bf.close();
                }catch (Exception e){
                    log.error("Error al cerrar el archivo: " + e.getMessage());
                }
        }
    }

    public void guardarPorcentajesDB(Integer id_archivo, String codigo,int grasas,int solidos){
        PorcentajesE porcentajesEntity = new PorcentajesE();
        porcentajesEntity.setProveedor(codigo);
        porcentajesEntity.setGrasas(grasas);
        porcentajesEntity.setSolidos(solidos);
        porcentajesEntity.setFecha(LocalDate.now());
        porcentajesR.save(porcentajesEntity);
    }
}
