package com.arriendo.login.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Datos requeridos para actualizar un usuario del sistema")
public class LoginModel {

    @Schema(description = "Apellido del usuario", example = "Pérez")
    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellido;

    @Schema(description = "Nombre del usuario", example = "Juan")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@email.com")
    @NotBlank(message = "El email no puede estar vacía")
    private String email;

    @Schema(description = "Número de teléfono", example = "987654321")
    @Min(value = 0, message = "el numero non puede estar vacio ")
    private int numero;

    @Schema(description = "Contraseña del usuario", example = "miPassword123")
    @NotBlank(message = "la contraseña es obligatoria")
    private String password;

    @Schema(description = "RUT del usuario", example = "12345678-9")
    @NotBlank(message = "El rut es obligatoria")
    private String rut;

    @Schema(description = "Rol del usuario en el sistema", example = "CLIENTE", allowableValues = {"ADMIN", "CLIENTE"})
    @NotBlank(message = "El campo Rol no puede estar vacio ")
    private String rol;
}
