package com.arriendo.clientes.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arriendo.clientes.*;

@Repository
public interface ClienteRepository extends JpaRepository <Cliente, Long>{

    boolean existsByEmail(String email);
    boolean existsByRut(String rut);

    List<Cliente> findByRut(String rut);
}
