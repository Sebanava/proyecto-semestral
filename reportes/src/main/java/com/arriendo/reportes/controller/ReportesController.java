package com.arriendo.reportes.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arriendo.reportes.Reportes;
import com.arriendo.reportes.model.ReportesModel;
import com.arriendo.reportes.service.ReportesService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reportes")
public class ReportesController {

    @Autowired
    private ReportesService service;

    @GetMapping
    public ResponseEntity<List<Reportes>> ObtenerTodo(){
        return ResponseEntity.ok(service.ObtenerTodo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reportes> ObtenerPorId(@PathVariable long id){
        return ResponseEntity.ok(service.ObtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody ReportesModel model, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede crear reportes");
        }
        return ResponseEntity.status(201).body(service.guardar(model));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable long id, @Valid @RequestBody ReportesModel model, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede actualizar reportes");
        }
        return ResponseEntity.ok(service.actualizar(id, model));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede eliminar reportes");
        }
        service.eliminar(id);
        return ResponseEntity.ok("Eliminado con exito");
    }

    @GetMapping("/resumen")
    public ResponseEntity<?> resumen(@RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede ver el resumen");
        }
        return ResponseEntity.ok(service.resumen());
    }
}