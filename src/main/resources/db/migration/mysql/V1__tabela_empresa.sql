create table empresa(
	id int not null auto_increment primary key,
	razao_social varchar(255) not null,
	cnpj varchar(255) not null,
	data_criacao datetime not null,
	data_atualizacao datetime not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;