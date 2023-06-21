package microservicios.acopioImport.Services;

import microservicios.acopioImport.Repositories.AcopioR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;


@Service
public class SubirDataS {
    @Autowired
    private AcopioR acopiorepo;


    private AcopioS acopioS;

    private Integer ID_quincena_actual = 1;
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

                String[] datos = bfread.split(";");
                acopioS.guardarAcopioDB(ID_quincena_actual, datos[0], datos[1], datos[2], Double.parseDouble(datos[3]));
                temp = temp + "\n" + bfread;

            }
            texto= temp;
            System.out.println("Achivo leido correctamente: " + texto);
        }catch (Exception e){
            log.error("Error al leer el archivo: " + e.getMessage());
        }finally{
            if(bf != null)
                try{
                    bf.close();
                }catch (Exception e){
                    log.error("Error al cerrar el archivo: " + e.getMessage());
                }
        }
    }

}
