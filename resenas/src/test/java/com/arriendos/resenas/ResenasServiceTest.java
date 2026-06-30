package com.arriendos.resenas;

import com.arriendos.resenas.repository.ResenasRepository;
import com.arriendos.resenas.service.ResenasService;
import com.arriendos.resenas.cliente.PeliculaClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResenasServiceTest {

    @Mock
    private ResenasRepository repository;

    @Mock
    private PeliculaClient peliculaClient;

    @InjectMocks
    private ResenasService service;

    @Test
    void obtenerPorId_cuandoNoExiste_lanzaExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.obtenerPorId(99L));
    }

    @Test
    void obtenerPorTitulo_cuandoNoHayResenas_lanzaExcepcion() {
        when(repository.findByTitulo("PeliculaInexistente"))
            .thenReturn(Collections.emptyList());

        assertThrows(RuntimeException.class,
            () -> service.ObtenerPorTitulo("PeliculaInexistente"));
    }

    @Test
    void eliminar_cuandoResenaNoExiste_lanzaExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.eliminar(99L));
    }
}
