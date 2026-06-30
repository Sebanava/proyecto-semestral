package com.arriendo.login.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RespuestaLoginDTO {
    private String nombre;
    private String email;
    private String rol;
}
