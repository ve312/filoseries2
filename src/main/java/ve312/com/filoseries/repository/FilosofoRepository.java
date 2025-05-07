package ve312.com.filoseries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ve312.com.filoseries.domain.Filosofo;

import java.util.List;

@Repository
public interface FilosofoRepository extends JpaRepository<Filosofo, Long> {
    List<Filosofo> findByNombreContainingIgnoreCase(String nombre);
}
