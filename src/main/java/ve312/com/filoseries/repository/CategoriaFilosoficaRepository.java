package ve312.com.filoseries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ve312.com.filoseries.domain.CategoriaFilosofica;

import java.util.List;


@Repository
public interface CategoriaFilosoficaRepository extends JpaRepository<CategoriaFilosofica, Long> {
    List<CategoriaFilosofica> findByNombreEsContainingIgnoreCase(String nombre);
    List<CategoriaFilosofica> findByNombreEnContainingIgnoreCase(String nombre);
    List<CategoriaFilosofica> findByNombreDeContainingIgnoreCase(String nombre);
}
