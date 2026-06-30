package com.arriendo.peliculas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Datos requeridos para crear o actualizar una película")
public class PeliculaModel {

    @Schema(description = "Título único de la película", example = "Inception")
    @NotBlank(message = "El titulo no puede estar vacío")
    private String titulo;

    @Schema(description = "Categoría o género de la película", example = "Ciencia Ficcion")
    @NotBlank(message = "La categoria no puede estar vacía")
    private String categoria;

    @Schema(description = "Duración de la película", example = "148 min")
    @NotBlank(message = "La duracion no puede estar vacía")
    private String duracion;

    @Schema(description = "Año de estreno", example = "2010")
    @NotBlank(message = "El año no puede estar vacío")
    private String anio;

    @Schema(description = "Nombre del director", example = "Christopher Nolan")
    @NotBlank(message = "El director no puede estar vacío")
    private String director;

    @Schema(description = "Unidades disponibles en bodega", example = "10")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private int stock;

    @Schema(description = "Precio de arriendo en pesos chilenos", example = "3500.0")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private double precio;
}
