package microservicios.planillaGenerate.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorM {
    public String code;
    public String nombre;
    public String categoria;
    public Integer retencion;
}
