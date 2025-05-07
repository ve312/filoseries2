package ve312.com.filoseries.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "filosofo")

public class Filosofo  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fil_id")
    private Long id;

    @NotEmpty
    @Column(name = "fil_nombre")
    private String nombre;

    @NotEmpty
    @Column(name = "fil_descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "filosofo" , cascade = CascadeType.ALL)
    private List<AnalisisFilosofico> analisis;
}
