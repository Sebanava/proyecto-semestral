package com.arriendo.clientes;

import com.arriendo.clientes.Repository.ClienteRepository;
import com.arriendo.clientes.Service.ClienteService;
import com.arriendo.clientes.model.ClienteModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService service;

    @Test
    void guardar_cuandoEmailRepetido_lanzaExcepcion() {
        ClienteModel model = new ClienteModel();
        model.setEmail("test@test.com");
        model.setRut("12345678-9");
        when(repository.existsByEmail("test@test.com")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> service.guardar(model));
    }

    @Test
    void guardar_cuandoRutRepetido_lanzaExcepcion() {
        ClienteModel model = new ClienteModel();
        model.setEmail("nuevo@test.com");
        model.setRut("12345678-9");
        when(repository.existsByEmail("nuevo@test.com")).thenReturn(false);
        when(repository.existsByRut("12345678-9")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> service.guardar(model));
    }

    @Test
    void actualizar_cuandoClienteNoExiste_lanzaExcepcion() {
        ClienteModel model = new ClienteModel();
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.actualizar(99L, model));
    }
}
