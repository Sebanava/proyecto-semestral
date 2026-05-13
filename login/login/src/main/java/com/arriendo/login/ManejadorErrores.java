package com.arriendo.login;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.arriendo.login.DTO.*;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ManejadorErrores {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> manejarErrores(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
                Map<String, String> errores = new HashMap<>();
ex.getBindingResult().getFieldErrors().forEach(error -> {
    errores.put(error.getField(), error.getDefaultMessage());
});

        ErrorDTO errorDTO = new ErrorDTO(
            LocalDateTime.now(),
            400,
            "error de validacion",
            errores,
            request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(errorDTO);
    }
}