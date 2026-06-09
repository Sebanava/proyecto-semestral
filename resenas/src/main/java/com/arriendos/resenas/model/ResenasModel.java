package com.arriendos.resenas.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResenasModel {

    @NotBlank(message = "No puede estar vacio")
    private String titulo;

    @NotBlank(message = "No puede estar vacio")
    private String comentario;

    @Min(value = 0, message = "La calificación mínima es 0")
    @Max(value = 10, message = "La calificación máxima es 10")
    private int calificaciones;
}
