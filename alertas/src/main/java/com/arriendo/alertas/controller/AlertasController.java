package com.arriendo.alertas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.arriendo.alertas.model.AlertasModel;
import com.arriendo.alertas.service.AlertasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/alertas")
@Tag(name = "Alertas", description = "Monitoreo de niveles de stock de películas en tiempo real")
public class AlertasController {

    @Autowired
    private AlertasService service;

    @Operation(summary = "Obtener todas las alertas", description = "Retorna todas las alertas registradas. Solo accesible con rol ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de alertas retornada exitosamente"),
        @ApiResponse(responseCode = "403", description = "No autorizado - se requiere rol ADMIN")
    })
    @GetMapping
    public ResponseEntity<?> obtenerTodo(@RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede ver alertas");
        }
        return ResponseEntity.ok(service.ObtenerTodo());
    }

    @Operation(summary = "Obtener alerta por ID", description = "Busca una alerta por su ID. Solo accesible con rol ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Alerta encontrada"),
        @ApiResponse(responseCode = "403", description = "No autorizado - se requiere rol ADMIN"),
        @ApiResponse(responseCode = "404", description = "Alerta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorIncidencia(@PathVariable long id, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede ver alertas");
        }
        return ResponseEntity.ok(service.ObtenerPorIncidencia(id));
    }

    @Operation(summary = "Crear nueva alerta", description = "Registra una nueva alerta. Solo accesible con rol ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Alerta creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "403", description = "No autorizado - se requiere rol ADMIN")
    })
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody AlertasModel model, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede crear alertas");
        }
        return ResponseEntity.status(201).body(service.guardar(model));
    }

    @Operation(summary = "Actualizar alerta", description = "Actualiza los datos de una alerta existente. Solo accesible con rol ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Alerta actualizada exitosamente"),
        @ApiResponse(responseCode = "403", description = "No autorizado - se requiere rol ADMIN"),
        @ApiResponse(responseCode = "404", description = "Alerta no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable long id, @Valid @RequestBody AlertasModel model, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede actualizar alertas");
        }
        return ResponseEntity.ok(service.actualizar(id, model));
    }

    @Operation(summary = "Eliminar alerta", description = "Elimina una alerta del sistema. Solo accesible con rol ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Alerta eliminada exitosamente"),
        @ApiResponse(responseCode = "403", description = "No autorizado - se requiere rol ADMIN"),
        @ApiResponse(responseCode = "404", description = "Alerta no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable long id, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede eliminar alertas");
        }
        service.eliminar(id);
        return ResponseEntity.ok("Alerta eliminada con éxito");
    }

    @Operation(summary = "Verificar nivel de stock de una película", description = "Consulta el stock actual por título de película y lo clasifica en SIN STOCK (0), STOCK BAJO (1-3) o STOCK SUFICIENTE (más de 3). Consulta en tiempo real Películas e Inventario.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Verificación completada con nivel de stock"),
        @ApiResponse(responseCode = "403", description = "No autorizado - se requiere rol ADMIN"),
        @ApiResponse(responseCode = "404", description = "Película o inventario no encontrado"),
        @ApiResponse(responseCode = "503", description = "Servicio de Películas o Inventario no disponible")
    })
    @GetMapping("/verificar/{titulo}")
    public ResponseEntity<?> verificarStock(@PathVariable String titulo, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede verificar stock");
        }
        return ResponseEntity.ok(service.VerificarStock(titulo));
    }
}
