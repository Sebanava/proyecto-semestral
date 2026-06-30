package com.arriendo.rental.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "INVENTARIO")
public interface InventarioClient {

    @PostMapping("/inventario/reducir/{idPelicula}")
    void ReducirStock(@PathVariable("idPelicula") long idPelicula);

    @PutMapping("/inventario/aumentar/{idPelicula}")
    void AumentarStock(@PathVariable long idPelicula);

}