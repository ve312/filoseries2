package ve312.com.filoseries.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ve312.com.filoseries.domain.Comentario;
import ve312.com.filoseries.service.ComentarioService;
import ve312.com.filoseries.util.MessageUtil;

import java.util.Optional;

@Controller
@RequestMapping("/mod")
@PreAuthorize("hasAnyRole('ROLE_MOD', 'ROLE_ADMIN')")
public class ModeradorController {
    private final ComentarioService comentarioService;
    private final MessageUtil messageUtil;

    @Autowired
    public ModeradorController(ComentarioService comentarioService, MessageUtil messageUtil) {
        this.comentarioService = comentarioService;
        this.messageUtil = messageUtil;
    }

    @GetMapping("/comentarios")
    public String listarComentariosPendientes(Model model) {
        // Obtener todos los comentarios
        model.addAttribute("comentarios", comentarioService.listarComentarios());
        return "mod/comentarios";
    }

    @PostMapping("/comentarios/{id}/moderar")
    public String moderarComentario(@PathVariable Long id,
                                    @RequestParam String estado,
                                    RedirectAttributes redirectAttributes) {

        Optional<Comentario> comentarioOpt = comentarioService.buscarPorId(id);

        if (comentarioOpt.isPresent()) {
            Comentario comentario = comentarioOpt.get();
            comentario.setEstado(estado);
            comentarioService.guardar(comentario);

            String mensajeKey = switch (estado) {
                case "APROBADO" -> "mod.comentario.aprobado";
                case "RECHAZADO" -> "mod.comentario.rechazado";
                default -> "mod.comentario.pendiente";
            };

            redirectAttributes.addFlashAttribute("mensaje", messageUtil.getMessage(mensajeKey));
        }

        return "redirect:/mod/comentarios";
    }
}
