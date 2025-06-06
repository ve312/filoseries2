package ve312.com.filoseries.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ve312.com.filoseries.domain.Usuario;
import ve312.com.filoseries.service.UsuarioService;

import javax.sql.DataSource;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Tag(name = "Administración", description = "Controlador para la gestión administrativa de usuarios")
public class AdminController {
    private final UsuarioService usuarioService;
    private final ResourceLoader resourceLoader;
    private final DataSource dataSource;

    //private final MessageUtil messageUtil;

    @Autowired
    public AdminController(UsuarioService usuarioService, ResourceLoader resourceLoader, DataSource dataSource) {
        this.usuarioService = usuarioService;
        this.resourceLoader = resourceLoader;
        this.dataSource = dataSource;
    }

    @GetMapping("/usuarios")
    @Operation(summary = "Listar usuarios",
            description = "Muestra la lista de usuarios no administradores con paginación y búsqueda por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios mostrada correctamente"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado para usuarios sin rol ADMIN")
    })
    public String listarUsuarios(Model model,
                                 @RequestParam(required = false) Long buscarId,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {

        Page<Usuario> usuariosPage;

        if (buscarId != null) {
            // Buscar usuario por ID (excluyendo admins)
            usuariosPage = usuarioService.buscarUsuarioPorIdNoAdmin(buscarId, PageRequest.of(page, size));
        } else {
            // Listar todos los usuarios que no son admin
            usuariosPage = usuarioService.listarUsuariosNoAdmin(PageRequest.of(page, size));
        }

        model.addAttribute("usuariosPage", usuariosPage);
        model.addAttribute("currentPage", page);
        return "admin/usuarios";
    }

    @PostMapping("/usuarios/{id}/toggle-estado")
    @Operation(summary = "Cambiar estado de un usuario",
            description = "Activa o desactiva un usuario según su estado actual")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Estado actualizado correctamente, redirige a la lista de usuarios"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado para usuarios sin rol ADMIN")
    })
    public String toggleEstadoUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.cambiarEstadoUsuario(id);
            redirectAttributes.addFlashAttribute("mensaje", "Estado del usuario actualizado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el estado: " + e.getMessage());
        }
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/usuarios/{id}/eliminar")
    @Operation(summary = "Eliminar un usuario",
            description = "Elimina permanentemente un usuario del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Usuario eliminado correctamente, redirige a la lista de usuarios"),
            @ApiResponse(responseCode = "403", description = "Acceso denegado para usuarios sin rol ADMIN")
    })
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.eliminarUsuario(id);
            redirectAttributes.addFlashAttribute("mensaje", "Usuario eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el usuario: " + e.getMessage());
        }
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/usuarios/reporte")
    public void generarReporteUsuarios(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"reporte_usuarios.pdf\"");

        // Cargar el archivo jrxml
        JasperReport report = JasperCompileManager.compileReport(
                resourceLoader.getResource("classpath:reporte_usuarios.jrxml").getInputStream()
        );

        // Parámetros opcionales
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("TITULO", "Reporte de Usuarios");

        try (Connection connection = dataSource.getConnection()) {
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, connection);

            OutputStream out = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al generar el reporte: " + e.getMessage());
        }
    }


}
