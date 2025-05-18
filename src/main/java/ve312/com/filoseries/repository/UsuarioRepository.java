package ve312.com.filoseries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ve312.com.filoseries.domain.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    @Query("SELECT COUNT(u) FROM Usuario u WHERE NOT EXISTS (SELECT r FROM u.roles r WHERE r.nombre = 'ADMIN')")
    long countByRolNoAdmin();

}
