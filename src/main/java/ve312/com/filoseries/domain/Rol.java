package ve312.com.filoseries.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "rol")
@Schema(description = "Entidad que representa un rol de usuario en el sistema")
public class Rol implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id")
    @Schema(description = "Identificador único del rol", example = "1")
    private long id;

    @NotEmpty
    @Column(name = "rol_nombre")
    @Schema(description = "Nombre del rol", example = "ADMIN")
    private String nombre;

    @ManyToMany(mappedBy = "roles")
    @Schema(description = "Lista de usuarios que tienen este rol")
    private List<Usuario> usuarios;
}
