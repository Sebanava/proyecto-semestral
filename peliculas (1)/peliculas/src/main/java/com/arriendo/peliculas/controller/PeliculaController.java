package com.arriendo.peliculas.controller;
import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arriendo.peliculas.Pelicula;
import com.arriendo.peliculas.model.PeliculaModel;
import com.arriendo.peliculas.service.PeliculaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/peliculas") 
public class PeliculaController {

    @Autowired
    private PeliculaService service;

    @GetMapping
    public ResponseEntity<List<Pelicula>> obtenertodo() {
        return ResponseEntity.ok(service.obtenertodo());
}
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Pelicula>obtenerPorTitulo(@PathVariable String titulo){
        return ResponseEntity.ok(service.obtenerPorTitulo(titulo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pelicula>actualizar(@PathVariable long id, @Valid @RequestBody PeliculaModel model){
        return ResponseEntity.ok(service.actualizar(id, model));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pelicula>obtenerPorId(@PathVariable long id){
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody PeliculaModel model, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede agregar películas");
        }
        return ResponseEntity.status(201).body(service.guardar(model));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede eliminar películas");
        }
        service.eliminar(id);
        return ResponseEntity.ok("Película eliminada con éxito");
    }


    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Pelicula>> obtenerPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(service.ObtenerPorCategoria(categoria));
    }
}