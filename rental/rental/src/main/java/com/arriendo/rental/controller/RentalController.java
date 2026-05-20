package com.arriendo.rental.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
        public ResponseEntity<rental>guardar(@Valid @RequestBody RentalModel login){
            return ResponseEntity.status(201).body(service.guardar(login));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void>eliminar(@PathVariable Long id){
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }

        @PutMapping("/{id}")
        public ResponseEntity<rental>actualizar(@PathVariable long id,@Valid @RequestBody RentalModel model){
            return ResponseEntity.ok(service.actualizar(id, model));
        }

        


    }


