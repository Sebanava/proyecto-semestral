package com.arriendos.resenas;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.arriendos.resenas.DTO.ErrorDTO;
import com.arriendos.resenas.exception.RecursoNoEncontradoException;
import com.arriendos.resenas.exception.ServicioNoDisponibleException;
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

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorDTO> manejarRecursoNoEncontrado(
            RecursoNoEncontradoException ex, HttpServletRequest request) {
        ErrorDTO errorDTO = new ErrorDTO(
            LocalDateTime.now(), 404, ex.getMessage(), null, request.getRequestURI());
        return ResponseEntity.status(404).body(errorDTO);
    }

    @ExceptionHandler(ServicioNoDisponibleException.class)
    public ResponseEntity<ErrorDTO> manejarServicioNoDisponible(
            ServicioNoDisponibleException ex, HttpServletRequest request) {
        ErrorDTO errorDTO = new ErrorDTO(
            LocalDateTime.now(), 503, ex.getMessage(), null, request.getRequestURI());
        return ResponseEntity.status(503).body(errorDTO);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO> manejarRuntimeException(
            RuntimeException ex,
            HttpServletRequest request) {

        String mensaje = ex.getMessage() != null ? ex.getMessage() : "";
        boolean esNoEncontrado = mensaje.toLowerCase().contains("no encontrado");
        int codigo = esNoEncontrado ? 404 : 400;

        ErrorDTO errorDTO = new ErrorDTO(
            LocalDateTime.now(),
            codigo,
            mensaje,
            null,
            request.getRequestURI()
        );

        return ResponseEntity.status(codigo).body(errorDTO);
    }
}