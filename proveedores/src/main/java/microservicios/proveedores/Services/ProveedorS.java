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

    public double obtenerPagoPorKilo(ProveedorE proveedor) {
        String categoria = proveedor.getCategoria();
        double pagoPorKilo = 0.0;
        if ("A".equals(categoria)) {
            pagoPorKilo = 700.0;
        } else if ("B".equals(categoria)) {
            pagoPorKilo = 550.0;
        } else if ("C".equals(categoria)) {
            pagoPorKilo = 400.0;
        } else if ("D".equals(categoria)) {
            pagoPorKilo = 250.0;
        }
        return pagoPorKilo;
    }


}
