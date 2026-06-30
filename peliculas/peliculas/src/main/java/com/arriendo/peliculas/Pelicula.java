package com.arriendo.peliculas;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;   
import lombok.Data;

@Entity
@Data
@Table (name = "peliculas")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(nullable = false, unique = true)
    private String titulo;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private String duracion;

    @Column(nullable = false)
    private String anio;

    @Column(nullable = false)
    private String director;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private double precio;


}
