package ve312.com.filoseries.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ve312.com.filoseries.domain.AnalisisFilosofico;
import ve312.com.filoseries.domain.Comentario;
import ve312.com.filoseries.service.*;
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
    private final CategoriaFilosoficaService categoriaFilosoficaService;

    @Autowired
    public AnalisisFilosoficoController(AnalisisFilosoficoService analisisService,
                                        ComentarioService comentarioService,
                                        UsuarioService usuarioService,
                                        SerieService serieService,
                                        EpisodioService episodioService,
                                        CategoriaFilosoficaService categoriaFilosoficaService
                                        ) {
        this.analisisService = analisisService;
        this.comentarioService = comentarioService;
        this.usuarioService = usuarioService;
        this.serieService = serieService;
        this.episodioService = episodioService;
        this.categoriaFilosoficaService = categoriaFilosoficaService;
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


            // Añadir los servicios necesarios al modelo
            model.addAttribute("analisisSer", analisisService);
            model.addAttribute("serieSer", serieService);
            model.addAttribute("episodioSer", episodioService);
            model.addAttribute("categoriaSer", categoriaFilosoficaService);


            return "detalle/analisis";
        } else {
            return "redirect:/series";
        }
    }

}
