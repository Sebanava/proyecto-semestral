    create table clientes(
        id bigint primary key auto_increment,
        rut VARCHAR (50) not null,
        nombre varchar(50) not null,
        email varchar(50) not null,
        numero INT(15) not null

    );