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
    public ResponseEntity<List<Reportes>>ObtenerTodo(){
        return ResponseEntity.ok(service.ObtenerTodo());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reportes>ObtenerPorId(@PathVariable long id){
        return ResponseEntity.ok(service.ObtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Reportes>guardar(@Valid @RequestBody ReportesModel model){
        return ResponseEntity.status(201).body(service.guardar(model));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reportes>actualizar(@PathVariable long id,@Valid @RequestBody  ReportesModel model ){
        return ResponseEntity.ok(service.actualizar(id, model));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>eliminar(@PathVariable Long id){
        service.eliminar(id);   
        return ResponseEntity.ok("Eliminado con exito");
    }
    @GetMapping("/resumen")
    public ResponseEntity<String> resumen(){
        return ResponseEntity.ok(service.resumen());
}

    

}
