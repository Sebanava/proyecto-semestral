package com.arriendo.pago;

import com.arriendo.pago.client.RentalClient;
import com.arriendo.pago.exception.RecursoNoEncontradoException;
import com.arriendo.pago.exception.ServicioNoDisponibleException;
import com.arriendo.pago.model.PagoModel;
import com.arriendo.pago.repository.PagoRepository;
import com.arriendo.pago.service.PagoService;
import com.arriendo.pago.DTO.RentalDTO;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository repository;

    @Mock
    private RentalClient rentalClient;

    @InjectMocks
    private PagoService service;

    @Test
    void procesarPago_cuandoTarjetaInvalida_lanzaExcepcion() {
        PagoModel model = new PagoModel();
        model.setNumero_tarjeta("1234");
        model.setCvv("123");
        model.setFecha_vencimiento("12/26");
        model.setId_rental(1L);
        assertThrows(RuntimeException.class, () -> service.procesarPago(model));
    }

    @Test
    void procesarPago_cuandoCvvInvalido_lanzaExcepcion() {
        PagoModel model = new PagoModel();
        model.setNumero_tarjeta("1234567890123456");
        model.setCvv("12");
        model.setFecha_vencimiento("12/26");
        model.setId_rental(1L);
        assertThrows(RuntimeException.class, () -> service.procesarPago(model));
    }

    @Test
    void procesarPago_cuandoRentalYaPagado_lanzaExcepcion() {
        PagoModel model = new PagoModel();
        model.setNumero_tarjeta("1234567890123456");
        model.setCvv("123");
        model.setFecha_vencimiento("12/26");
        model.setId_rental(1L);
        RentalDTO rental = new RentalDTO();
        rental.setEstado("pagado");
        when(rentalClient.obtenerRental(1L)).thenReturn(rental);
        assertThrows(RuntimeException.class, () -> service.procesarPago(model));
    }

    @Test
    void procesarPago_cuandoDatosValidos_retornaAprobado() {
        PagoModel model = new PagoModel();
        model.setNumero_tarjeta("1234567890123456");
        model.setCvv("123");
        model.setFecha_vencimiento("12/26");
        model.setId_rental(1L);
        RentalDTO rental = new RentalDTO();
        rental.setEstado("pendiente");
        rental.setMonto(5000);
        when(rentalClient.obtenerRental(1L)).thenReturn(rental);
        when(repository.save(any(pago.class))).thenReturn(new pago());
        doNothing().when(rentalClient).pagarRental(1L);
        String resultado = service.procesarPago(model);
        assertTrue(resultado.contains("aprobado") || resultado.contains("5000"));
    }

    @Test
    void procesarPago_cuandoRentalServiceNoDisponible_lanzaServicioNoDisponibleException() {
        PagoModel model = new PagoModel();
        model.setNumero_tarjeta("1234567890123456");
        model.setCvv("123");
        model.setFecha_vencimiento("12/26");
        model.setId_rental(1L);

        when(rentalClient.obtenerRental(1L))
            .thenThrow(mock(FeignException.class));

        assertThrows(ServicioNoDisponibleException.class, () -> service.procesarPago(model));
    }

    @Test
    void procesarPago_cuandoRentalNotFound_lanzaRecursoNoEncontradoException() {
        PagoModel model = new PagoModel();
        model.setNumero_tarjeta("1234567890123456");
        model.setCvv("123");
        model.setFecha_vencimiento("12/26");
        model.setId_rental(99L);

        when(rentalClient.obtenerRental(99L))
            .thenThrow(mock(FeignException.NotFound.class));

        assertThrows(RecursoNoEncontradoException.class, () -> service.procesarPago(model));
    }
}
