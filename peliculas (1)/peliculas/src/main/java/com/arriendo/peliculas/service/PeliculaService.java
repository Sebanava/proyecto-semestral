package com.arriendo.peliculas.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arriendo.peliculas.Pelicula;
import com.arriendo.peliculas.Repository.PeliculaRepository;
import com.arriendo.peliculas.model.PeliculaModel;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepository repository;

    public List<Pelicula> obtenertodo(){
        return repository.findAll();
    }

    public Pelicula obtenerPorTitulo(String titulo) {
    return repository.findByTitulo(titulo)
        .orElseThrow(() -> new RuntimeException("Película no encontrada"));
    }

    public Pelicula obtenerPorId(Long id){
        return repository.findById(id)
        .orElseThrow(()-> new RuntimeException("pelicula no encontrada por id"));
    }

    public Pelicula actualizar(Long id, PeliculaModel model){
        Pelicula pelicula = repository.findById(id)
            .orElseThrow(()-> new RuntimeException("Pelicula no encontrada "));
        pelicula.setTitulo(model.getTitulo());
        pelicula.setCategoria(model.getCategoria());
        pelicula.setDuracion(model.getDuracion());
        pelicula.setAnio(model.getAnio());
        pelicula.setDirector(model.getDirector());
        pelicula.setStock(model.getStock());
        pelicula.setPrecio(model.getPrecio());
        return repository.save(pelicula);
        
    }

    public Pelicula guardar(PeliculaModel model){

        if(repository.existsByTitulo(model.getTitulo())){
            throw new RuntimeException("esta pelicula ya esta guardada");
        }


        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo(model.getTitulo());
        pelicula.setCategoria(model.getCategoria());
        pelicula.setDuracion(model.getDuracion());
        pelicula.setAnio(model.getAnio());
        pelicula.setDirector(model.getDirector());
        pelicula.setStock(model.getStock());
        pelicula.setPrecio(model.getPrecio());
        return repository.save(pelicula);
}

    public void eliminar(long id){
        repository.deleteById(id);
    }

    public List<Pelicula>ObtenerPorCategoria(String categoria){
        return repository.findByCategoria(categoria);
    }

}