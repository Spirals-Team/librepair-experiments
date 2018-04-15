CREATE TABLE pontointeligente.empresa (
	id bigint(20) not null,
	cnpj varchar(20) not null,
	data_atualizacao datetime not null,
	data_criacao datetime not null,
	razao_social varchar(255) not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
        
CREATE TABLE pontointeligente.funcionario (
	id bigint(20) not null,
	cpf varchar(255) not null,
	data_atualizacao datetime not null,
	data_criacao datetime not null,
	email varchar(255) not null,
	nome varchar(255) not null,
	perfil varchar(255) not null,
	qtd_horas_almoco float default null,
	qtd_horas_trabalho_dia float default null,
	senha varchar(255) not null,
	valor_hora decimal(19,2) default null,
	empresa_id bigint(20) default null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;        
        
CREATE TABLE pontointeligente.lancamento (
	id bigint(20) not null,
	data datetime not null,
	data_atualizacao datetime not null,
	data_criacao datetime not null,
	descricao varchar(255) not null,
	localizacao varchar(255) not null,
	tipo varchar(255) not null,
	funcionario_id bigint(20) default null
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE pontointeligente.empresa 
        ADD primary key(id);  
        
ALTER TABLE pontointeligente.funcionario 
        ADD primary key(id),
        ADD KEY FKm1qjpisd1499p2s5v55u9eyqc (empresa_id);         
  
ALTER TABLE pontointeligente.lancamento 
        ADD primary key(id),
        ADD KEY FKglwam4y1o58gv9uocb9bkfyyo (funcionario_id);     
           
        
ALTER TABLE pontointeligente.empresa 
        MODIFY id bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE pontointeligente.funcionario 
        MODIFY id bigint(20) NOT NULL AUTO_INCREMENT;
        
ALTER TABLE pontointeligente.lancamento 
        MODIFY id bigint(20) NOT NULL AUTO_INCREMENT;
        
                
ALTER TABLE pontointeligente.funcionario 
        ADD CONSTRAINT FKm1qjpisd1499p2s5v55u9eyqc FOREIGN KEY(empresa_id) REFERENCES empresa(id);        
        
ALTER TABLE pontointeligente.lancamento 
        ADD CONSTRAINT FKglwam4y1o58gv9uocb9bkfyyo FOREIGN KEY(funcionario_id) REFERENCES funcionario(id);