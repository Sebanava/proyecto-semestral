    create table login(
        id bigint primary key auto_increment,
        rut VARCHAR (50) not null,
        nombre varchar(50) not null,
        apellido varchar(50) not null,
        email varchar(50) not null,
        numero INT not null,
        password VARCHAR (50) not null

    );