package ve312.com.filoseries.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve312.com.filoseries.domain.Rol;
import ve312.com.filoseries.domain.Usuario;
import ve312.com.filoseries.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolService rolService;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, RolService rolService) {
        this.usuarioRepository = usuarioRepository;
        this.rolService = rolService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }


    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        // Encrypt password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        var passwordEnc = encoder.encode(usuario.getPassword());
        usuario.setPassword(passwordEnc);

        // Set registration date
        usuario.setFechaRegistro(LocalDateTime.now());

        // Set default state to ACTIVO
        usuario.setEstado("ACTIVO");

        // Ensure USER role is assigned
        List<Rol> roles = new ArrayList<>();
        Optional<Rol> userRole = rolService.buscarPorNombre("ROLE_USER");

        if (userRole.isEmpty()) {
            // If ROLE_USER doesn't exist, create it
            Rol newUserRole = new Rol();
            newUserRole.setNombre("ROLE_USER");
            userRole = Optional.of(rolService.guardar(newUserRole));
        }

        roles.add(userRole.get());
        usuario.setRoles(roles);

        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    //

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> listarUsuariosNoAdmin(Pageable pageable) {
        // Obtener todos los usuarios
        Page<Usuario> todosUsuarios = usuarioRepository.findAll(pageable);

        // Filtrar aquellos que no tienen rol ADMIN
        List<Usuario> usuariosNoAdmin = todosUsuarios.getContent().stream()
                .filter(u -> !tieneRolAdmin(u))
                .collect(Collectors.toList());

        return new PageImpl<>(usuariosNoAdmin, pageable,
                usuarioRepository.countByRolNoAdmin());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Usuario> buscarUsuarioPorIdNoAdmin(Long id, Pageable pageable) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isPresent() && !tieneRolAdmin(usuarioOpt.get())) {
            List<Usuario> listaUsuario = new ArrayList<>();
            listaUsuario.add(usuarioOpt.get());
            return new PageImpl<>(listaUsuario, pageable, 1);
        }

        return new PageImpl<>(Collections.emptyList(), pageable, 0);
    }

    @Override
    @Transactional
    public void cambiarEstadoUsuario(Long id) throws Exception {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        // Verificar que no sea un administrador
        if (tieneRolAdmin(usuario)) {
            throw new Exception("No se puede modificar el estado de un administrador");
        }

        // Cambiar estado (ACTIVO a INACTIVO o viceversa)
        if ("ACTIVO".equals(usuario.getEstado())) {
            usuario.setEstado("INACTIVO");
        } else {
            usuario.setEstado("ACTIVO");
        }

        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminarUsuario(Long id) throws Exception {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        // Verificar que no sea un administrador
        if (tieneRolAdmin(usuario)) {
            throw new Exception("No se puede eliminar un administrador");
        }

        usuarioRepository.delete(usuario);
    }

    private boolean tieneRolAdmin(Usuario usuario) {
        return usuario.getRoles().stream()
                .anyMatch(rol -> "ADMIN".equals(rol.getNombre()));
    }


}
