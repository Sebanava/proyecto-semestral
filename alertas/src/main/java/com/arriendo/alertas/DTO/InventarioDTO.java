package com.arriendo.alertas.DTO;

import lombok.Data;

@Data
public class InventarioDTO {
    private long id;
    private long id_pelicula;
    private int stock_total;
    private int stock_disponible;
    private int stock_arrendadas;
}