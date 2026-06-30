package com.arriendo.clientes.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Datos requeridos para crear o actualizar un cliente")
public class ClienteModel {

    @Schema(description = "RUT del cliente con dígito verificador", example = "12345678-9")
    @NotBlank(message = "El rut no puede estar vacío")
    private String rut;

    @Schema(description = "Nombre completo del cliente", example = "Juan Pérez")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Schema(description = "Correo electrónico único del cliente", example = "juan.perez@email.com")
    @Email(message = "Debe ser un email válido")
    @NotBlank(message = "El email no puede estar vacío")
    private String email;

    @Schema(description = "Número de teléfono sin código de país", example = "987654321")
    @Min(value = 0, message = "El numero no puede ser negativo")
    private int numero;

    @Schema(description = "Dirección de residencia del cliente", example = "Av. Principal 123, Santiago")
    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;
}
