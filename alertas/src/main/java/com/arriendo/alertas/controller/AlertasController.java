package com.arriendo.alertas.controller;

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
import com.arriendo.alertas.model.AlertasModel;
import com.arriendo.alertas.service.AlertasService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/alertas")
public class AlertasController {

    @Autowired
    private AlertasService service;

    @GetMapping
    public ResponseEntity<?> obtenerTodo(@RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede ver alertas");
        }
        return ResponseEntity.ok(service.ObtenerTodo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorIncidencia(@PathVariable long id, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede ver alertas");
        }
        return ResponseEntity.ok(service.ObtenerPorIncidencia(id));
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody AlertasModel model, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede crear alertas");
        }
        return ResponseEntity.status(201).body(service.guardar(model));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable long id, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede eliminar alertas");
        }
        service.eliminar(id);
        return ResponseEntity.ok("Alerta eliminada con éxito");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable long id, @Valid @RequestBody AlertasModel model, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede actualizar alertas");
        }
        return ResponseEntity.ok(service.actualizar(id, model));
    }

    @GetMapping("/verificar/{titulo}")
    public ResponseEntity<?> verificarStock(@PathVariable String titulo, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede verificar stock");
        }
        return ResponseEntity.ok(service.VerificarStock(titulo));
    }
}
