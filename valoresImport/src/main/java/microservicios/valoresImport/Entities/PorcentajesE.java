package microservicios.valoresImport.Entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "porcentajes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PorcentajesE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer ID;

    public Integer id_archivo;
    public String proveedor;
    public Integer grasas;
    public Integer solidos;
    public LocalDate fecha;

}
