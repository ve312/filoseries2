package ve312.com.filoseries.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "categoria_filosofica")
public class CategoriaFilosofica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cat_id")
    private Long id;

    @NotEmpty
    @Column(name = "cat_nombre_es")
    private String nombreEs;

    @NotEmpty
    @Column(name = "cat_nombre_en")
    private String nombreEn;

    @NotEmpty
    @Column(name = "cat_nombre_de")
    private String nombreDe;

    @OneToMany(mappedBy = "categoria" , cascade = CascadeType.ALL)
    private List<AnalisisFilosofico> analisis;
}
