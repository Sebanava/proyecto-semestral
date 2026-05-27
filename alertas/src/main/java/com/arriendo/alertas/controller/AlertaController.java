package com.arriendo.alertas.controller;

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

import com.arriendo.alertas.alertas;
import com.arriendo.alertas.model.AlertasModel;
import com.arriendo.alertas.service.AlertasService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/alertas")
public class AlertaController {

    @Autowired
    private AlertasService Service;

    @GetMapping
    public ResponseEntity<List<alertas>> ObtenerTodo(){
        return ResponseEntity.ok(Service.ObtenerTodo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<alertas> ObtenerPorIncidencia(@PathVariable long id){
        return ResponseEntity.ok(Service.ObtenerPorIncidencia(id));
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody AlertasModel model, @RequestParam String rol){
        if (!rol.equals("OPERADOR")) {
            return ResponseEntity.status(403).body("Solo un OPERADOR puede crear alertas");
        }
        return ResponseEntity.status(201).body(Service.guardar(model));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable long id, @RequestParam String rol){
        if (!rol.equals("OPERADOR")) {
            return ResponseEntity.status(403).body("Solo un OPERADOR puede eliminar alertas");
        }
        Service.eliminar(id);
        return ResponseEntity.ok("eliminado con exito");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable long id, @Valid @RequestBody AlertasModel model, @RequestParam String rol){
        if (!rol.equals("OPERADOR")) {
            return ResponseEntity.status(403).body("Solo un OPERADOR puede actualizar alertas");
        }
        return ResponseEntity.ok(Service.actualizar(id, model));
    }

    @GetMapping("/verificar/{titulo}")
    public ResponseEntity<?> VerificarStock(@PathVariable String titulo, @RequestParam String rol){
        if (!rol.equals("OPERADOR")) {
            return ResponseEntity.status(403).body("Solo un OPERADOR puede verificar stock");
        }
        return ResponseEntity.ok(Service.VerificarStock(titulo));
    }
}