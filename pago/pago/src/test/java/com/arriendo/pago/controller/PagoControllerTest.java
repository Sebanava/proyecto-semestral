package com.arriendo.pago.controller;

import com.arriendo.pago.pago;
import com.arriendo.pago.service.PagoService;
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

@WebMvcTest(PagoController.class)
class PagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PagoService service;

    @Test
    void obtenerTodo_retornaOk() throws Exception {
        pago pagoTest = new pago();
        pagoTest.setId(1L);
        pagoTest.setId_rental(10L);
        pagoTest.setResultado("aprobado");
        pagoTest.setFecha(LocalDate.now());

        when(service.obtenerTodo()).thenReturn(List.of(pagoTest));

        mockMvc.perform(get("/pago"))
                .andExpect(status().isOk());
    }
}
