package com.arriendo.rental.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arriendo.rental.rental;
import com.arriendo.rental.DTO.PeliculaDTO;
import com.arriendo.rental.repository.RentalRepository;
import com.arriendo.rental.client.InventarioClient;
import com.arriendo.rental.client.PeliculaClient;
import com.arriendo.rental.model.RentalModel;

@Service
public class RentalService {

    @Autowired
    private InventarioClient inventarioClient;

    @Autowired
    private RentalRepository repository;

    @Autowired
    private PeliculaClient peliculaClient;

    public List<rental> obtenertodo(){
        return repository.findAll();
    }

    public rental guardar(RentalModel model){
        if (model.getFecha_fin().isBefore(model.getFecha_inicio())) {
            throw new RuntimeException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }

        PeliculaDTO pelicula = peliculaClient.obtenerPorId(model.getId_pelicula());

        rental nuevoRental = new rental();
        nuevoRental.setId_cliente(model.getId_cliente());
        nuevoRental.setId_pelicula(model.getId_pelicula());
        nuevoRental.setFecha_inicio(model.getFecha_inicio());
        nuevoRental.setFecha_fin(model.getFecha_fin());
        nuevoRental.setEstado(model.getEstado());
        nuevoRental.setMonto(pelicula.getPrecio());

        rental resultado = repository.save(nuevoRental);
        inventarioClient.ReducirStock(model.getId_pelicula());
        return resultado;
    }

    public void eliminar(long id){
        repository.deleteById(id);
    }

    public rental obtenerPorId(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Arriendo no encontrado"));
    }

    public rental actualizar(long id, RentalModel model){
        rental rentalExistente = repository.findById(id)
        .orElseThrow(()-> new RuntimeException("Arriendo no encontrado"));
            rentalExistente.setId_cliente(model.getId_cliente());
            rentalExistente.setId_pelicula(model.getId_pelicula());
            rentalExistente.setFecha_inicio(model.getFecha_inicio());
            rentalExistente.setFecha_fin(model.getFecha_fin());
            rentalExistente.setEstado(model.getEstado());

            return repository.save(rentalExistente);
    }

    public void pagar(Long id){
        rental rentalExistente = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Arriendo no encontrado"));
        if ("pagado".equals(rentalExistente.getEstado())) {
            throw new RuntimeException("Este arriendo ya fue pagado");
        }
        rentalExistente.setEstado("pagado");
        repository.save(rentalExistente);
    }

    public void devolver(Long id){
        rental rentalExistente = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Arriendo no encontrado"));
        if ("devuelto".equals(rentalExistente.getEstado())) {
            throw new RuntimeException("Este arriendo ya fue devuelto");
        }
        rentalExistente.setEstado("devuelto");
        repository.save(rentalExistente);
        inventarioClient.AumentarStock(rentalExistente.getId_pelicula());
    }

}