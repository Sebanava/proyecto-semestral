package com.arriendo.login;

import com.arriendo.login.DTO.LoginDTO;
import com.arriendo.login.repository.LoginRepository;
import com.arriendo.login.service.LoginService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private LoginRepository repository;

    @InjectMocks
    private LoginService service;

    @Test
    void login_cuandoUsuarioNoExiste_retorna401() {
        LoginDTO dto = new LoginDTO();
        dto.setEmail("noexiste@test.com");
        dto.setPassword("1234");
        when(repository.findByEmail("noexiste@test.com")).thenReturn(Optional.empty());

        ResponseEntity<?> respuesta = service.login(dto);

        assertEquals(HttpStatus.UNAUTHORIZED, respuesta.getStatusCode());
    }

    @Test
    void login_cuandoPasswordIncorrecta_retorna401() {
        LoginDTO dto = new LoginDTO();
        dto.setEmail("user@test.com");
        dto.setPassword("incorrecta");

        login usuario = new login();
        usuario.setEmail("user@test.com");
        usuario.setPassword("correcta");
        usuario.setNombre("Juan");
        usuario.setRol("CLIENTE");

        when(repository.findByEmail("user@test.com")).thenReturn(Optional.of(usuario));

        ResponseEntity<?> respuesta = service.login(dto);

        assertEquals(HttpStatus.UNAUTHORIZED, respuesta.getStatusCode());
    }

    @Test
    void login_cuandoCredencialesCorrectas_retorna200() {
        LoginDTO dto = new LoginDTO();
        dto.setEmail("user@test.com");
        dto.setPassword("correcta");

        login usuario = new login();
        usuario.setEmail("user@test.com");
        usuario.setPassword("correcta");
        usuario.setNombre("Juan");
        usuario.setRol("CLIENTE");

        when(repository.findByEmail("user@test.com")).thenReturn(Optional.of(usuario));

        ResponseEntity<?> respuesta = service.login(dto);

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
    }
}
