package com.arriendo.alertas.cliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.arriendo.alertas.DTO.InventarioDTO;

@FeignClient(name = "inventario", url = "http://localhost:8085")
public interface InventarioClient {

    @GetMapping("/inventario/{id}")
    InventarioDTO obtenerPorId(@PathVariable long id);
}