package microservicios.proveedores.Controllers;

import microservicios.proveedores.Entities.ProveedorE;
import microservicios.proveedores.Services.ProveedorS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
