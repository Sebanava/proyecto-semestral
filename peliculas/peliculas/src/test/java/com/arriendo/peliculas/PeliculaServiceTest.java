package com.arriendo.peliculas;

import com.arriendo.peliculas.Repository.PeliculaRepository;
import com.arriendo.peliculas.model.PeliculaModel;
import com.arriendo.peliculas.service.PeliculaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeliculaServiceTest {

    @Mock
    private PeliculaRepository repository;

    @InjectMocks
    private PeliculaService service;

    @Test
    void obtenerPorId_cuandoExiste_retornaPelicula() {
        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo("Inception");
        when(repository.findById(1L)).thenReturn(Optional.of(pelicula));

        Pelicula resultado = service.obtenerPorId(1L);

        assertEquals("Inception", resultado.getTitulo());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_lanzaExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.obtenerPorId(99L));
    }

    @Test
    void guardar_cuandoTituloRepetido_lanzaExcepcion() {
        PeliculaModel model = new PeliculaModel();
        model.setTitulo("Inception");
        when(repository.existsByTitulo("Inception")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> service.guardar(model));
    }

    @Test
    void guardar_cuandoTituloNuevo_guardaCorrectamente() {
        PeliculaModel model = new PeliculaModel();
        model.setTitulo("Avatar");
        model.setCategoria("Accion");
        model.setDuracion("162 min");
        model.setAnio("2009");
        model.setDirector("James Cameron");
        model.setStock(10);
        model.setPrecio(5000);

        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo("Avatar");

        when(repository.existsByTitulo("Avatar")).thenReturn(false);
        when(repository.save(any(Pelicula.class))).thenReturn(pelicula);

        Pelicula resultado = service.guardar(model);

        assertEquals("Avatar", resultado.getTitulo());
        verify(repository, times(1)).save(any(Pelicula.class));
    }
    
}
