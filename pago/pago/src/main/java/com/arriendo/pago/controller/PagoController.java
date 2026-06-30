package com.arriendo.pago.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.arriendo.pago.pago;
import com.arriendo.pago.model.PagoModel;
import com.arriendo.pago.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pago")
@Tag(name = "Pagos", description = "Procesamiento de pagos de arriendos con validación de tarjeta")
public class PagoController {

    @Autowired
    private PagoService service;

    @Operation(summary = "Procesar pago de un arriendo", description = "Valida tarjeta (16 dígitos) y CVV (3 dígitos). Verifica que el arriendo no haya sido pagado. Al aprobar, actualiza el estado del arriendo a 'pagado'.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pago aprobado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos: tarjeta, CVV o arriendo ya pagado"),
        @ApiResponse(responseCode = "403", description = "No autorizado - se requiere rol CLIENTE"),
        @ApiResponse(responseCode = "404", description = "Arriendo no encontrado"),
        @ApiResponse(responseCode = "503", description = "Servicio de Arriendos no disponible")
    })
    @PostMapping("/procesar")
    public ResponseEntity<?> procesarPago(@Valid @RequestBody PagoModel model, @RequestParam String rol) {
        if (!rol.equals("CLIENTE")) {
            return ResponseEntity.status(403).body("Solo un CLIENTE puede procesar pagos");
        }
        return ResponseEntity.ok(service.procesarPago(model));
    }

    @Operation(summary = "Obtener todos los pagos", description = "Retorna el historial completo de pagos registrados en el sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Historial de pagos retornado exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<pago>> obtenerTodo() {
        return ResponseEntity.ok(service.obtenerTodo());
    }

    @Operation(summary = "Obtener pago por ID", description = "Busca un pago específico por su ID. Retorna 404 si no existe.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pago encontrado"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<pago> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @Operation(summary = "Actualizar pago", description = "Actualiza los datos de un pago existente. Solo accesible con rol ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pago actualizado exitosamente"),
        @ApiResponse(responseCode = "403", description = "No autorizado - se requiere rol ADMIN"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody pago pagoActualizado, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede actualizar pagos");
        }
        return ResponseEntity.ok(service.actualizar(id, pagoActualizado));
    }

    @Operation(summary = "Eliminar pago", description = "Elimina un registro de pago del sistema. Solo accesible con rol ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pago eliminado exitosamente"),
        @ApiResponse(responseCode = "403", description = "No autorizado - se requiere rol ADMIN"),
        @ApiResponse(responseCode = "404", description = "Pago no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede eliminar pagos");
        }
        service.eliminar(id);
        return ResponseEntity.ok("Pago eliminado con éxito");
    }
}
