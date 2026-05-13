package com.arriendo.clientes.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.arriendo.clientes.Cliente;
import com.arriendo.clientes.Repository.ClienteRepository;
import com.arriendo.clientes.model.ClienteModel;




    @Service
    public class ClienteService {
        
    @Autowired
    private ClienteRepository repository;
    
        
    

    public List<Cliente> obtenertodo(){
        return repository.findAll();
    }




    public Cliente guardar(ClienteModel model){

        if (repository.existsByEmail(model.getEmail())) {
            throw new RuntimeException("El mail ya esta registrado");
            
        }

        if (repository.existsByRut(model.getRut())) {
            throw new RuntimeException("Este rut ya esta en uso");
            
        }
        Cliente cliente = new Cliente();
        cliente.setRut(model.getRut());
        cliente.setNombre(model.getNombre());
        cliente.setEmail(model.getEmail());
        cliente.setNumero(model.getNumero());
        cliente.setDireccion(model.getDireccion());
        
        return repository.save(cliente);
    }

    public void eliminar(long id){
        repository.deleteById(id);
    }

    public List<Cliente>ObtenerPorRut(String rut){
        return repository.findByRut(rut);
    }

    public Cliente actualizar(Long id, ClienteModel model){
        Cliente cliente = repository.findById(id)
            .orElseThrow(()-> new RuntimeException("cliente no encontrado"));

        cliente.setRut(model.getRut());
        cliente.setNombre(model.getNombre());
        cliente.setEmail(model.getEmail());
        cliente.setNumero(model.getNumero());
        cliente.setDireccion(model.getDireccion());

        return repository.save(cliente);
        
        
        
    }

   
}
