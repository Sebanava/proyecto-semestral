package com.arriendo.reportes.controller;

import com.arriendo.reportes.Reportes;
import com.arriendo.reportes.service.ReportesService;
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

@WebMvcTest(ReportesController.class)
class ReportesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReportesService service;

    @Test
    void obtenerTodo_retornaOk() throws Exception {
        Reportes reportes = new Reportes();
        reportes.setTipo("Ventas");
        reportes.setEstado("generado");
        reportes.setFecha(LocalDate.now());

        when(service.ObtenerTodo()).thenReturn(List.of(reportes));

        mockMvc.perform(get("/reportes").param("rol", "ADMIN"))
                .andExpect(status().isOk());
    }
}
