package ve312.com.filoseries.domain;

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
public class Episodio implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="epi_id")
    private Long id;

    @NotEmpty
    @Column(name = "epi_titulo_es")
    private String tituloEs;

    @NotEmpty
    @Column(name = "epi_titulo_en")
    private String tituloEn;

    @NotEmpty
    @Column(name = "epi_titulo_de")
    private String tituloDe;

    @NotEmpty
    @Column(name = "epi_descripcion_es")
    private String descripcionEs;

    @NotEmpty
    @Column(name = "epi_descripcion_en")
    private String descripcionEn;

    @NotEmpty
    @Column(name = "epi_descripcion_de")
    private String descripcionDe;

    @NotNull
    @Min(1)
    @Column(name = "epi_numero_episodio")
    private int numeroEpisodio;

    @NotNull
    @Min(1)
    @Column(name = "epi_temporada")
    private int numeroTemporada;

    @NotEmpty
    @Column(name = "epi_imagen_url")
    private String imagenUrl;

    @ManyToOne
    @JoinColumn(name = "ser_id")
    private Serie serie;

    @OneToMany(mappedBy = "episodio", cascade = CascadeType.ALL)
    private List<AnalisisFilosofico> analisis;
}
