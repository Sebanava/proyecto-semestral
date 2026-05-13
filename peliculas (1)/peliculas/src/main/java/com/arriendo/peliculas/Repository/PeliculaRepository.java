package com.arriendo.peliculas.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arriendo.peliculas.Pelicula;

@Repository
public interface PeliculaRepository extends JpaRepository <Pelicula, Long>{

    Optional<Pelicula> findByTitulo(String titulo);

    boolean existsByTitulo(String titulo);

    List<Pelicula> findByCategoria(String categoria);
}


