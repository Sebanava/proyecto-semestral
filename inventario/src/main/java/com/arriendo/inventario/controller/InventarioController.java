package com.arriendo.inventario.controller;

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

import com.arriendo.inventario.Inventario;
import com.arriendo.inventario.model.InventarioModel;
import com.arriendo.inventario.service.InventarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private InventarioService service;

    @GetMapping
    public ResponseEntity<List<Inventario>> obtenerTodo(){
        return ResponseEntity.ok(service.obtenerTodo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventario> obtenerPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody InventarioModel model, @RequestParam String rol){
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede crear inventario");
        }
        return ResponseEntity.status(201).body(service.guardar(model));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody InventarioModel model, @RequestParam String rol){
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede actualizar inventario");
        }
        return ResponseEntity.ok(service.actualizar(id, model));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestParam String rol){
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede eliminar inventario");
        }
        service.eliminar(id);
        return ResponseEntity.ok("Inventario eliminado con exito");
    }

    @PostMapping("/reducir/{idPelicula}")
    public ResponseEntity<String> reducirStock(@PathVariable Long idPelicula){
        service.reducirStock(idPelicula);
        return ResponseEntity.ok("Stock reducido exitosamente");
    }
}