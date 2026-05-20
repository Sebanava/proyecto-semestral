package com.arriendo.inventario.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InventarioModel {

    @Min(value = 0 ,message = "el total no puede estar vacio")
    private int stock_total;    

    @Min(value = 0 ,message = "el stock disponible no puede estar en blanco")
    private int stock_disponible;

    @Min(value = 0, message = "el stock arrendadas no puede estar en blanco")
    private int stock_arrendadas;

    @NotNull(message = "el id de peliculas no puede estar en blanco")
    private long id_pelicula;

}
