package microservicios.proveedores.Controllers;

import microservicios.proveedores.Entities.ProveedorE;
import microservicios.proveedores.Services.ProveedorS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedor")
public class ProveedorC {
    @Autowired
    ProveedorS proveedorS;

    @GetMapping("/{code}")
    public ResponseEntity<ProveedorE> obtenerProveedor(@PathVariable String code){
        ProveedorE proveedor = proveedorS.obtenerPorCodigo(code);
        if (proveedor == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(proveedor);
    }

    @GetMapping("/proveedores")
    public ResponseEntity<List<ProveedorE>> obtenerProveedores(){
        List<ProveedorE> proveedores = proveedorS.obtenerProveedores();
        if (proveedores.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(proveedores);
    }
    @PostMapping("/nuevo-proveedor")
    public ResponseEntity<Boolean> nuevoProveedor(@RequestParam("codigo") String codigo,
                                 @RequestParam("nombre") String nombre,
                                 @RequestParam("categoria") String categoria,
                                 @RequestParam("retencion") Integer retencion){

        ProveedorE proveedor = new ProveedorE();
        proveedor.setCode(codigo);
        proveedor.setNombre(nombre);
        proveedor.setCategoria(categoria);
        proveedor.setRetencion(retencion);
        proveedorS.guardarProveedor(proveedor);
        return ResponseEntity.ok(true);
    }
}
