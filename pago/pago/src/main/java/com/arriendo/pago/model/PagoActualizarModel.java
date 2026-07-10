package com.arriendo.pago.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Datos requeridos para actualizar un pago existente")
public class PagoActualizarModel {

    @Schema(description = "Resultado del pago", example = "aprobado", allowableValues = {"aprobado", "rechazado"})
    @NotBlank(message = "El resultado no puede estar vacío")
    private String resultado;

    @Schema(description = "Fecha del pago", example = "2026-07-10")
    @NotNull(message = "La fecha no puede estar vacía")
    private LocalDate fecha;
}
