package com.arriendo.alertas.cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.arriendo.alertas.DTO.PeliculaDTO;



@FeignClient(name = "peliculas", url = "http://localhost:8080")
public interface PeliculaClient {

@GetMapping("/peliculas/titulo/{titulo}")
PeliculaDTO obtenerPorTitulo(@PathVariable String titulo);

}
