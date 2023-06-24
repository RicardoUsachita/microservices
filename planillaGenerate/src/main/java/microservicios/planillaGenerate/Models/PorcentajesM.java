package microservicios.planillaGenerate.Models;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PorcentajesM {
    public Integer id_archivo;
    public String proveedor;
    public Integer grasas;
    public Integer solidos;
    public LocalDate fecha;

}
