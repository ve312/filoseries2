package ve312.com.filoseries.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ve312.com.filoseries.domain.AnalisisFilosofico;
import ve312.com.filoseries.domain.Comentario;
import ve312.com.filoseries.domain.Usuario;
import ve312.com.filoseries.service.AnalisisFilosoficoService;
import ve312.com.filoseries.service.ComentarioService;
import ve312.com.filoseries.service.UsuarioService;
import ve312.com.filoseries.util.MessageUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/analisis")
public class AnalisisFilosoficoController {

    private final AnalisisFilosoficoService analisisService;
    private final ComentarioService comentarioService;
    private final UsuarioService usuarioService;
    private final MessageUtil messageUtil;

    @Autowired
    public AnalisisFilosoficoController(AnalisisFilosoficoService analisisService,
                                        ComentarioService comentarioService,
                                        UsuarioService usuarioService,
                                        MessageUtil messageUtil) {
        this.analisisService = analisisService;
        this.comentarioService = comentarioService;
        this.usuarioService = usuarioService;
        this.messageUtil = messageUtil;
    }

    @GetMapping("/{id}")
    public String verDetalleAnalisis(@PathVariable Long id, Model model) {
        Optional<AnalisisFilosofico> analisis = analisisService.buscarPorId(id);

        if (analisis.isPresent()) {
            model.addAttribute("analisis", analisis.get());

            // Obtener comentarios aprobados para este análisis
            List<Comentario> comentarios = comentarioService.buscarPorAnalisisOrdenadosPorFecha(analisis.get())
                    .stream()
                    .filter(c -> "APROBADO".equals(c.getEstado()))
                    .toList();

            model.addAttribute("comentarios", comentarios);
            model.addAttribute("nuevoComentario", new Comentario());

            return "analisis/detalle";
        } else {
            return "redirect:/series";
        }
    }

    @PostMapping("/{id}/comentar")
    public String agregarComentario(@PathVariable Long id,
                                    @ModelAttribute("nuevoComentario") Comentario comentario,
                                    @AuthenticationPrincipal UserDetails userDetails) {

        Optional<AnalisisFilosofico> analisis = analisisService.buscarPorId(id);
        Optional<Usuario> usuario = usuarioService.buscarPorUsername(userDetails.getUsername());

        if (analisis.isPresent() && usuario.isPresent()) {
            // Verificar si el usuario está activo
            if (!"ACTIVO".equals(usuario.get().getEstado())) {
                return "redirect:/acceso-denegado";
            }

            comentario.setAnalisis(analisis.get());
            comentario.setUsuario(usuario.get());
            comentario.setFechaCreacion(LocalDateTime.now());
            comentario.setEstado("PENDIENTE"); // Todos los comentarios inician en estado pendiente

            comentarioService.guardar(comentario);
        }

        return "redirect:/analisis/" + id;
    }
}
