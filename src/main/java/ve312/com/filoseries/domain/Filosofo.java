package ve312.com.filoseries.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "filosofo")
@Schema(description = "Entidad que representa un filósofo")
public class Filosofo  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fil_id")
    @Schema(description = "Identificador único del filósofo", example = "1")
    private Long id;

    @NotEmpty
    @Column(name = "fil_nombre")
    @Schema(description = "Nombre completo del filósofo", example = "Friedrich Nietzsche")
    private String nombre;

    @NotEmpty
    @Column(name = "fil_descripcion")
    @Schema(description = "Descripción biográfica del filósofo", example = "Filósofo alemán del siglo XIX conocido por sus críticas a la religión y la moralidad tradicional...")
    private String descripcion;

    @OneToMany(mappedBy = "filosofo" , cascade = CascadeType.ALL)
    @Schema(description = "Lista de análisis que se basaron en este filósofo")
    private List<AnalisisFilosofico> analisis;
}
