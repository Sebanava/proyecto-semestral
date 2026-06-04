package com.arriendo.inventario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.arriendo.inventario.Inventario;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    @Query("SELECT i FROM Inventario i WHERE i.id_pelicula = :idPelicula")
    Optional<Inventario> findByIdPelicula(@Param("idPelicula") Long idPelicula);

}