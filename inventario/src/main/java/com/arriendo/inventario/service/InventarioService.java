package com.arriendo.inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arriendo.inventario.Inventario;
import com.arriendo.inventario.model.InventarioModel;
import com.arriendo.inventario.repository.InventarioRepository;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository repository;

    public List<Inventario> obtenerTodo(){
        return repository.findAll();
    }

    public Inventario obtenerPorId(Long id){
        return repository.findById(id)
            .orElseThrow(()-> new RuntimeException("no encontrado"));
    }

    public Inventario guardar(InventarioModel model){
        Inventario inventario = new Inventario();
        inventario.setStock_total(model.getStock_total());
        inventario.setId_pelicula(model.getId_pelicula());
        inventario.setStock_arrendadas(model.getStock_arrendadas());
        inventario.setStock_disponible(model.getStock_disponible());
        return repository.save(inventario);
        
    }

    public Inventario actualizar(Long id, InventarioModel model){
        Inventario inventario = repository.findById(id)
            .orElseThrow(()-> new RuntimeException("Inventario no encontrado"));
        inventario.setStock_total(model.getStock_total());
        inventario.setId_pelicula(model.getId_pelicula());
        inventario.setStock_arrendadas(model.getStock_arrendadas());
        inventario.setStock_disponible(model.getStock_disponible());
        return repository.save(inventario);
    }

    public void eliminar(Long id){
        repository.findById(id)
            .orElseThrow(()-> new RuntimeException("Inventario no encontrado"));
        repository.deleteById(id);
    }

    public void reducirStock(Long idPelicula){
    Inventario inventario = repository.findById(idPelicula)
        .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
    
    if(inventario.getStock_disponible() <= 0){
        throw new RuntimeException("Sin stock disponible");
    }
    
    inventario.setStock_disponible(inventario.getStock_disponible() - 1);
    inventario.setStock_arrendadas(inventario.getStock_arrendadas() + 1);
    repository.save(inventario);
}
}