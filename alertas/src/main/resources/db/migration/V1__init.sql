    create table alertas(
        id bigint primary key auto_increment,
        id_pelicula bigint not null,
        tipo varchar(50) not null,
        fecha date not null
    );