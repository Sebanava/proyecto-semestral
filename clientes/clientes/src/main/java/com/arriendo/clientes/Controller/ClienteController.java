package com.arriendo.clientes.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.arriendo.clientes.Cliente;
import com.arriendo.clientes.Service.ClienteService;
import com.arriendo.clientes.model.ClienteModel;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @Operation(
        summary = "Obtener todos los clientes",
        description = "Retorna la lista completa de clientes registrados en el sistema."
    )
    @GetMapping
    public ResponseEntity<List<Cliente>> obtenertodo() {
        return ResponseEntity.ok(service.obtenertodo());
    }

    @Operation(
        summary = "Obtener cliente por RUT",
        description = "Busca un cliente por su RUT. Retorna 404 si el cliente no existe."
    )
    @GetMapping("/{rut}")
    public ResponseEntity<List<Cliente>> ObtenerPorRut(@PathVariable String rut) {
        return ResponseEntity.ok(service.ObtenerPorRut(rut));
    }

    @Operation(
        summary = "Crear nuevo cliente",
        description = "Registra un nuevo cliente en el sistema. " +
                      "Valida que el email no esté registrado previamente. " +
                      "Valida que el RUT no esté registrado previamente. " +
                      "Retorna 400 si el email o RUT ya existen. Retorna 201 si fue creado."
    )
    @PostMapping
    public ResponseEntity<Cliente> guardar(@Valid @RequestBody ClienteModel model) {
        return ResponseEntity.status(201).body(service.guardar(model));
    }

    @Operation(
        summary = "Actualizar cliente",
        description = "Actualiza los datos de un cliente existente. Solo accesible con rol ADMIN. " +
                      "Retorna 404 si el cliente no existe. Retorna 403 si el rol no es ADMIN."
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable long id, @Valid @RequestBody ClienteModel model, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede actualizar clientes");
        }
        return ResponseEntity.ok(service.actualizar(id, model));
    }

    @Operation(
        summary = "Eliminar cliente",
        description = "Elimina un cliente del sistema. Solo accesible con rol ADMIN. " +
                      "Retorna 403 si el rol no es ADMIN."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede eliminar clientes");
        }
        service.eliminar(id);
        return ResponseEntity.ok("Cliente eliminado con exito");
    }
}
