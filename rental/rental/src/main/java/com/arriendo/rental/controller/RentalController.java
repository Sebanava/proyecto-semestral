package com.arriendo.rental.controller;

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

import com.arriendo.rental.*;
import com.arriendo.rental.model.RentalModel;
import com.arriendo.rental.service.RentalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/rental")
public class RentalController {

    @Autowired
    private RentalService service;

    @GetMapping
    public ResponseEntity<List<rental>> obtenertodo(){
        return ResponseEntity.ok(service.obtenertodo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<rental> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody RentalModel model, @RequestParam String rol){
        if (!rol.equals("CLIENTE")) {
            return ResponseEntity.status(403).body("Solo un CLIENTE puede crear arriendos");
        }
        return ResponseEntity.status(201).body(service.guardar(model));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestParam String rol){
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede eliminar arriendos");
        }
        service.eliminar(id);
        return ResponseEntity.ok("Arriendo eliminado con éxito");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable long id, @Valid @RequestBody RentalModel model, @RequestParam String rol){
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede actualizar arriendos");
        }
        return ResponseEntity.ok(service.actualizar(id, model));
    }
}