package ve312.com.filoseries.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "serie")
@Schema(description = "Entidad que representa una serie de televisión o película")
public class Serie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ser_id")
    @Schema(description = "Identificador único de la serie", example = "1")
    private Long id;


    @NotEmpty
    @Column(name="ser_titulo_es")
    @Schema(description = "Título de la serie en español", example = "Breaking Bad")
    private String tituloEs;

    @NotEmpty
    @Column(name="ser_titulo_en")
    @Schema(description = "Título de la serie en inglés", example = "Breaking Bad")
    private String tituloEn;

    @NotEmpty
    @Column(name="ser_titulo_de")
    @Schema(description = "Título de la serie en alemán", example = "Breaking Bad")
    private String tituloDe;

    @NotEmpty
    @Column(name="ser_descripcion_es")
    @Schema(description = "Descripción de la serie en español", example = "Un profesor de química con cáncer terminal se convierte en fabricante de metanfetamina...")
    private String descripcionEs;

    @NotEmpty
    @Column(name="ser_descripcion_en")
    @Schema(description = "Descripción de la serie en inglés", example = "A high school chemistry teacher diagnosed with terminal cancer turns to manufacturing methamphetamine...")
    private String descripcionEn;

    @NotEmpty
    @Column(name="ser_descripcion_de")
    @Schema(description = "Descripción de la serie en alemán", example = "Ein Chemielehrer mit Krebs im Endstadium beginnt, Methamphetamin herzustellen...")
    private String descripcionDe;

    @NotEmpty
    @Column(name="ser_imagen_url")
    @Schema(description = "URL de la imagen de portada de la serie", example = "https://example.com/images/breaking-bad.jpg")
    private String imagenUrl;

    @NotNull
    @Column(name="ser_fecha_estreno")
    @Schema(description = "Fecha de estreno de la serie", example = "2008-01-20")
    private LocalDate fecha_estreno;

    @NotEmpty
    @Column(name="ser_tipo")
    @Schema(description = "Tipo de serie (Drama, Crimen, Accion, etc.)", example = "Drama/Crimen")
    private String tipo;

    @OneToMany(mappedBy = "serie",cascade = CascadeType.ALL)
    @Schema(description = "Lista de episodios que componen la serie en el sitio")
    private List<Episodio> episodios;
}
