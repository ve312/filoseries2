package ve312.com.filoseries.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "episodio")
@Schema(description = "Entidad que representa un episodio de una serie")
public class Episodio implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="epi_id")
    @Schema(description = "Identificador único del episodio", example = "1")
    private Long id;

    @NotEmpty
    @Column(name = "epi_titulo_es")
    @Schema(description = "Título del episodio en español", example = "El comienzo")
    private String tituloEs;

    @NotEmpty
    @Column(name = "epi_titulo_en")
    @Schema(description = "Título del episodio en inglés", example = "The Beginning")
    private String tituloEn;

    @NotEmpty
    @Column(name = "epi_titulo_de")
    @Schema(description = "Título del episodio en alemán", example = "Der Anfang")
    private String tituloDe;

    @NotEmpty
    @Column(name = "epi_descripcion_es")
    @Schema(description = "Descripción del episodio en español", example = "En este episodio...")
    private String descripcionEs;

    @NotEmpty
    @Column(name = "epi_descripcion_en")
    @Schema(description = "Descripción del episodio en inglés", example = "In this episode...")
    private String descripcionEn;

    @NotEmpty
    @Column(name = "epi_descripcion_de")
    @Schema(description = "Descripción del episodio en alemán", example = "In dieser Episode...")
    private String descripcionDe;

    @NotNull
    @Min(1)
    @Column(name = "epi_numero_episodio")
    @Schema(description = "Número del episodio dentro de la temporada", example = "1")
    private int numeroEpisodio;

    @NotNull
    @Min(1)
    @Column(name = "epi_temporada")
    @Schema(description = "Número de la temporada a la que pertenece", example = "1")
    private int numeroTemporada;

    @NotEmpty
    @Column(name = "epi_imagen_url")
    @Schema(description = "URL de la imagen de portada del episodio", example = "https://example.com/images/ep1.jpg")
    private String imagenUrl;

    @ManyToOne
    @JoinColumn(name = "ser_id")
    @Schema(description = "Serie a la que pertenece el episodio")
    private Serie serie;

    @OneToMany(mappedBy = "episodio", cascade = CascadeType.ALL)
    @Schema(description = "Lista de análisis filosóficos asociados a este episodio")
    private List<AnalisisFilosofico> analisis;
}
