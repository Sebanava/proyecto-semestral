package com.arriendo.clientes.controller;

import com.arriendo.clientes.Cliente;
import com.arriendo.clientes.Controller.ClienteController;
import com.arriendo.clientes.Service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClienteService service;

    @Test
    void obtenertodo_retornaOk() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setRut("12345678-9");
        cliente.setNombre("Juan Perez");
        cliente.setEmail("juan.perez@mail.com");
        cliente.setNumero(912345678);
        cliente.setDireccion("Av. Siempre Viva 123");

        when(service.obtenertodo()).thenReturn(List.of(cliente));

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk());
    }
}
