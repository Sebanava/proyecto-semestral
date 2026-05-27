package com.arriendo.pago.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import com.arriendo.pago.pago;
import com.arriendo.pago.model.PagoModel;
import com.arriendo.pago.service.PagoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pago")
public class PagoController {

    @Autowired
    private PagoService service;

    @PostMapping("/procesar")
    public ResponseEntity<?> procesarPago(@Valid @RequestBody PagoModel model, @RequestParam String rol) {
        if (!rol.equals("CLIENTE")) {
            return ResponseEntity.status(403).body("Solo un CLIENTE puede procesar pagos");
        }
        return ResponseEntity.ok(service.procesarPago(model));
    }

    @GetMapping
    public ResponseEntity<List<pago>> obtenerTodo(){
        return ResponseEntity.ok(service.obtenerTodo());
    }
}