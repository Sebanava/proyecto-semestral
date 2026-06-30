package com.arriendos.resenas.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.arriendos.resenas.Resenas;
import com.arriendos.resenas.model.ResenasModel;
import com.arriendos.resenas.service.ResenasService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/resenas")
public class ResenasController {

    @Autowired
    private ResenasService service;

    @Operation(
        summary = "Obtener todas las reseñas",
        description = "Retorna todas las reseñas registradas en el sistema."
    )
    @GetMapping
    public ResponseEntity<List<Resenas>> ObtenerTodo() {
        return ResponseEntity.ok(service.ObtenerTodo());
    }

    @Operation(
        summary = "Obtener reseña por ID",
        description = "Busca una reseña específica por su ID. Retorna 404 si no existe."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Resenas> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @Operation(
        summary = "Obtener reseñas por título de película",
        description = "Retorna todas las reseñas de una película específica buscando por título. " +
                      "Retorna 404 si no hay reseñas para ese título."
    )
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<Resenas>> ObtenerPorTitulo(@PathVariable String titulo) {
        return ResponseEntity.ok(service.ObtenerPorTitulo(titulo));
    }

    @Operation(
        summary = "Crear nueva reseña",
        description = "Crea una reseña para una película. Solo accesible con rol CLIENTE. " +
                      "Valida que la película exista en el catálogo antes de guardar. " +
                      "Calificación debe estar entre 0 y 10. " +
                      "Comentario no puede superar los 200 caracteres. " +
                      "Retorna 404 si la película no existe. Retorna 403 si el rol no es CLIENTE."
    )
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody ResenasModel model, @RequestParam String rol) {
        if (!rol.equals("CLIENTE")) {
            return ResponseEntity.status(403).body("Solo un CLIENTE puede crear reseñas");
        }
        return ResponseEntity.status(201).body(service.guardar(model));
    }

    @Operation(
        summary = "Actualizar reseña",
        description = "Actualiza el contenido de una reseña existente. Solo accesible con rol ADMIN. " +
                      "Retorna 404 si la reseña no existe."
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody ResenasModel model, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede actualizar reseñas");
        }
        return ResponseEntity.ok(service.actualizar(id, model));
    }

    @Operation(
        summary = "Eliminar reseña",
        description = "Elimina una reseña del sistema. Solo accesible con rol ADMIN. " +
                      "Retorna 404 si la reseña no existe. Retorna 403 si el rol no es ADMIN."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede eliminar reseñas");
        }
        service.eliminar(id);
        return ResponseEntity.ok("Eliminado con exito");
    }
}
