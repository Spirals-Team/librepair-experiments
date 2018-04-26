create table usuarios (
	id int not null auto_increment,
	nombre varchar(50) not null,
	apellido varchar(50) not null,
	email varchar(100) not null,
	primary key (id)
	);