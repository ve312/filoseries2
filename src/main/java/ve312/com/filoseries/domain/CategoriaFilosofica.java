package ve312.com.filoseries.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "categoria_filosofica")
@Schema(description = "Entidad que representa una categoría filosófica")
public class CategoriaFilosofica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id")
    @Schema(description = "Identificador único de la categoría", example = "1")
    private Long id;

    @NotEmpty
    @Column(name = "cat_nombre_es")
    @Schema(description = "Nombre de la categoría en español", example = "Ética")
    private String nombreEs;

    @NotEmpty
    @Column(name = "cat_nombre_en")
    @Schema(description = "Nombre de la categoría en inglés", example = "Ethics")
    private String nombreEn;

    @NotEmpty
    @Column(name = "cat_nombre_de")
    @Schema(description = "Nombre de la categoría en alemán", example = "Ethik")
    private String nombreDe;

    @OneToMany(mappedBy = "categoria" , cascade = CascadeType.ALL)
    @Schema(description = "Lista de análisis filosóficos asociados a esta categoría")
    private List<AnalisisFilosofico> analisis;
}
