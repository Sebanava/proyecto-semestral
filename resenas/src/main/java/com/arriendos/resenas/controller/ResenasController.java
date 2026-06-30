package com.arriendos.resenas.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.arriendos.resenas.Resenas;
import com.arriendos.resenas.model.ResenasModel;
import com.arriendos.resenas.service.ResenasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/resenas")
@Tag(name = "Reseñas", description = "Gestión de reseñas y calificaciones de películas por clientes")
public class ResenasController {

    @Autowired
    private ResenasService service;

    @Operation(summary = "Obtener todas las reseñas", description = "Retorna todas las reseñas registradas en el sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de reseñas retornada exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<Resenas>> ObtenerTodo() {
        return ResponseEntity.ok(service.ObtenerTodo());
    }

    @Operation(summary = "Obtener reseña por ID", description = "Busca una reseña específica por su ID. Retorna 404 si no existe.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reseña encontrada"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Resenas> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @Operation(summary = "Obtener reseñas por título de película", description = "Retorna todas las reseñas de una película buscando por título exacto. Retorna 404 si no hay reseñas.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reseñas encontradas para ese título"),
        @ApiResponse(responseCode = "404", description = "No hay reseñas para ese título")
    })
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<Resenas>> ObtenerPorTitulo(@PathVariable String titulo) {
        return ResponseEntity.ok(service.ObtenerPorTitulo(titulo));
    }

    @Operation(summary = "Crear nueva reseña", description = "Crea una reseña para una película. Valida que la película exista en el catálogo. Calificación entre 0 y 10. Solo accesible con rol CLIENTE.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Reseña creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos (calificación fuera de rango o campos vacíos)"),
        @ApiResponse(responseCode = "403", description = "No autorizado - se requiere rol CLIENTE"),
        @ApiResponse(responseCode = "404", description = "Película no encontrada en el catálogo"),
        @ApiResponse(responseCode = "503", description = "Servicio de Películas no disponible")
    })
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody ResenasModel model, @RequestParam String rol) {
        if (!rol.equals("CLIENTE")) {
            return ResponseEntity.status(403).body("Solo un CLIENTE puede crear reseñas");
        }
        return ResponseEntity.status(201).body(service.guardar(model));
    }

    @Operation(summary = "Actualizar reseña", description = "Actualiza el contenido de una reseña existente. Solo accesible con rol ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reseña actualizada exitosamente"),
        @ApiResponse(responseCode = "403", description = "No autorizado - se requiere rol ADMIN"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody ResenasModel model, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede actualizar reseñas");
        }
        return ResponseEntity.ok(service.actualizar(id, model));
    }

    @Operation(summary = "Eliminar reseña", description = "Elimina una reseña del sistema. Solo accesible con rol ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reseña eliminada exitosamente"),
        @ApiResponse(responseCode = "403", description = "No autorizado - se requiere rol ADMIN"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede eliminar reseñas");
        }
        service.eliminar(id);
        return ResponseEntity.ok("Eliminado con exito");
    }
}
