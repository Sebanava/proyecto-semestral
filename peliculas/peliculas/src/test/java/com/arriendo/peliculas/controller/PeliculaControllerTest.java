package com.arriendo.peliculas.controller;

import com.arriendo.peliculas.Pelicula;
import com.arriendo.peliculas.service.PeliculaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PeliculaController.class)
class PeliculaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PeliculaService service;

    @Test
    void obtenertodo_retornaOk() throws Exception {
        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo("Gladiador");
        pelicula.setCategoria("Accion");
        pelicula.setDuracion("155 min");
        pelicula.setAnio("2000");
        pelicula.setDirector("Ridley Scott");
        pelicula.setStock(5);
        pelicula.setPrecio(3000);

        when(service.obtenertodo()).thenReturn(List.of(pelicula));

        mockMvc.perform(get("/peliculas"))
                .andExpect(status().isOk());
    }
}
