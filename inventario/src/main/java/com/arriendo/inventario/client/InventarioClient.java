package com.arriendo.inventario.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "PELICULAS")
public interface InventarioClient {

    @PostMapping("/inventario/reducir/{idPelicula}")
    void ReducirStock(@PathVariable("idPelicula") long idPelicula);


}
