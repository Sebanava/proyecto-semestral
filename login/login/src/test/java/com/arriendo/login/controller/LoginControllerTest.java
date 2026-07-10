package com.arriendo.login.controller;

import com.arriendo.login.login;
import com.arriendo.login.LoginController.LoginController;
import com.arriendo.login.service.LoginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LoginService service;

    @Test
    void obtenertodo_retornaOk() throws Exception {
        login usuario = new login();
        usuario.setNombre("Juan");
        usuario.setApellido("Perez");
        usuario.setEmail("juan.perez@mail.com");
        usuario.setNumero(912345678);
        usuario.setPassword("clave123");
        usuario.setRut("12345678-9");
        usuario.setRol("CLIENTE");

        when(service.obtenertodo()).thenReturn(List.of(usuario));

        mockMvc.perform(get("/usuario"))
                .andExpect(status().isOk());
    }
}
