package ve312.com.filoseries.domain;

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
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usu_id")
    private Long id;

    @NotEmpty
    @Column(name = "usu_nombre")
    private String username;

    @NotEmpty
    @Column(name = "usu_nombre_completo")
    private String nombre;

    @NotEmpty
    @Column(name = "usu_email")
    private String email;

    @NotEmpty
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Column(name = "usu_password")
    private String password;

    @Column(name = "usu_fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column(name = "usu_estado")
    private String estado;

    @ManyToMany
    @JoinTable(
            name = "usuario_rol",
            joinColumns = @JoinColumn(name = "usu_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private List<Rol> roles;

}
