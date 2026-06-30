package com.arriendo.reportes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.arriendo.reportes.model.ReportesModel;
import com.arriendo.reportes.service.ReportesService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/reportes")
public class ReportesController {

    @Autowired
    private ReportesService service;

    @Operation(
        summary = "Obtener todos los reportes",
        description = "Retorna todos los reportes registrados. Solo accesible con rol ADMIN."
    )
    @GetMapping
    public ResponseEntity<?> ObtenerTodo(@RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede ver los reportes");
        }
        return ResponseEntity.ok(service.ObtenerTodo());
    }

    @Operation(
        summary = "Obtener reporte por ID",
        description = "Busca un reporte específico por su ID. Solo accesible con rol ADMIN. " +
                      "Retorna 404 si el reporte no existe."
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> ObtenerPorId(@PathVariable long id, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede ver los reportes");
        }
        return ResponseEntity.ok(service.ObtenerPorId(id));
    }

    @Operation(
        summary = "Crear nuevo reporte",
        description = "Registra un nuevo reporte en el sistema. Solo accesible con rol ADMIN. " +
                      "Requiere tipo, estado y fecha."
    )
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody ReportesModel model, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede crear reportes");
        }
        return ResponseEntity.status(201).body(service.guardar(model));
    }

    @Operation(
        summary = "Actualizar reporte",
        description = "Actualiza los datos de un reporte existente. Solo accesible con rol ADMIN. " +
                      "Retorna 404 si el reporte no existe."
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable long id, @Valid @RequestBody ReportesModel model, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede actualizar reportes");
        }
        return ResponseEntity.ok(service.actualizar(id, model));
    }

    @Operation(
        summary = "Eliminar reporte",
        description = "Elimina un reporte del sistema. Solo accesible con rol ADMIN. " +
                      "Retorna 404 si el reporte no existe."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede eliminar reportes");
        }
        service.eliminar(id);
        return ResponseEntity.ok("Eliminado con exito");
    }

    @Operation(
        summary = "Obtener resumen consolidado del sistema",
        description = "Consolida en una sola respuesta datos de tres microservicios: " +
                      "total de clientes (desde Clientes), total y estado de arriendos (desde Rental), " +
                      "y total de pagos aprobados (desde Pago). Solo accesible con rol ADMIN."
    )
    @GetMapping("/resumen")
    public ResponseEntity<?> resumen(@RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede ver el resumen");
        }
        return ResponseEntity.ok(service.resumen());
    }
}
