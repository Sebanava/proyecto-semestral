package com.arriendo.alertas.controller;

import com.arriendo.alertas.alertas;
import com.arriendo.alertas.service.AlertasService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AlertasController.class)
class AlertasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AlertasService service;

    @Test
    void obtenerTodo_retornaOk() throws Exception {
        alertas alertaTest = new alertas();
        alertaTest.setId(1L);
        alertaTest.setId_pelicula(1L);
        alertaTest.setTipo("STOCK BAJO");
        alertaTest.setFecha(LocalDate.now());

        when(service.ObtenerTodo()).thenReturn(List.of(alertaTest));

        mockMvc.perform(get("/alertas").param("rol", "ADMIN"))
                .andExpect(status().isOk());
    }
}
