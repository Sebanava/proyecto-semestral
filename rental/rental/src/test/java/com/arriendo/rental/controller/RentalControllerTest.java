package com.arriendo.rental.controller;

import com.arriendo.rental.rental;
import com.arriendo.rental.service.RentalService;
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

@WebMvcTest(RentalController.class)
class RentalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RentalService service;

    @Test
    void obtenertodo_retornaOk() throws Exception {
        rental arriendo = new rental();
        arriendo.setId_cliente(1L);
        arriendo.setId_pelicula(1L);
        arriendo.setFecha_inicio(LocalDate.of(2026, 7, 1));
        arriendo.setFecha_fin(LocalDate.of(2026, 7, 5));
        arriendo.setEstado("pendiente");
        arriendo.setMonto(3000);

        when(service.obtenertodo()).thenReturn(List.of(arriendo));

        mockMvc.perform(get("/rental"))
                .andExpect(status().isOk());
    }
}
