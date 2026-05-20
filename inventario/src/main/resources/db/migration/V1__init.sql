        create table inventario(
            id bigint primary key auto_increment,
            stock_total int not null,
            stock_disponible  int not null, 
            stock_arrendadas int not null,
            id_pelicula bigint not null

        );