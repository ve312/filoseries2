package ve312.com.filoseries.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comentario")
public class Comentario  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "com_id")
    private Long id;

    @NotEmpty
    @Column(name = "com_contenido")
    private String Contenido;

    @NotNull
    @Column(name = "com_fecha_creacion")
    private LocalDateTime fechaCreacion;

    @NotEmpty
    @Column(name = "com_estado")
    private String estado;

    @Version
    @Column(name = "com_version")
    private Integer version;

    @ManyToOne
    @JoinColumn(name = "usu_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ana_id")
    private AnalisisFilosofico analisis;
}
