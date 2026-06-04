package com.arriendo.login.LoginController;

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

import com.arriendo.login.*;
import com.arriendo.login.DTO.LoginDTO;
import com.arriendo.login.model.LoginModel;
import com.arriendo.login.service.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class LoginController {

    @Autowired
    private LoginService service;

    @GetMapping
    public ResponseEntity<List<login>> obtenertodo(){
        return ResponseEntity.ok(service.obtenertodo());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO dto){
        return service.login(dto);
    }

    @PostMapping
    public ResponseEntity<login> guardar(@Valid @RequestBody login login){
        return ResponseEntity.status(201).body(service.guardar(login));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable long id, @Valid @RequestBody LoginModel model, @RequestParam String rol){
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede actualizar usuarios");
        }
        return ResponseEntity.ok(service.actualizar(id, model));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestParam String rol){
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede eliminar usuarios");
        }
        service.eliminar(id);
        return ResponseEntity.ok("Usuario eliminado con exito");
    }
}