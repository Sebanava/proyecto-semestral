    package com.arriendo.reportes.cliente;

    import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;



import com.arriendo.reportes.DTO.ClienteDTO;


    @FeignClient(name = "CLIENTES")
    public interface ClienteClient {

        @GetMapping("/clientes")
        List<ClienteDTO>ObtenerTodo();

    }
