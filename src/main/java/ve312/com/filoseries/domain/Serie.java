package ve312.com.filoseries.domain;

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
public class Serie implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ser_id")
    private Long id;


    @NotEmpty
    @Column(name="ser_titulo_es")
    private String titulo_es;

    @NotEmpty
    @Column(name="ser_titulo_en")
    private String titulo_en;

    @NotEmpty
    @Column(name="ser_titulo_de")
    private String titulo_de;

    @NotEmpty
    @Column(name="ser_descripcion_es")
    private String descripcion_es;

    @NotEmpty
    @Column(name="ser_descripcion_en")
    private String descripcion_en;

    @NotEmpty
    @Column(name="ser_descripcion_de")
    private String descripcion_de;

    @NotEmpty
    @Column(name="ser_imagen_url")
    private String imagenUrl;

    @NotNull
    @Column(name="ser_fecha_estreno")
    private LocalDate fecha_estreno;

    @NotEmpty
    @Column(name="ser_tipo")
    private String tipo;

    @OneToMany(mappedBy = "serie",cascade = CascadeType.ALL)
    private List<Episodio> episodios;
}
