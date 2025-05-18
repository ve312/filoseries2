package ve312.com.filoseries.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve312.com.filoseries.domain.AnalisisFilosofico;
import ve312.com.filoseries.domain.Comentario;
import ve312.com.filoseries.domain.Usuario;
import ve312.com.filoseries.repository.ComentarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepository comentarioRepository;

    @Autowired
    public ComentarioServiceImpl(ComentarioRepository comentarioRepository) {
        this.comentarioRepository = comentarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comentario> listarComentarios() {
        return comentarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comentario> buscarPorId(Long id) {
        return comentarioRepository.findById(id);
    }

    @Override
    @Transactional
    public Comentario guardar(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        comentarioRepository.deleteById(id);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Comentario> buscarPorUsuario(Usuario usuario) {
        return comentarioRepository.findByUsuario(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comentario> buscarPorAnalisisOrdenadosPorFecha(AnalisisFilosofico analisis) {
        return comentarioRepository.findByAnalisisOrderByFechaCreacionDesc(analisis);
    }
}
