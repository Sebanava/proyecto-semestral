package com.arriendo.pago.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arriendo.pago.model.PagoModel;
import com.arriendo.pago.service.PagoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pago")
public class PagoController {

    @Autowired
    private PagoService service;

    @PostMapping("/procesar")
    public ResponseEntity<String> procesarPago(@Valid @RequestBody PagoModel model) {
        return ResponseEntity.ok(service.procesarPago(model));
    }
}