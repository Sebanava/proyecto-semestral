package com.arriendo.rental;

import com.arriendo.rental.client.InventarioClient;
import com.arriendo.rental.client.PeliculaClient;
import com.arriendo.rental.exception.RecursoNoEncontradoException;
import com.arriendo.rental.exception.ServicioNoDisponibleException;
import com.arriendo.rental.model.RentalModel;
import com.arriendo.rental.repository.RentalRepository;
import com.arriendo.rental.service.RentalService;
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
class RentalServiceTest {

    @Mock
    private RentalRepository repository;

    @Mock
    private PeliculaClient peliculaClient;

    @Mock
    private InventarioClient inventarioClient;

    @InjectMocks
    private RentalService service;

    @Test
    void obtenerPorId_cuandoNoExiste_lanzaExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.obtenerPorId(99L));
    }

    @Test
    void guardar_cuandoFechaFinAntesDeInicio_lanzaExcepcion() {
        RentalModel model = new RentalModel();
        model.setId_cliente(1L);
        model.setId_pelicula(1L);
        model.setFecha_inicio(LocalDate.of(2025, 6, 10));
        model.setFecha_fin(LocalDate.of(2025, 6, 5));
        model.setEstado("pendiente");
        assertThrows(RuntimeException.class, () -> service.guardar(model));
    }

    @Test
    void pagar_cuandoYaEstaPagado_lanzaExcepcion() {
        rental rentalExistente = new rental();
        rentalExistente.setEstado("pagado");
        when(repository.findById(1L)).thenReturn(Optional.of(rentalExistente));
        assertThrows(RuntimeException.class, () -> service.pagar(1L));
    }

    @Test
    void devolver_cuandoYaEstaDevuelto_lanzaExcepcion() {
        rental rentalExistente = new rental();
        rentalExistente.setEstado("devuelto");
        when(repository.findById(1L)).thenReturn(Optional.of(rentalExistente));
        assertThrows(RuntimeException.class, () -> service.devolver(1L));
    }

    @Test
    void guardar_cuandoPeliculaServiceNoDisponible_lanzaServicioNoDisponibleException() {
        RentalModel model = new RentalModel();
        model.setId_cliente(1L);
        model.setId_pelicula(1L);
        model.setFecha_inicio(LocalDate.of(2026, 7, 1));
        model.setFecha_fin(LocalDate.of(2026, 7, 7));
        model.setEstado("pendiente");

        when(peliculaClient.obtenerPorId(1L))
            .thenThrow(mock(FeignException.class));

        assertThrows(ServicioNoDisponibleException.class, () -> service.guardar(model));
    }

    @Test
    void guardar_cuandoPeliculaNotFound_lanzaRecursoNoEncontradoException() {
        RentalModel model = new RentalModel();
        model.setId_cliente(1L);
        model.setId_pelicula(99L);
        model.setFecha_inicio(LocalDate.of(2026, 7, 1));
        model.setFecha_fin(LocalDate.of(2026, 7, 7));
        model.setEstado("pendiente");

        when(peliculaClient.obtenerPorId(99L))
            .thenThrow(mock(FeignException.NotFound.class));

        assertThrows(RecursoNoEncontradoException.class, () -> service.guardar(model));
    }
}
