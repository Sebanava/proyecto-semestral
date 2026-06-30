package com.arriendo.rental.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;
import jakarta.validation.constraints.NotNull;

@Data
@Schema(description = "Datos requeridos para crear un nuevo arriendo")
public class RentalModel {

    @Schema(description = "ID del cliente que realiza el arriendo", example = "1")
    @NotNull(message = "id_cliente no puede estar vacío")
    private Long id_cliente;

    @Schema(description = "ID de la película a arrendar", example = "1")
    @NotNull(message = "id_pelicula no puede estar vacío")
    private Long id_pelicula;

    @Schema(description = "Fecha de inicio del arriendo (formato YYYY-MM-DD)", example = "2026-07-01")
    @NotNull(message = "fecha_inicio no puede estar vacía")
    private LocalDate fecha_inicio;

    @Schema(description = "Fecha de término del arriendo, debe ser posterior a fecha_inicio", example = "2026-07-07")
    @NotNull(message = "fecha_fin no puede estar vacía")
    private LocalDate fecha_fin;

    @Schema(description = "Estado inicial del arriendo", example = "pendiente", allowableValues = {"pendiente", "pagado", "devuelto"})
    @NotBlank(message = "estado non puede estar vacio ")
    private String estado;
}
