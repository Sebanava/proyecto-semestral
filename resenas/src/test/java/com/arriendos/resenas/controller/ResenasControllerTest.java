package com.arriendos.resenas.controller;

import com.arriendos.resenas.Resenas;
import com.arriendos.resenas.service.ResenasService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ResenasController.class)
class ResenasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ResenasService service;

    @Test
    void obtenerTodo_retornaOk() throws Exception {
        Resenas resenas = new Resenas();
        resenas.setTitulo("Gladiador");
        resenas.setComentario("Muy buena pelicula");
        resenas.setCalificaciones(9);

        when(service.ObtenerTodo()).thenReturn(List.of(resenas));

        mockMvc.perform(get("/resenas"))
                .andExpect(status().isOk());
    }
}
