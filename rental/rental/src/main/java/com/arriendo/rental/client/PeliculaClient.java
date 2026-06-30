package com.arriendo.rental.client;

import com.arriendo.rental.DTO.PeliculaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PELICULAS")
public interface PeliculaClient {

    @GetMapping("/peliculas/{id}")
    PeliculaDTO obtenerPorId(@PathVariable Long id);
}