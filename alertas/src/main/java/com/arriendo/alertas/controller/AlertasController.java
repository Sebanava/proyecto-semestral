package com.arriendo.alertas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.arriendo.alertas.model.AlertasModel;
import com.arriendo.alertas.service.AlertasService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/alertas")
public class AlertasController {

    @Autowired
    private AlertasService service;

    @Operation(
        summary = "Obtener todas las alertas",
        description = "Retorna todas las alertas registradas. Solo accesible con rol ADMIN."
    )
    @GetMapping
    public ResponseEntity<?> obtenerTodo(@RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede ver alertas");
        }
        return ResponseEntity.ok(service.ObtenerTodo());
    }

    @Operation(
        summary = "Obtener alerta por ID",
        description = "Busca una alerta por su ID. Solo accesible con rol ADMIN. " +
                      "Retorna 404 si la alerta no existe."
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorIncidencia(@PathVariable long id, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede ver alertas");
        }
        return ResponseEntity.ok(service.ObtenerPorIncidencia(id));
    }

    @Operation(
        summary = "Crear nueva alerta",
        description = "Registra una nueva alerta en el sistema. Solo accesible con rol ADMIN. " +
                      "Requiere id_pelicula, tipo y fecha."
    )
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody AlertasModel model, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede crear alertas");
        }
        return ResponseEntity.status(201).body(service.guardar(model));
    }

    @Operation(
        summary = "Actualizar alerta",
        description = "Actualiza los datos de una alerta existente. Solo accesible con rol ADMIN. " +
                      "Retorna 404 si la alerta no existe."
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable long id, @Valid @RequestBody AlertasModel model, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede actualizar alertas");
        }
        return ResponseEntity.ok(service.actualizar(id, model));
    }

    @Operation(
        summary = "Eliminar alerta",
        description = "Elimina una alerta del sistema. Solo accesible con rol ADMIN. " +
                      "Retorna 404 si la alerta no existe."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable long id, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede eliminar alertas");
        }
        service.eliminar(id);
        return ResponseEntity.ok("Alerta eliminada con éxito");
    }

    @Operation(
        summary = "Verificar nivel de stock de una película",
        description = "Consulta el stock actual de una película por su título. Solo accesible con rol ADMIN. " +
                      "Clasifica el resultado en tres niveles: " +
                      "SIN STOCK (0 unidades), STOCK BAJO (1 a 3 unidades), STOCK SUFICIENTE (más de 3). " +
                      "Consulta en tiempo real los servicios de Películas e Inventario. " +
                      "Retorna 404 si la película no existe."
    )
    @GetMapping("/verificar/{titulo}")
    public ResponseEntity<?> verificarStock(@PathVariable String titulo, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede verificar stock");
        }
        return ResponseEntity.ok(service.VerificarStock(titulo));
    }
}
