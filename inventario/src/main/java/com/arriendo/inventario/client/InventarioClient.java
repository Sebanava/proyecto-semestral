package com.arriendo.inventario.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.arriendo.inventario.DTO.ErrorDTO;

@FeignClient(name = "peliculas", url = "http://localhost:8080")
public interface InventarioClient {

    @PostMapping("/inventario/reducir/{idPelicula}")
    void ReducirStock(@PathVariable("idPelicula") long idPelicula);


}
