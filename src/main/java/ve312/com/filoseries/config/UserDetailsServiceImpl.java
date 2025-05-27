package ve312.com.filoseries.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve312.com.filoseries.domain.Rol;
import ve312.com.filoseries.domain.Usuario;
import ve312.com.filoseries.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

@Service("userDetailsService")
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("Error en el login: no existe el usuario '{}' en el sistema", username);
                    return new UsernameNotFoundException("Usuario " + username + " no encontrado");
                });

        // revisa si esta ACTIVO
        if (!"ACTIVO".equals(usuario.getEstado())) {
            log.error("Error en el login: usuario '{}' no está activo. Estado actual: {}",
                    username, usuario.getEstado());
            throw new InactiveUserException("Usuario " + username + " no está activo. Estado: " + usuario.getEstado());
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Rol rol : usuario.getRoles()) {
            String roleName = rol.getNombre();
            if (!roleName.startsWith("ROLE_")) {
                roleName = "ROLE_" + roleName;
            }

            authorities.add(new SimpleGrantedAuthority(roleName));
            log.info("Rol asignado a {}: {}", username, roleName);
        }

        if (authorities.isEmpty()) {
            log.error("Error en el login: usuario '{}' no tiene roles asignados", username);
            throw new UsernameNotFoundException("Error en el login: usuario '" + username + "' no tiene roles asignados");
        }

        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                true,  // activado
                true,  // cuenta no expirada
                true,  // credenciales validads
                true,  // cuanta sin bloqueo
                authorities
        );
    }
}
