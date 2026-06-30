package com.arriendo.alertas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arriendo.alertas.alertas;
import com.arriendo.alertas.DTO.InventarioDTO;
import com.arriendo.alertas.DTO.PeliculaDTO;
import com.arriendo.alertas.cliente.InventarioClient;
import com.arriendo.alertas.cliente.PeliculaClient;
import com.arriendo.alertas.exception.RecursoNoEncontradoException;
import com.arriendo.alertas.exception.ServicioNoDisponibleException;
import com.arriendo.alertas.model.AlertasModel;
import com.arriendo.alertas.repository.AlertasRepository;

@Service
public class AlertasService {

    @Autowired
    private AlertasRepository repository;

    @Autowired
    private PeliculaClient peliculaClient;

    @Autowired
    private InventarioClient inventarioClient;

    public List<alertas> ObtenerTodo(){
        return repository.findAll();
    }

    public alertas guardar(AlertasModel model){
        alertas alertas = new alertas();
        alertas.setId_pelicula(model.getId_pelicula());
        alertas.setTipo(model.getTipo());
        alertas.setFecha(model.getFecha());
        return repository.save(alertas);
    }

    public alertas actualizar(long id, AlertasModel model){
        alertas alertas = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Alerta no encontrada"));
        alertas.setId_pelicula(model.getId_pelicula());
        alertas.setTipo(model.getTipo());
        alertas.setFecha(model.getFecha());
        return repository.save(alertas);
    }

    public void eliminar(long id){
        repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Alerta no encontrada"));
        repository.deleteById(id);
    }

    public alertas ObtenerPorIncidencia(long id){
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Alerta no encontrada"));
    }

    public String VerificarStock(String titulo){
        PeliculaDTO pelicula;
        try {
            pelicula = peliculaClient.obtenerPorTitulo(titulo);
        } catch (feign.FeignException.NotFound e) {
            throw new RecursoNoEncontradoException(
                "La película '" + titulo + "' no existe en el catálogo");
        } catch (feign.FeignException e) {
            throw new ServicioNoDisponibleException(
                "El servicio de Películas no está disponible. Intente más tarde.");
        }

        InventarioDTO inventario;
        try {
            inventario = inventarioClient.obtenerPorId(pelicula.getId());
        } catch (feign.FeignException.NotFound e) {
            throw new RecursoNoEncontradoException(
                "No se encontró inventario para la película '" + titulo + "'");
        } catch (feign.FeignException e) {
            throw new ServicioNoDisponibleException(
                "El servicio de Inventario no está disponible. Intente más tarde.");
        }

        String estado;
        if (inventario.getStock_disponible() == 0) {
            estado = "SIN STOCK";
        } else if (inventario.getStock_disponible() <= 3) {
            estado = "STOCK BAJO";
        } else {
            estado = "STOCK SUFICIENTE";
        }

        return "Película: " + pelicula.getTitulo() +
               " | Stock total: " + inventario.getStock_total() +
               " | Disponible: " + inventario.getStock_disponible() +
               " | Arrendadas: " + inventario.getStock_arrendadas() +
               " | Estado: " + estado;
    }
}
