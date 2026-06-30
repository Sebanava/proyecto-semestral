package com.arriendo.peliculas.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.arriendo.peliculas.Pelicula;
import com.arriendo.peliculas.model.PeliculaModel;
import com.arriendo.peliculas.service.PeliculaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController {

    @Autowired
    private PeliculaService service;

    @Operation(
        summary = "Obtener todas las películas",
        description = "Retorna el catálogo completo de películas disponibles en el sistema."
    )
    @GetMapping
    public ResponseEntity<List<Pelicula>> obtenertodo() {
        return ResponseEntity.ok(service.obtenertodo());
    }

    @Operation(
        summary = "Obtener película por ID",
        description = "Busca una película por su ID. Retorna 404 si no existe."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> obtenerPorId(@PathVariable long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @Operation(
        summary = "Obtener película por título",
        description = "Busca una película exactamente por su título. Retorna 404 si no se encuentra."
    )
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Pelicula> obtenerPorTitulo(@PathVariable String titulo) {
        return ResponseEntity.ok(service.obtenerPorTitulo(titulo));
    }

    @Operation(
        summary = "Obtener películas por categoría",
        description = "Filtra el catálogo por categoría (ej: Accion, Drama, Comedia)."
    )
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Pelicula>> obtenerPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(service.ObtenerPorCategoria(categoria));
    }

    @Operation(
        summary = "Crear nueva película",
        description = "Crea una película en el catálogo. Solo accesible con rol ADMIN. " +
                      "Valida que el título no esté duplicado. " +
                      "Retorna 403 si el rol no es ADMIN. Retorna 400 si el título ya existe."
    )
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody PeliculaModel model, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede agregar películas");
        }
        return ResponseEntity.status(201).body(service.guardar(model));
    }

    @Operation(
        summary = "Actualizar película",
        description = "Actualiza los datos de una película existente. Solo accesible con rol ADMIN. " +
                      "Retorna 404 si la película no existe. Retorna 403 si el rol no es ADMIN."
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable long id, @Valid @RequestBody PeliculaModel model, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede actualizar películas");
        }
        return ResponseEntity.ok(service.actualizar(id, model));
    }

    @Operation(
        summary = "Eliminar película",
        description = "Elimina una película del catálogo. Solo accesible con rol ADMIN. " +
                      "Retorna 403 si el rol no es ADMIN."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede eliminar películas");
        }
        service.eliminar(id);
        return ResponseEntity.ok("Película eliminada con éxito");
    }
}
