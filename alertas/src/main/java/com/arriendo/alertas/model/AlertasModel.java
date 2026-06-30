package com.arriendo.alertas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Datos requeridos para crear o actualizar una alerta de inventario")
public class AlertasModel {

    @Schema(description = "ID de la película asociada a la alerta", example = "1")
    @NotNull(message = "id pelicula no puede estar vacio")
    private long id_pelicula;

    @Schema(description = "Tipo de alerta generada", example = "STOCK BAJO", allowableValues = {"SIN STOCK", "STOCK BAJO", "STOCK SUFICIENTE"})
    @NotBlank(message = "Tipo no puede estar vacio ")
    private String tipo;

    @Schema(description = "Fecha en que se generó la alerta (formato YYYY-MM-DD)", example = "2026-07-01")
    @NotNull(message = "Fecha no puede estar vacio ")
    private LocalDate fecha;
}
