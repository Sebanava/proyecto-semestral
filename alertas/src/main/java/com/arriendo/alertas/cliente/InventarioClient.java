package com.arriendo.alertas.cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.arriendo.alertas.DTO.InventarioDTO;

@FeignClient(name = "INVENTARIO")
public interface InventarioClient {

    @GetMapping("/inventario/pelicula/{idPelicula}")
    InventarioDTO obtenerPorId(@PathVariable long idPelicula);
}