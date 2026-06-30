package com.arriendo.rental.exception;

public class ServicioNoDisponibleException extends RuntimeException {
    public ServicioNoDisponibleException(String message) {
        super(message);
    }
}
