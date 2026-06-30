package com.arriendo.alertas;

import com.arriendo.alertas.cliente.InventarioClient;
import com.arriendo.alertas.cliente.PeliculaClient;
import com.arriendo.alertas.exception.RecursoNoEncontradoException;
import com.arriendo.alertas.exception.ServicioNoDisponibleException;
import com.arriendo.alertas.model.AlertasModel;
import com.arriendo.alertas.repository.AlertasRepository;
import com.arriendo.alertas.service.AlertasService;
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
class AlertasServiceTest {

    @Mock
    private AlertasRepository repository;

    @Mock
    private PeliculaClient peliculaClient;

    @Mock
    private InventarioClient inventarioClient;

    @InjectMocks
    private AlertasService service;

    @Test
    void obtenerPorIncidencia_cuandoNoExiste_lanzaExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.ObtenerPorIncidencia(99L));
    }

    @Test
    void actualizar_cuandoAlertaNoExiste_lanzaExcepcion() {
        AlertasModel model = new AlertasModel();
        model.setTipo("CRITICA");
        model.setFecha(LocalDate.now());
        model.setId_pelicula(1L);
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.actualizar(99L, model));
    }

    @Test
    void eliminar_cuandoAlertaNoExiste_lanzaExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.eliminar(99L));
    }

    @Test
    void verificarStock_cuandoPeliculaServiceNoDisponible_lanzaServicioNoDisponibleException() {
        when(peliculaClient.obtenerPorTitulo("Inception"))
            .thenThrow(mock(FeignException.class));

        assertThrows(ServicioNoDisponibleException.class,
            () -> service.VerificarStock("Inception"));
    }

    @Test
    void verificarStock_cuandoPeliculaNotFound_lanzaRecursoNoEncontradoException() {
        when(peliculaClient.obtenerPorTitulo("PeliculaInexistente"))
            .thenThrow(mock(FeignException.NotFound.class));

        assertThrows(RecursoNoEncontradoException.class,
            () -> service.VerificarStock("PeliculaInexistente"));
    }
}
