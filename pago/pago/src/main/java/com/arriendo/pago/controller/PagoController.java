package com.arriendo.pago.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.arriendo.pago.pago;
import com.arriendo.pago.model.PagoModel;
import com.arriendo.pago.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pago")
public class PagoController {

    @Autowired
    private PagoService service;

    @Operation(
        summary = "Procesar pago de un arriendo",
        description = "Valida número de tarjeta (exactamente 16 dígitos) y CVV (exactamente 3 dígitos). " +
                      "Verifica que el arriendo exista y no haya sido pagado anteriormente. " +
                      "Si el pago es exitoso, actualiza automáticamente el estado del arriendo a 'pagado'. " +
                      "Solo accesible con rol CLIENTE. " +
                      "Retorna 400 si la tarjeta o CVV son inválidos o si el arriendo ya fue pagado."
    )
    @PostMapping("/procesar")
    public ResponseEntity<?> procesarPago(@Valid @RequestBody PagoModel model, @RequestParam String rol) {
        if (!rol.equals("CLIENTE")) {
            return ResponseEntity.status(403).body("Solo un CLIENTE puede procesar pagos");
        }
        return ResponseEntity.ok(service.procesarPago(model));
    }

    @Operation(
        summary = "Obtener todos los pagos",
        description = "Retorna el historial completo de pagos registrados en el sistema."
    )
    @GetMapping
    public ResponseEntity<List<pago>> obtenerTodo() {
        return ResponseEntity.ok(service.obtenerTodo());
    }

    @Operation(
        summary = "Obtener pago por ID",
        description = "Busca un pago específico por su ID. Retorna 404 si no existe."
    )
    @GetMapping("/{id}")
    public ResponseEntity<pago> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @Operation(
        summary = "Actualizar pago",
        description = "Actualiza los datos de un pago existente. Solo accesible con rol ADMIN. " +
                      "Retorna 404 si el pago no existe."
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody pago pagoActualizado, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede actualizar pagos");
        }
        return ResponseEntity.ok(service.actualizar(id, pagoActualizado));
    }

    @Operation(
        summary = "Eliminar pago",
        description = "Elimina un registro de pago del sistema. Solo accesible con rol ADMIN. " +
                      "Retorna 404 si el pago no existe."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede eliminar pagos");
        }
        service.eliminar(id);
        return ResponseEntity.ok("Pago eliminado con éxito");
    }
}
