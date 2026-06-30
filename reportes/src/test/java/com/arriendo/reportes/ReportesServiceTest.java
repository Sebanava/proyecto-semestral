package com.arriendo.reportes;

import com.arriendo.reportes.cliente.ClienteClient;
import com.arriendo.reportes.cliente.PagoClient;
import com.arriendo.reportes.cliente.RentalClient;
import com.arriendo.reportes.exception.ServicioNoDisponibleException;
import com.arriendo.reportes.model.ReportesModel;
import com.arriendo.reportes.repository.ReportesRepository;
import com.arriendo.reportes.service.ReportesService;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReportesServiceTest {

    @Mock
    private ReportesRepository repository;

    @Mock
    private ClienteClient clienteClient;

    @Mock
    private PagoClient pagoClient;

    @Mock
    private RentalClient rentalClient;

    @InjectMocks
    private ReportesService service;

    @Test
    void obtenerPorId_cuandoNoExiste_lanzaExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.ObtenerPorId(99L));
    }

    @Test
    void actualizar_cuandoReporteNoExiste_lanzaExcepcion() {
        ReportesModel model = new ReportesModel();
        model.setTipo("VENTAS");
        model.setEstado("generado");
        model.setFecha(LocalDate.now());
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.actualizar(99L, model));
    }

    @Test
    void eliminar_cuandoReporteNoExiste_lanzaExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.eliminar(99L));
    }

    @Test
    void resumen_cuandoClienteServiceNoDisponible_lanzaServicioNoDisponibleException() {
        when(clienteClient.ObtenerTodo())
            .thenThrow(mock(FeignException.class));

        assertThrows(ServicioNoDisponibleException.class, () -> service.resumen());
    }

    @Test
    void resumen_cuandoRentalServiceNoDisponible_lanzaServicioNoDisponibleException() {
        when(clienteClient.ObtenerTodo()).thenReturn(java.util.List.of());
        when(rentalClient.ObtenerTodo())
            .thenThrow(mock(FeignException.class));

        assertThrows(ServicioNoDisponibleException.class, () -> service.resumen());
    }

    @Test
    void resumen_cuandoPagoServiceNoDisponible_lanzaServicioNoDisponibleException() {
        when(clienteClient.ObtenerTodo()).thenReturn(java.util.List.of());
        when(rentalClient.ObtenerTodo()).thenReturn(java.util.List.of());
        when(pagoClient.ObtenerTodo())
            .thenThrow(mock(FeignException.class));

        assertThrows(ServicioNoDisponibleException.class, () -> service.resumen());
    }
}
