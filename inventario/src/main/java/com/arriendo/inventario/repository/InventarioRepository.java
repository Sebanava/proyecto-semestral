package com.arriendo.inventario.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arriendo.inventario.Inventario;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {




}

