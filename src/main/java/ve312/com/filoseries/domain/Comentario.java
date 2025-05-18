package ve312.com.filoseries.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comentario")
@Schema(description = "Entidad que representa un comentario de usuario sobre un análisis filosófico")
public class Comentario  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "com_id")
    @Schema(description = "Identificador único del comentario", example = "1")
    private Long id;

    @NotEmpty
    @Column(name = "com_contenido")
    @Schema(description = "Contenido del comentario", example = "Este análisis me parece muy interesante...")
    private String Contenido;

    @NotNull
    @Column(name = "com_fecha_creacion")
    @Schema(description = "Fecha y hora de creación del comentario", example = "2025-05-17T10:30:00")
    private LocalDateTime fechaCreacion;

    @NotEmpty
    @Column(name = "com_estado")
    @Schema(description = "Estado actual del comentario", example = "PENDIENTE")
    private String estado;

    @Version
    @Column(name = "com_version")
    @Schema(description = "Versión para control de concurrencia", example = "1")
    private Integer version;

    @ManyToOne
    @JoinColumn(name = "usu_id")
    @Schema(description = "Usuario que realizó el comentario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ana_id")
    @Schema(description = "Análisis filosófico al que pertenece el comentario")
    private AnalisisFilosofico analisis;
}
