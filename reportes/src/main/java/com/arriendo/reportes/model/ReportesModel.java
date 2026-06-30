package com.arriendo.reportes.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Datos requeridos para crear o actualizar un reporte del sistema")
public class ReportesModel {

    @Schema(description = "Tipo de reporte a generar", example = "VENTAS", allowableValues = {"VENTAS", "ARRIENDOS", "CLIENTES", "INVENTARIO"})
    @NotBlank(message = "No puede estar vacio")
    private String tipo;

    @Schema(description = "Estado actual del reporte", example = "generado", allowableValues = {"pendiente", "generado", "entregado"})
    @NotBlank(message = "No puede estar vacio")
    private String estado;

    @Schema(description = "Fecha de generación del reporte (formato YYYY-MM-DD)", example = "2026-07-01")
    @NotNull(message = "No puede estar vaio")
    private LocalDate fecha;
}
