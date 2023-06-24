package microservicios.planillaGenerate.Entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "planilla")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanillaE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    public Integer ID;
    public String fecha;
    public String codigo_Proveedor;
    public String nombre_Proveedor;
    private String kls_leche;
    private String dias;
    private String promedio;
    private String variacion_leche;
    private String grasa;
    private String variacion_grasas;
    private String solidos;
    private String variacion_solidos;
    private String pago_leche;
    private String pago_grasa;
    private String pago_solidos;
    private String bonificacion_frecuencia;
    private String descuento_leche;
    private String descuento_grasa;
    private String descuento_solidos;
    private String pago_total;
    private String monto_retencion;
    private String pago_final;
}
