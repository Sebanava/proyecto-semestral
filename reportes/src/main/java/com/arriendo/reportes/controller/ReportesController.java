package com.arriendo.reportes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.arriendo.reportes.model.ReportesModel;
import com.arriendo.reportes.service.ReportesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/reportes")
@Tag(name = "Reportes", description = "Generación de reportes y resumen consolidado del sistema")
public class ReportesController {

    @Autowired
    private ReportesService service;

    @Operation(summary = "Obtener todos los reportes", description = "Retorna todos los reportes registrados. Solo accesible con rol ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de reportes retornada exitosamente"),
        @ApiResponse(responseCode = "403", description = "No autorizado - se requiere rol ADMIN")
    })
    @GetMapping
    public ResponseEntity<?> ObtenerTodo(@RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede ver los reportes");
        }
        return ResponseEntity.ok(service.ObtenerTodo());
    }

    @Operation(summary = "Obtener reporte por ID", description = "Busca un reporte específico por su ID. Solo accesible con rol ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reporte encontrado"),
        @ApiResponse(responseCode = "403", description = "No autorizado - se requiere rol ADMIN"),
        @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> ObtenerPorId(@PathVariable long id, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede ver los reportes");
        }
        return ResponseEntity.ok(service.ObtenerPorId(id));
    }

    @Operation(summary = "Crear nuevo reporte", description = "Registra un nuevo reporte. Solo accesible con rol ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Reporte creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "403", description = "No autorizado - se requiere rol ADMIN")
    })
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody ReportesModel model, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede crear reportes");
        }
        return ResponseEntity.status(201).body(service.guardar(model));
    }

    @Operation(summary = "Actualizar reporte", description = "Actualiza los datos de un reporte existente. Solo accesible con rol ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reporte actualizado exitosamente"),
        @ApiResponse(responseCode = "403", description = "No autorizado - se requiere rol ADMIN"),
        @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable long id, @Valid @RequestBody ReportesModel model, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede actualizar reportes");
        }
        return ResponseEntity.ok(service.actualizar(id, model));
    }

    @Operation(summary = "Eliminar reporte", description = "Elimina un reporte del sistema. Solo accesible con rol ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reporte eliminado exitosamente"),
        @ApiResponse(responseCode = "403", description = "No autorizado - se requiere rol ADMIN"),
        @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede eliminar reportes");
        }
        service.eliminar(id);
        return ResponseEntity.ok("Eliminado con exito");
    }

    @Operation(summary = "Obtener resumen consolidado del sistema", description = "Agrega en una sola respuesta: total de clientes (desde Clientes), total y estado de arriendos (desde Rental), y total de pagos aprobados (desde Pago). Solo accesible con rol ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Resumen consolidado generado exitosamente"),
        @ApiResponse(responseCode = "403", description = "No autorizado - se requiere rol ADMIN"),
        @ApiResponse(responseCode = "503", description = "Uno o más servicios externos no disponibles")
    })
    @GetMapping("/resumen")
    public ResponseEntity<?> resumen(@RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede ver el resumen");
        }
        return ResponseEntity.ok(service.resumen());
    }
}
