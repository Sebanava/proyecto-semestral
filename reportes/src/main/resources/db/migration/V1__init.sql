create table reportes(
    id bigint primary key auto_increment,
    tipo varchar (50) not null,
    estado varchar (50) not null,
    fecha date not null
)