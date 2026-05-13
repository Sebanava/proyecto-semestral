package com.arriendo.clientes.Controller;

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

import com.arriendo.clientes.Cliente;
import com.arriendo.clientes.Service.ClienteService;
import com.arriendo.clientes.model.ClienteModel;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

        @GetMapping
        public ResponseEntity<List<Cliente>> obtenertodo(){
            return ResponseEntity.ok(service.obtenertodo());
        }

        @PostMapping
        public ResponseEntity<Cliente>guardar(@Valid @RequestBody ClienteModel model){
            return ResponseEntity.status(201).body(service.guardar(model));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void>eliminar(@PathVariable Long id){
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }

        @GetMapping("/{rut}")
        public ResponseEntity<List<Cliente>> ObtenerPorRut(@PathVariable String rut) {
            return ResponseEntity.ok(service.ObtenerPorRut(rut));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Cliente>actualizar(@PathVariable long id,@Valid @RequestBody ClienteModel model){
        return ResponseEntity.ok(service.actualizar(id, model));
    }


    }


