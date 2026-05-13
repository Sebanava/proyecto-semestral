package com.arriendo.pago;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table (name = "pago")
public class pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "El id_rental no puede estar vacio")
    private long id_rental;

    @NotBlank(message = "El resultado no puede estar vacio")
    private String resultado;

    @NotNull(message = "Fecha no puede estar vacio ")
    private LocalDate fecha;


}
