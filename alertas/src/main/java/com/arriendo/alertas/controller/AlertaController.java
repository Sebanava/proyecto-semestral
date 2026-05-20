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
    public ResponseEntity<List<alertas>>ObtenerTodo(){
        return ResponseEntity.ok(Service.ObtenerTodo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<alertas>ObtenerPorIncidencia(@PathVariable long id){
        return ResponseEntity.ok(Service.ObtenerPorIncidencia(id));
    }

    @PostMapping 
    public ResponseEntity<alertas>guardar(@Valid @RequestBody AlertasModel model){
        return ResponseEntity.status(201).body(Service.guardar(model));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>eliminar(@PathVariable long id){
        Service.eliminar(id);
        return ResponseEntity.ok("eliminado con exito");
    }

    @PutMapping("/{id}")
        public ResponseEntity<alertas>actualizar(@PathVariable long id,@Valid @RequestBody AlertasModel model){
            return ResponseEntity.ok(Service.actualizar(id, model));
        }

    @GetMapping("/verificar/{titulo}")
    public ResponseEntity<String>VerificarStock(@PathVariable String titulo){
        return ResponseEntity.ok(Service.VerificarStock(titulo));
    }
}
