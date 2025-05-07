package ve312.com.filoseries.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @Column(name = "usu_email")
    private String email;

    @NotEmpty
    @Column(name = "usu_password")
    private String password;

    @NotNull
    @Column(name = "usu_fecha_registro")
    private LocalDateTime fechaRegistro;

    @NotEmpty
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
