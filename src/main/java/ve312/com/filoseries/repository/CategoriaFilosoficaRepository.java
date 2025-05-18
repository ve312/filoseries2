package ve312.com.filoseries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ve312.com.filoseries.domain.CategoriaFilosofica;


@Repository
public interface CategoriaFilosoficaRepository extends JpaRepository<CategoriaFilosofica, Long> {

}
