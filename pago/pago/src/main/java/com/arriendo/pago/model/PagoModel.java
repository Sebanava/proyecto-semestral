package com.arriendo.pago.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PagoModel {

    @NotNull(message = "El id_rental no puede estar vacío")
    private Long id_rental;

    @NotBlank(message = "El numero de tarjeta no puede estar vacío")
    private String numero_tarjeta;

    @NotBlank(message = "El CVV no puede estar vacío")
    private String cvv;

    @NotBlank(message = "La fecha de vencimiento no puede estar vacía")
    private String fecha_vencimiento;
}