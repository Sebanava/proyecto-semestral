package com.arriendo.rental.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "inventario", url = "http://localhost:8085")
public interface InventarioClient {

    @PostMapping("/inventario/reducir/{idPelicula}")
    void ReducirStock(@PathVariable("idPelicula") long idPelicula);

}