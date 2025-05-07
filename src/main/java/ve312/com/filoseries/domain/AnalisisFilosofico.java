package ve312.com.filoseries.domain;

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
public class AnalisisFilosofico implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ana_id")
    private Long id;

    @NotEmpty
    @Column(name = "ana_contenido_es")
    private String contenido_es;

    @NotEmpty
    @Column(name = "ana_contenido_en")
    private String contenido_en;

    @NotEmpty
    @Column(name = "ana_contenido_de")
    private String contenido_de;

    @NotEmpty
    @Column(name = "ana_titulo_es")
    private String titulo_es;

    @NotEmpty
    @Column(name = "ana_titulo_en")
    private String titulo_en;

    @NotEmpty
    @Column(name = "ana_titulo_de")
    private String titulo_de;

    @NotNull
    @Column(name = "ana_fecha_publicacion")
    private LocalDate fechaPublicacion;

    @ManyToOne
    @JoinColumn(name = "fil_id")
    private Filosofo filosofo;

    @ManyToOne
    @JoinColumn(name = "cat_id")
    private CategoriaFilosofica categoria;

    @ManyToOne
    @JoinColumn(name = "epi_id")
    private Episodio episodio;

    @OneToMany(mappedBy = "analisis", cascade = CascadeType.ALL)
    private List<Comentario> comentarios;

}
