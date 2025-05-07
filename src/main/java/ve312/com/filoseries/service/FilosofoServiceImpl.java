package ve312.com.filoseries.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve312.com.filoseries.domain.Filosofo;
import ve312.com.filoseries.repository.FilosofoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FilosofoServiceImpl implements FilosofoService {

    private final FilosofoRepository filosofoRepository;

    @Autowired
    public FilosofoServiceImpl(FilosofoRepository filosofoRepository) {
        this.filosofoRepository = filosofoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Filosofo> listarFilosofos() {
        return filosofoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Filosofo> buscarPorId(Long id) {
        return filosofoRepository.findById(id);
    }

    @Override
    @Transactional
    public Filosofo guardar(Filosofo filosofo) {
        return filosofoRepository.save(filosofo);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        filosofoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Filosofo> buscarPorNombre(String nombre) {
        return filosofoRepository.findByNombreContainingIgnoreCase(nombre);
    }
}
