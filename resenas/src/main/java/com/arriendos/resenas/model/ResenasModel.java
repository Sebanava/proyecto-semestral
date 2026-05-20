package com.arriendos.resenas.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResenasModel {

    @NotBlank(message = "No puede estar vacio")
    private String titulo;

    @NotBlank(message = "no puede estar vacio")
    private String comentario;

    @Min(value = 0, message = "no puede estar vacio")
    private int calificaciones;

    

}
