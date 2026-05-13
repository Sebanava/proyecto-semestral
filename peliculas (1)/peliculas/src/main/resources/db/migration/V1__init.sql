    create table peliculas(
        id bigint primary key auto_increment,
        titulo varchar(50) not null,
        categoria varchar(15) not null,
        duracion varchar(10) not null,
        anio varchar(5) not null,
        director varchar(20) not null,
        stock  int not null,   
        precio DOUBLE not null

    );