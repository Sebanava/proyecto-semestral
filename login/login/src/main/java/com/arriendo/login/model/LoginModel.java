package com.arriendo.login.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginModel {

    @NotBlank(message = "El apellido no puede estar vacío")
    private String apellido;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre ;

    @NotBlank(message = "El email no puede estar vacía")
    private String email;

    @Min(value = 0, message = "el numero non puede estar vacio ")
    private int numero;
    
    @NotBlank(message = "la contraseña es obligatoria")
    private String password;

    @NotBlank(message = "El rut es obligatoria")
    private String rut;
    

}
