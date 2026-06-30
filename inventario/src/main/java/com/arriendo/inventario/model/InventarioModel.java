package com.arriendo.inventario.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Datos requeridos para crear o actualizar el inventario de una película")
public class InventarioModel {

    @Schema(description = "Total de unidades físicas de la película", example = "10")
    @Min(value = 0, message = "el total no puede estar vacio")
    private int stock_total;

    @Schema(description = "Unidades actualmente disponibles para arrendar", example = "8")
    @Min(value = 0, message = "el stock disponible no puede estar en blanco")
    private int stock_disponible;

    @Schema(description = "Unidades actualmente arrendadas a clientes", example = "2")
    @Min(value = 0, message = "el stock arrendadas no puede estar en blanco")
    private int stock_arrendadas;

    @Schema(description = "ID de la película asociada a este inventario", example = "1")
    @NotNull(message = "el id de peliculas no puede estar en blanco")
    private long id_pelicula;
}
