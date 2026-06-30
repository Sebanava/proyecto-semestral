package com.arriendo.rental.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.arriendo.rental.rental;
import com.arriendo.rental.model.RentalModel;
import com.arriendo.rental.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/rental")
public class RentalController {

    @Autowired
    private RentalService service;

    @Operation(
        summary = "Obtener todos los arriendos",
        description = "Retorna la lista completa de arriendos registrados en el sistema."
    )
    @GetMapping
    public ResponseEntity<List<rental>> obtenertodo() {
        return ResponseEntity.ok(service.obtenertodo());
    }

    @Operation(
        summary = "Obtener arriendo por ID",
        description = "Busca un arriendo por su ID. Retorna 404 si no existe."
    )
    @GetMapping("/{id}")
    public ResponseEntity<rental> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @Operation(
        summary = "Crear nuevo arriendo",
        description = "Crea un arriendo para un cliente. Solo accesible con rol CLIENTE. " +
                      "Valida que fecha_fin no sea anterior a fecha_inicio. " +
                      "Consulta el precio de la película automáticamente. " +
                      "Reduce el stock disponible en Inventario. " +
                      "Retorna 400 si las fechas son inválidas. Retorna 403 si el rol no es CLIENTE."
    )
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody RentalModel model, @RequestParam String rol) {
        if (!rol.equals("CLIENTE")) {
            return ResponseEntity.status(403).body("Solo un CLIENTE puede crear arriendos");
        }
        return ResponseEntity.status(201).body(service.guardar(model));
    }

    @Operation(
        summary = "Actualizar arriendo",
        description = "Actualiza los datos de un arriendo existente. Solo accesible con rol ADMIN. " +
                      "Retorna 404 si el arriendo no existe."
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable long id, @Valid @RequestBody RentalModel model, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede actualizar arriendos");
        }
        return ResponseEntity.ok(service.actualizar(id, model));
    }

    @Operation(
        summary = "Eliminar arriendo",
        description = "Elimina un arriendo del sistema. Solo accesible con rol ADMIN."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede eliminar arriendos");
        }
        service.eliminar(id);
        return ResponseEntity.ok("Arriendo eliminado con éxito");
    }

    @Operation(
        summary = "Marcar arriendo como pagado",
        description = "Cambia el estado del arriendo a 'pagado'. " +
                      "Es llamado automáticamente por el servicio de Pago al aprobar un pago. " +
                      "Retorna 400 si el arriendo ya estaba pagado."
    )
    @PutMapping("/{id}/pagar")
    public ResponseEntity<?> pagar(@PathVariable Long id) {
        service.pagar(id);
        return ResponseEntity.ok("Estado del arriendo actualizado a pagado");
    }

    @Operation(
        summary = "Devolver película arrendada",
        description = "Cambia el estado del arriendo a 'devuelto' y restaura el stock en Inventario. " +
                      "Accesible por CLIENTE o ADMIN. " +
                      "Retorna 400 si el arriendo ya fue devuelto anteriormente."
    )
    @PutMapping("/{id}/devolver")
    public ResponseEntity<?> devolver(@PathVariable Long id, @RequestParam String rol) {
        if (!rol.equals("CLIENTE") && !rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("No autorizado para devolver arriendos");
        }
        service.devolver(id);
        return ResponseEntity.ok("Película devuelta exitosamente. Stock restaurado.");
    }
}
