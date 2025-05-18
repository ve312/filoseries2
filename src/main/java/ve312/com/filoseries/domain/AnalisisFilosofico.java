package ve312.com.filoseries.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "analisis_filosofico")
@Schema(description = "Entidad que representa un análisis filosófico de un episodio")
public class AnalisisFilosofico implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ana_id")
    @Schema(description = "Identificador único del análisis filosófico", example = "1")
    private Long id;

    @NotEmpty
    @Column(name = "ana_contenido_es")
    @Schema(description = "Contenido del análisis en español", example = "Este episodio explora conceptos existencialistas...")
    private String contenidoEs;

    @NotEmpty
    @Column(name = "ana_contenido_en")
    @Schema(description = "Contenido del análisis en inglés", example = "This episode explores existentialist concepts...")
    private String contenidoEn;

    @NotEmpty
    @Column(name = "ana_contenido_de")
    @Schema(description = "Contenido del análisis en alemán", example = "Diese Episode erforscht existentialistische Konzepte...")
    private String contenidoDe;

    @NotEmpty
    @Column(name = "ana_titulo_es")
    @Schema(description = "Título del análisis en español", example = "Existencialismo en la serie")
    private String tituloEs;

    @NotEmpty
    @Column(name = "ana_titulo_en")
    @Schema(description = "Título del análisis en inglés", example = "Existentialism in the series")
    private String tituloEn;

    @NotEmpty
    @Column(name = "ana_titulo_de")
    @Schema(description = "Título del análisis en alemán", example = "Existentialismus in der Serie")
    private String tituloDe;

    @NotNull
    @Column(name = "ana_fecha_publicacion")
    @Schema(description = "Fecha de publicación del análisis", example = "2025-05-17")
    private LocalDate fechaPublicacion;

    @ManyToOne
    @JoinColumn(name = "fil_id")
    @Schema(description = "Filósofo asociado al análisis")
    private Filosofo filosofo;

    @ManyToOne
    @JoinColumn(name = "cat_id")
    @Schema(description = "Categoría filosófica del análisis")
    private CategoriaFilosofica categoria;

    @ManyToOne
    @JoinColumn(name = "epi_id")
    @Schema(description = "Episodio analizado")
    private Episodio episodio;

    @OneToMany(mappedBy = "analisis", cascade = CascadeType.ALL)
    @Schema(description = "Lista de comentarios sobre este análisis")
    private List<Comentario> comentarios;

}
