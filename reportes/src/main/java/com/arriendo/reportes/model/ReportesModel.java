package com.arriendo.reportes.model;

import java.time.LocalDate;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReportesModel {

    @NotBlank(message = "No puede estar vacio")
    private String tipo;

    @NotBlank( message =  "No puede estar vacio")
    private String estado;

    @NotNull(message = "No puede estar vaio")
    private LocalDate fecha;
    

}
