create table EVENTO (
    ID serial primary key not null ,
    TITULO varchar(100) not null,
    DATA varchar(100) not null,
    DESCRICAO varchar(100) not null,
    FOTO varchar(100) not null
);

insert into evento (TITULO, DATA, DESCRICAO, FOTO) values ('Encontro de Carros', 'Seg 18 Mai 2018', 'Praesent tincidunt sed tellus ut rutrum sed vitae justo...', '1.jpg');
insert into evento (TITULO, DATA, DESCRICAO, FOTO) values ('Festival de Rua', 'Seg 18 Mai 2019', 'Praesent tincidunt sed tellus ut rutrum sed vitae justo...', '2.jpg');
insert into evento (TITULO, DATA, DESCRICAO, FOTO) values ('Passeio em Veneza', 'Seg 18 Mai 2020', 'Praesent tincidunt sed tellus ut rutrum sed vitae justo...', '3.jpg');
insert into evento (TITULO, DATA, DESCRICAO, FOTO) values ('Travessia', 'Seg 18 Mai 2020', 'Praesent tincidunt sed tellus ut rutrum sed vitae justo...', '4.jpg');