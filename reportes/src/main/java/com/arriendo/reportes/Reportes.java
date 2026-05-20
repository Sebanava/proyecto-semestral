package com.arriendo.reportes;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "reportes")
public class Reportes {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String tipo;

    @Column (nullable = false)
    private String estado;

    @Column (nullable = false)
    private LocalDate fecha;

}
