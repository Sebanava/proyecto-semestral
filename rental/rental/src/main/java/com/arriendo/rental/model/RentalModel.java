package com.arriendo.rental.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;


@Data
public class RentalModel {

        @NotNull(message = "id_cliente no puede estar vacío")
        private Long id_cliente;

        @NotNull(message = "id_pelicula no puede estar vacío")
        private Long id_pelicula;

        @NotNull(message = "fecha_inicio no puede estar vacía")
        private LocalDate fecha_inicio;

        @NotNull(message = "fecha_fin no puede estar vacía")
        private LocalDate fecha_fin;

        @NotBlank( message = "estado non puede estar vacio ")
        private String estado;
        


}