package com.arriendo.inventario.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.arriendo.inventario.Inventario;
import com.arriendo.inventario.model.InventarioModel;
import com.arriendo.inventario.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private InventarioService service;

    @Operation(
        summary = "Obtener todo el inventario",
        description = "Retorna el inventario completo con stock_total, stock_disponible y stock_arrendadas de cada película."
    )
    @GetMapping
    public ResponseEntity<List<Inventario>> obtenerTodo() {
        return ResponseEntity.ok(service.obtenerTodo());
    }

    @Operation(
        summary = "Obtener inventario por ID",
        description = "Busca un registro de inventario por su ID. Retorna 404 si no existe."
    )
    @GetMapping("/{id}")
    public ResponseEntity<Inventario> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @Operation(
        summary = "Obtener inventario por ID de película",
        description = "Consulta el stock de una película específica usando su ID. Retorna 404 si la película no tiene inventario registrado."
    )
    @GetMapping("/pelicula/{idPelicula}")
    public ResponseEntity<Inventario> obtenerPorIdPelicula(@PathVariable Long idPelicula) {
        return ResponseEntity.ok(service.obtenerPorIdPelicula(idPelicula));
    }

    @Operation(
        summary = "Crear registro de inventario",
        description = "Crea el inventario inicial para una película. Solo accesible con rol ADMIN. " +
                      "Debe ingresarse stock_total, stock_disponible y stock_arrendadas. " +
                      "Retorna 403 si el rol no es ADMIN."
    )
    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody InventarioModel model, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede crear inventario");
        }
        return ResponseEntity.status(201).body(service.guardar(model));
    }

    @Operation(
        summary = "Actualizar inventario",
        description = "Actualiza los datos de stock de una película. Solo accesible con rol ADMIN. " +
                      "Retorna 404 si el inventario no existe."
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody InventarioModel model, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede actualizar inventario");
        }
        return ResponseEntity.ok(service.actualizar(id, model));
    }

    @Operation(
        summary = "Eliminar inventario",
        description = "Elimina el registro de inventario de una película. Solo accesible con rol ADMIN."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id, @RequestParam String rol) {
        if (!rol.equals("ADMIN")) {
            return ResponseEntity.status(403).body("Solo un ADMIN puede eliminar inventario");
        }
        service.eliminar(id);
        return ResponseEntity.ok("Inventario eliminado con exito");
    }

    @Operation(
        summary = "Reducir stock disponible",
        description = "Reduce en 1 el stock_disponible e incrementa en 1 el stock_arrendadas. " +
                      "Es llamado automáticamente por el servicio de Rental al crear un arriendo. " +
                      "Retorna 400 si no hay stock disponible (stock_disponible = 0)."
    )
    @PostMapping("/reducir/{idPelicula}")
    public ResponseEntity<String> reducirStock(@PathVariable Long idPelicula) {
        service.reducirStock(idPelicula);
        return ResponseEntity.ok("Stock reducido exitosamente");
    }

    @Operation(
        summary = "Aumentar stock disponible",
        description = "Incrementa en 1 el stock_disponible y reduce en 1 el stock_arrendadas. " +
                      "Es llamado automáticamente por el servicio de Rental al devolver una película. " +
                      "Retorna 400 si no hay unidades arrendadas para devolver."
    )
    @PutMapping("/aumentar/{idPelicula}")
    public ResponseEntity<String> aumentarStock(@PathVariable Long idPelicula) {
        service.aumentarStock(idPelicula);
        return ResponseEntity.ok("Stock restaurado exitosamente");
    }
}
