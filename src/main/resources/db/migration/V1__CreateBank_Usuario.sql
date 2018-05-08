create table usuario(
	id integer not null primary key auto_increment,
	email varchar(100),
	senha varchar(100)
);

create table biblioteca(
	id integer not null primary key auto_increment,
	nome varchar(50)
);

create table palavras_biblioteca(
	id_biblioteca integer not null primary key,
	foreign key (id_biblioteca) references biblioteca(id),
	nome varchar(100),
	traducao1 varchar(100),
	traducao2 varchar(100),
	traducao3 varchar(100)
);