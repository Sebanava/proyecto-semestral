package com.arriendo.clientes.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClienteModel {

    @NotBlank(message = "El rut no puede estar vacío")
    private String rut;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Email(message = "Debe ser un email válido")
    @NotBlank(message = "El email no puede estar vacío")
    private String email;

    @Min(value = 0, message = "El numero no puede ser negativo")
    private int numero;

    @NotBlank(message = "La dirección no puede estar vacía")
    private String direccion;
}