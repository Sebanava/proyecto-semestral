package com.arriendo.login.DTO;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.Data;

@Data
public class ErrorDTO {

    private Map<String, String> errores;
    private LocalDateTime timestamp;
    private int status;
    private String mensaje;
    private String path;

    public ErrorDTO(LocalDateTime timestamp, int status, String mensaje, Map<String, String> errores, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.mensaje = mensaje;
        this.errores = errores;
        this.path = path;
    }
}