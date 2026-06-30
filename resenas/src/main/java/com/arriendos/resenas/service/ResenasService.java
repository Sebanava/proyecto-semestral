package com.arriendos.resenas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arriendos.resenas.Resenas;
import com.arriendos.resenas.cliente.PeliculaClient;
import com.arriendos.resenas.model.ResenasModel;
import com.arriendos.resenas.repository.ResenasRepository;

@Service
public class ResenasService {

    @Autowired
    private ResenasRepository repository;

    @Autowired 
    PeliculaClient peliculaClient;


    public List<Resenas>ObtenerTodo(){
        return repository.findAll();
    }

    public List<Resenas>ObtenerPorTitulo(String titulo){
        List<Resenas> resenas = repository.findByTitulo(titulo);
            if (resenas.isEmpty()){
                throw new RuntimeException("No se encuentrar reseñas para " + titulo);
            }

            return resenas;
    }

    public Resenas obtenerPorId(Long id){
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reseña no encontrada"));
    }

    public Resenas actualizar(Long id, ResenasModel model){
        Resenas resena = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reseña no encontrada"));
        resena.setTitulo(model.getTitulo());
        resena.setComentario(model.getComentario());
        resena.setCalificaciones(model.getCalificaciones());
        return repository.save(resena);
    }

    public void eliminar(long id){
        repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Reseña no encontrada"));
        repository.deleteById(id);
    }

    public Resenas guardar(ResenasModel model){
        peliculaClient.obtenerPorTitulo(model.getTitulo());

        Resenas resena = new Resenas();
        resena.setTitulo(model.getTitulo());
        resena.setCalificaciones(model.getCalificaciones());
        resena.setComentario(model.getComentario());


        return repository.save(resena);
    }
    
    
}
