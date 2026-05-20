package com.arriendos.resenas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arriendos.resenas.Resenas;

@Repository
public interface ResenasRepository extends JpaRepository<Resenas, Long>{

    List<Resenas> findByTitulo(String titulo);

}






