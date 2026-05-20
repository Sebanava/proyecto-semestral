package com.arriendo.alertas.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AlertasModel {

    @NotNull(message = "id pelicula no puede estar vacio")
    private long id_pelicula;

    @NotBlank(message = "Tipo no puede estar vacio ")
    private String tipo;

    @NotNull(message = "Fecha no puede estar vacio ")
    private LocalDate fecha;


}
