package ve312.com.filoseries.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ve312.com.filoseries.service.AnalisisFilosoficoService;
import ve312.com.filoseries.service.CategoriaFilosoficaService;
import ve312.com.filoseries.service.EpisodioService;
import ve312.com.filoseries.service.SerieService;

@Controller
@Tag(name = "Home", description = "Controlador para la página principal y funcionalidades generales")
public class HomeController {
    private final SerieService serieService;
    private final AnalisisFilosoficoService analisisService;
    private final CategoriaFilosoficaService categoriaFilosoficaService;
    private final EpisodioService episodioService;

    @Autowired
    public HomeController(SerieService serieService, AnalisisFilosoficoService analisisService, CategoriaFilosoficaService categoriaFilosoficaService, EpisodioService episodioService) {
        this.serieService = serieService;
        this.analisisService = analisisService;
        this.categoriaFilosoficaService = categoriaFilosoficaService;
        this.episodioService = episodioService;
    }

    @GetMapping("/")
    @Operation(summary = "Página principal",
            description = "Muestra la página principal con series destacadas, últimos análisis y categorías filosóficas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página principal mostrada correctamente")
    })
    public String home(Model model) {
        // Obtener series para mostrar en la página principal
        model.addAttribute("series", serieService.listarSeries());

        // Obtener los últimos análisis para mostrar en la página principal
        model.addAttribute("ultimosAnalisis", analisisService.buscarUltimosAnalisis(5));

        var categorias = categoriaFilosoficaService.listarCategorias();
        model.addAttribute("categorias", categorias);

        model.addAttribute("serieSer", serieService);
        model.addAttribute("analisisSer", analisisService);
        model.addAttribute("categoriaSer", categoriaFilosoficaService);
        model.addAttribute("episodioSer", episodioService);

        return "home";
    }

    @GetMapping("/cambiar-idioma")
    @Operation(summary = "Cambiar idioma de la aplicación",
            description = "Cambia el idioma de la aplicación y redirige a la página principal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Redirige a la página principal con el nuevo idioma")
    })
    public String cambiarIdioma(@RequestParam String lang, @RequestHeader(value = "referer", required = false) String referer) {
        // El cambio de idioma lo maneja Spring automáticamente con LocaleChangeInterceptor
        if (referer != null) {
            return "redirect:" + referer;
        }
        return "redirect:/";
    }
}
