package microservicios.planillaGenerate.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcopioM {
    public Integer id_quincena;
    public LocalDate fecha;
    public String turno;
    public String proveedor;
    public Double kg_leche;
}
