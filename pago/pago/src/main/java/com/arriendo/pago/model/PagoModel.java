package com.arriendo.pago.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Datos requeridos para procesar el pago de un arriendo")
public class PagoModel {

    @Schema(description = "ID del arriendo a pagar", example = "1")
    @NotNull(message = "El id_rental no puede estar vacío")
    private Long id_rental;

    @Schema(description = "Número de tarjeta de crédito o débito (exactamente 16 dígitos)", example = "1234567890123456")
    @NotBlank(message = "El numero de tarjeta no puede estar vacío")
    private String numero_tarjeta;

    @Schema(description = "Código de seguridad de la tarjeta (exactamente 3 dígitos)", example = "123")
    @NotBlank(message = "El CVV no puede estar vacío")
    private String cvv;

    @Schema(description = "Fecha de vencimiento de la tarjeta (formato MM/YY)", example = "12/26")
    @NotBlank(message = "La fecha de vencimiento no puede estar vacía")
    private String fecha_vencimiento;
}
