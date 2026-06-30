package com.arriendo.peliculas.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PeliculaModel {

    @NotBlank(message = "El titulo no puede estar vacío")
    private String titulo;

    @NotBlank(message = "La categoria no puede estar vacía")
    private String categoria;

    @NotBlank(message = "La duracion no puede estar vacía")
    private String duracion;

    @NotBlank(message = "El año no puede estar vacío")
    private String anio;

    @NotBlank(message = "El director no puede estar vacío")
    private String director;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private int stock;

    @Min(value = 0, message = "El precio no puede ser negativo")
    private double precio;
}