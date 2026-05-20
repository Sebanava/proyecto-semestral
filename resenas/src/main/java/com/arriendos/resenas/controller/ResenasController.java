package com.arriendos.resenas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arriendos.resenas.Resenas;
import com.arriendos.resenas.model.ResenasModel;
import com.arriendos.resenas.service.ResenasService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/resenas")
public class ResenasController {

    @Autowired
    private ResenasService service;


    @GetMapping
    public ResponseEntity<List<Resenas>>ObtenerTodo(){
        return ResponseEntity.ok(service.ObtenerTodo());
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<Resenas>>ObtenerPorTitulo(@PathVariable String titulo){
        return ResponseEntity.ok(service.ObtenerPorTitulo(titulo));
    }

    @PostMapping
    public ResponseEntity<Resenas>guardar(@Valid @RequestBody ResenasModel model){
        return ResponseEntity.status(201).body(service.guardar(model));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>eliminar(@PathVariable Long id){
        service.eliminar(id);
        return ResponseEntity.ok("Eliminado con exito");
}




}
