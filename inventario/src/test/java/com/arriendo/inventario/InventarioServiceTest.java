package com.arriendo.inventario;

import com.arriendo.inventario.repository.InventarioRepository;
import com.arriendo.inventario.service.InventarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private InventarioRepository repository;

    @InjectMocks
    private InventarioService service;

    @Test
    void reducirStock_cuandoSinStock_lanzaExcepcion() {
        Inventario inventario = new Inventario();
        inventario.setStock_disponible(0);
        inventario.setStock_arrendadas(5);
        when(repository.findByIdPelicula(1L)).thenReturn(Optional.of(inventario));

        assertThrows(RuntimeException.class, () -> service.reducirStock(1L));
    }

    @Test
    void reducirStock_cuandoHayStock_reduceCorrectamente() {
        Inventario inventario = new Inventario();
        inventario.setStock_disponible(5);
        inventario.setStock_arrendadas(2);
        inventario.setStock_total(7);
        when(repository.findByIdPelicula(1L)).thenReturn(Optional.of(inventario));
        when(repository.save(any(Inventario.class))).thenReturn(inventario);

        service.reducirStock(1L);

        assertEquals(4, inventario.getStock_disponible());
        assertEquals(3, inventario.getStock_arrendadas());
    }

    @Test
    void aumentarStock_cuandoSinArrendadas_lanzaExcepcion() {
        Inventario inventario = new Inventario();
        inventario.setStock_disponible(5);
        inventario.setStock_arrendadas(0);
        when(repository.findByIdPelicula(1L)).thenReturn(Optional.of(inventario));

        assertThrows(RuntimeException.class, () -> service.aumentarStock(1L));
    }

    @Test
    void obtenerPorId_cuandoNoExiste_lanzaExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.obtenerPorId(99L));
    }
}
