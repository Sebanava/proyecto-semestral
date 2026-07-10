package com.arriendo.inventario.controller;

import com.arriendo.inventario.Inventario;
import com.arriendo.inventario.service.InventarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InventarioController.class)
class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InventarioService service;

    @Test
    void obtenerTodo_retornaOk() throws Exception {
        Inventario inventario = new Inventario();
        inventario.setId(1L);
        inventario.setStock_total(10);
        inventario.setStock_disponible(5);
        inventario.setStock_arrendadas(5);
        inventario.setId_pelicula(1L);

        when(service.obtenerTodo()).thenReturn(List.of(inventario));

        mockMvc.perform(get("/inventario"))
                .andExpect(status().isOk());
    }
}
