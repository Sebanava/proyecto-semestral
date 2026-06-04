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
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/{rut}")
    public ResponseEntity<List<Cliente>> ObtenerPorRut(@PathVariable String rut){
        return ResponseEntity.ok(service.ObtenerPorRut(rut));
    }

    @PostMapping
    public ResponseEntity<Cliente> guardar(@Valid @RequestBody ClienteModel model){
        return ResponseEntity.status(201).body(service.guardar(model));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable long id, @Valid @RequestBody ClienteModel model, @RequestParam String rol){
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede actualizar clientes");
        }
        return ResponseEntity.ok(service.actualizar(id, model));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestParam String rol){
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede eliminar clientes");
        }
        service.eliminar(id);
        return ResponseEntity.ok("Cliente eliminado con exito");
    }
}