package ve312.com.filoseries.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "usuario")
@Schema(description = "Entidad que representa un usuario del sistema")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usu_id")
    @Schema(description = "Identificador único del usuario", example = "1")
    private Long id;

    @NotEmpty
    @Column(name = "usu_nombre")
    @Schema(description = "Nombre de usuario para iniciar sesión", example = "johndoe")
    private String username;

    @NotEmpty
    @Column(name = "usu_nombre_completo")
    @Schema(description = "Nombre completo del usuario", example = "John Doe")
    private String nombre;

    @NotEmpty
    @Column(name = "usu_email")
    @Schema(description = "Correo electrónico del usuario", example = "john.doe@example.com")
    private String email;

    @NotEmpty
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Column(name = "usu_password")
    @Schema(description = "Contraseña del usuario (encriptada)", example = "********")
    private String password;

    @Column(name = "usu_fecha_registro")
    @Schema(description = "Fecha y hora de registro del usuario", example = "2025-05-17T14:30:00")
    private LocalDateTime fechaRegistro;

    @Column(name = "usu_estado")
    @Schema(description = "Estado actual del usuario (ACTIVO, INACTIVO, BLOQUEADO, etc.)", example = "ACTIVO")
    private String estado;

    @ManyToMany
    @JoinTable(
            name = "usuario_rol",
            joinColumns = @JoinColumn(name = "usu_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    @Schema(description = "Roles asignados al usuario")
    private List<Rol> roles;

}
