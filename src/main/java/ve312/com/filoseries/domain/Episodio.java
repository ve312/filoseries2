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
    private String titulo_es;

    @NotEmpty
    @Column(name = "epi_titulo_en")
    private String titulo_en;

    @NotEmpty
    @Column(name = "epi_titulo_de")
    private String titulo_de;

    @NotEmpty
    @Column(name = "epi_descripcion_es")
    private String descripcion_es;

    @NotEmpty
    @Column(name = "epi_descripcion_en")
    private String descripcion_en;

    @NotEmpty
    @Column(name = "epi_descripcion_de")
    private String descripcion_de;

    @NotNull
    @Min(1)
    @Column(name = "epi_numero_episodio")
    private int numero_episodio;

    @NotNull
    @Min(1)
    @Column(name = "epi_temporada")
    private int numero_temporada;

    @NotEmpty
    @Column(name = "epi_imagen_url")
    private String imagenUrl;

    @ManyToOne
    @JoinColumn(name = "ser_id")
    private Serie serie;

    @OneToMany(mappedBy = "episodio", cascade = CascadeType.ALL)
    private List<AnalisisFilosofico> analisis;
}
