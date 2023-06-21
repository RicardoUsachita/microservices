package microservicios.proveedores.Services;

import microservicios.proveedores.Entities.ProveedorE;
import microservicios.proveedores.Repositories.ProveedorR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorS {
    @Autowired
    ProveedorR proveedorRepo;

    public List<ProveedorE> obtenerProveedores(){
        return (List<ProveedorE>) proveedorRepo.findAll();
    }

    public void guardarProveedor(ProveedorE proveedor){
        proveedorRepo.save(proveedor);
    }

    public ProveedorE obtenerPorCodigo(String codigo){
        return proveedorRepo.findByCode(codigo);
    }
    public void borrarTodos(){
        proveedorRepo.deleteAll();
    }
}
