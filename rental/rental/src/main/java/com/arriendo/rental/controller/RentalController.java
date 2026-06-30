package com.arriendo.rental.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.arriendo.rental.rental;
import com.arriendo.rental.model.RentalModel;
import com.arriendo.rental.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/rental")
@Tag(name = "Arriendos", description = "Gestión del ciclo completo de arriendos de películas")
public class RentalController {

    @Autowired
    private RentalService service;

    @Operation(summary = "Obtener todos los arriendos", description = "Retorna la lista completa de arriendos registrados en el sistema.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de arriendos retornada exitosamente")
    })
    @GetMapping
    public ResponseEntity<List<rental>> obtenertodo() {
        return ResponseEntity.ok(service.obtenertodo());
    }

    @Operation(summary = "Obtener arriendo por ID", description = "Busca un arriendo por su ID. Retorna 404 si no existe.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Arriendo encontrado"),
        @ApiResponse(responseCode = "404", description = "Arriendo no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<rental> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @Operation(summary = "Crear nuevo arriendo", description = "Crea un arriendo. Valida que fecha_fin no sea anterior a fecha_inicio. Consulta el precio desde Películas y reduce el stock en Inventario.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Arriendo creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Fechas inválidas u otro error de validación"),
        @ApiResponse(responseCode = "403", description = "No autorizado - se requiere rol CLIENTE"),
        @ApiResponse(responseCode = "404", description = "Película no encontrada en el servicio externo"),
        @ApiResponse(responseCode = "503", description = "Servicio de Películas o Inventario no disponible")
    })
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody RentalModel model, @RequestParam String rol) {
        if (!rol.equals("CLIENTE")) {
            return ResponseEntity.status(403).body("Solo un CLIENTE puede crear arriendos");
        }
        return ResponseEntity.status(201).body(service.guardar(model));
    }

    @Operation(summary = "Actualizar arriendo", description = "Actualiza los datos de un arriendo existente. Solo accesible con rol ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Arriendo actualizado exitosamente"),
        @ApiResponse(responseCode = "403", description = "No autorizado - se requiere rol ADMIN"),
        @ApiResponse(responseCode = "404", description = "Arriendo no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable long id, @Valid @RequestBody RentalModel model, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede actualizar arriendos");
        }
        return ResponseEntity.ok(service.actualizar(id, model));
    }

    @Operation(summary = "Eliminar arriendo", description = "Elimina un arriendo del sistema. Solo accesible con rol ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Arriendo eliminado exitosamente"),
        @ApiResponse(responseCode = "403", description = "No autorizado - se requiere rol ADMIN")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede eliminar arriendos");
        }
        service.eliminar(id);
        return ResponseEntity.ok("Arriendo eliminado con éxito");
    }

    @Operation(summary = "Marcar arriendo como pagado", description = "Cambia el estado del arriendo a 'pagado'. Llamado automáticamente por el servicio Pago.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estado actualizado a pagado"),
        @ApiResponse(responseCode = "400", description = "El arriendo ya fue pagado anteriormente"),
        @ApiResponse(responseCode = "404", description = "Arriendo no encontrado")
    })
    @PutMapping("/{id}/pagar")
    public ResponseEntity<?> pagar(@PathVariable Long id) {
        service.pagar(id);
        return ResponseEntity.ok("Estado del arriendo actualizado a pagado");
    }

    @Operation(summary = "Devolver película arrendada", description = "Cambia el estado a 'devuelto' y restaura el stock en Inventario. Accesible por CLIENTE o ADMIN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Película devuelta y stock restaurado exitosamente"),
        @ApiResponse(responseCode = "400", description = "El arriendo ya fue devuelto anteriormente"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "503", description = "Servicio de Inventario no disponible")
    })
    @PutMapping("/{id}/devolver")
    public ResponseEntity<?> devolver(@PathVariable Long id, @RequestParam String rol) {
        if (!rol.equals("CLIENTE") && !rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("No autorizado para devolver arriendos");
        }
        service.devolver(id);
        return ResponseEntity.ok("Película devuelta exitosamente. Stock restaurado.");
    }
}
