package ve312.com.filoseries.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ve312.com.filoseries.domain.AnalisisFilosofico;
import ve312.com.filoseries.domain.Comentario;
import ve312.com.filoseries.domain.Usuario;
import ve312.com.filoseries.service.*;
import ve312.com.filoseries.util.MessageUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/analisis")
@Tag(name = "Análisis Filosófico", description = "Controlador para la gestión de análisis filosóficos y sus comentarios")
public class AnalisisFilosoficoController {

    private final AnalisisFilosoficoService analisisService;
    private final ComentarioService comentarioService;
    private final UsuarioService usuarioService;
    private final SerieService serieService;
    private final EpisodioService episodioService;

    @Autowired
    public AnalisisFilosoficoController(AnalisisFilosoficoService analisisService,
                                        ComentarioService comentarioService,
                                        UsuarioService usuarioService,
                                        SerieService serieService,
                                        EpisodioService episodioService
                                        ) {
        this.analisisService = analisisService;
        this.comentarioService = comentarioService;
        this.usuarioService = usuarioService;
        this.serieService = serieService;
        this.episodioService = episodioService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ver detalle de un análisis filosófico",
            description = "Muestra la página de detalle de un análisis filosófico con sus comentarios aprobados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Análisis encontrado y mostrado correctamente"),
            @ApiResponse(responseCode = "302", description = "Análisis no encontrado, redirige a la página de series")
    })
    public String verDetalleAnalisis(@PathVariable Long id, Model model,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        Optional<AnalisisFilosofico> analisisOpt = analisisService.buscarPorId(id);

        if (analisisOpt.isPresent()) {
            AnalisisFilosofico analisis = analisisOpt.get();
            model.addAttribute("analisis", analisis);

            // Obtener comentarios aprobados para este análisis
            List<Comentario> comentariosAprobados = comentarioService.buscarPorAnalisisOrdenadosPorFecha(analisis)
                    .stream()
                    .filter(c -> "APROBADO".equals(c.getEstado()))
                    .toList();

            model.addAttribute("comentarios", comentariosAprobados);
            model.addAttribute("nuevoComentario", new Comentario());

            // Añadir los servicios necesarios al modelo
            model.addAttribute("analisisSer", analisisService);
            model.addAttribute("serieSer", serieService);
            model.addAttribute("episodioSer", episodioService);

            /*
            // Si el usuario está autenticado, obtener información adicional
            if (userDetails != null) {
                Optional<Usuario> usuarioOpt = usuarioService.buscarPorUsername(userDetails.getUsername());
                if (usuarioOpt.isPresent()) {
                    Usuario usuario = usuarioOpt.get();

                    // Obtener los comentarios pendientes del usuario actual para este análisis
                    List<Comentario> comentariosPendientes = comentarioService.buscarPorUsuario(usuario)
                            .stream()
                            .filter(c -> c.getAnalisis().getId().equals(id) && "PENDIENTE".equals(c.getEstado()))
                            .toList();

                    // Obtener los comentarios rechazados del usuario actual para este análisis
                    List<Comentario> comentariosRechazados = comentarioService.buscarPorUsuario(usuario)
                            .stream()
                            .filter(c -> c.getAnalisis().getId().equals(id) && "RECHAZADO".equals(c.getEstado()))
                            .toList();

                    model.addAttribute("comentariosPendientes", comentariosPendientes);
                    model.addAttribute("comentariosRechazados", comentariosRechazados);
                    model.addAttribute("usuarioActual", usuario);

                    // Verificar si el usuario es MOD o ADMIN para mostrar panel de moderación
                    boolean isModerador = userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MOD")) ||
                            userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

                    if (isModerador) {
                        // Obtener todos los comentarios pendientes para moderación
                        List<Comentario> comentariosParaModerar = comentarioService.buscarPorAnalisisOrdenadosPorFecha(analisis)
                                .stream()
                                .filter(c -> "PENDIENTE".equals(c.getEstado()))
                                .toList();

                        model.addAttribute("comentariosModeracion", comentariosParaModerar);
                    }
                }
            }
            */

            return "detalle/analisis";
        } else {
            return "redirect:/series";
        }
    }

    /*
    @PostMapping("/{id}/comentar")
    public String agregarComentario(@PathVariable Long id,
                                    @ModelAttribute("nuevoComentario") Comentario comentario,
                                    @AuthenticationPrincipal UserDetails userDetails) {

        Optional<AnalisisFilosofico> analisisOpt = analisisService.buscarPorId(id);
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorUsername(userDetails.getUsername());

        if (analisisOpt.isPresent() && usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            // Verificar si el usuario está activo
            if (!"ACTIVO".equals(usuario.getEstado())) {
                return "redirect:/acceso-denegado";
            }

            AnalisisFilosofico analisis = analisisOpt.get();
            comentario.setAnalisis(analisis);
            comentario.setUsuario(usuario);
            comentario.setFechaCreacion(LocalDateTime.now());
            comentario.setEstado("PENDIENTE"); // Todos los comentarios inician en estado pendiente

            comentarioService.guardar(comentario);
        }

        return "redirect:/analisis/" + id + "?comentarioEnviado=true";
    }

    // Endpoint para que moderadores/administradores cambien el estado de los comentarios
    @PostMapping("/{analisisId}/comentarios/{comentarioId}/estado")
    public String cambiarEstadoComentario(@PathVariable Long analisisId,
                                          @PathVariable Long comentarioId,
                                          @RequestParam String nuevoEstado,
                                          @AuthenticationPrincipal UserDetails userDetails) {

        // Verificar que el usuario tenga rol MOD o ADMIN
        boolean isModerador = userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MOD")) ||
                userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        if (!isModerador) {
            return "redirect:/acceso-denegado";
        }

        // Validar que el nuevo estado sea válido
        if (!("APROBADO".equals(nuevoEstado) || "PENDIENTE".equals(nuevoEstado) || "RECHAZADO".equals(nuevoEstado))) {
            return "redirect:/analisis/" + analisisId;
        }

        Optional<Comentario> comentarioOpt = comentarioService.buscarPorId(comentarioId);
        if (comentarioOpt.isPresent()) {
            Comentario comentario = comentarioOpt.get();
            comentario.setEstado(nuevoEstado);
            comentarioService.guardar(comentario);
        }

        return "redirect:/analisis/" + analisisId + "?estadoActualizado=true";
    }
     */

}
