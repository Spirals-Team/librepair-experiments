CREATE TABLE USUARIO(
	ID serial primary key not null ,
	nome VARCHAR(100) not null,
	cpf VARCHAR(30) not null,
	email VARCHAR(70) not null,
	senha VARCHAR(150) NOT NULL,
	foto VARCHAR(50),
	ativo BOOLEAN not null
);