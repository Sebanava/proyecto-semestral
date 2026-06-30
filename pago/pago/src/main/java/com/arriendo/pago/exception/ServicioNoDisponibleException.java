package com.arriendo.pago.exception;

public class ServicioNoDisponibleException extends RuntimeException {
    public ServicioNoDisponibleException(String message) {
        super(message);
    }
}
