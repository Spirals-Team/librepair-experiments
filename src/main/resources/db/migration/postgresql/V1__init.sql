CREATE TABLE empresa (
  id_empresa BIGINT NOT NULL,
  cnpj varchar(255) NOT NULL,
  data_atualizacao TIMESTAMP NOT NULL,
  data_criacao TIMESTAMP NOT NULL,
  razao_social varchar(255) NOT NULL
);

CREATE TABLE pessoa (
  id_pessoa BIGINT NOT NULL,
  nome varchar(255) NOT NULL,
  cpf varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  senha varchar(255) NOT NULL,
  data_atualizacao TIMESTAMP NOT NULL,
  data_criacao TIMESTAMP NOT NULL,
  razao_social varchar(255) NOT NULL
);

CREATE TABLE funcionario (
  id_funcionario BIGINT NOT NULL,
  id_pessoa BIGINT DEFAULT NULL,
  id_empresa BIGINT DEFAULT NULL,
  data_atualizacao TIMESTAMP NOT NULL,
  data_criacao TIMESTAMP NOT NULL,
  perfil varchar(255) NOT NULL,
  qtd_horas_almoco float DEFAULT NULL,
  qtd_horas_trabalho_dia float DEFAULT NULL,
  valor_hora decimal(19,2) DEFAULT NULL
);

CREATE TABLE lancamento (
  id_lancamento BIGINT NOT NULL,
  id_funcionario BIGINT DEFAULT NULL,
  data TIMESTAMP NOT NULL,
  data_atualizacao TIMESTAMP NOT NULL,
  data_criacao TIMESTAMP NOT NULL,
  descricao varchar(255) DEFAULT NULL,
  localizacao varchar(255) DEFAULT NULL,
  tipo varchar(255) NOT NULL
);

--
-- Indexes for table empresa
--
ALTER TABLE empresa
  ADD PRIMARY KEY (id_empresa);

--
-- Indexes for table pessoa
--
ALTER TABLE pessoa
  ADD PRIMARY KEY (id_pessoa);
  
--
-- Indexes for table funcionario
--
ALTER TABLE funcionario
  ADD PRIMARY KEY (id_funcionario);

--
-- Indexes for table lancamento
--
ALTER TABLE lancamento
  ADD PRIMARY KEY (id_lancamento);
  
--
-- Constraints for table funcionario
--
ALTER TABLE funcionario
  ADD CONSTRAINT fk_pessoa_funcionario FOREIGN KEY (id_pessoa) REFERENCES pessoa (id_pessoa);
  
ALTER TABLE funcionario
  ADD CONSTRAINT fk_funcionario_empresa FOREIGN KEY (id_empresa) REFERENCES empresa (id_empresa);

--
-- Constraints for table lancamento
--
ALTER TABLE lancamento
  ADD CONSTRAINT fk_lancamento_funcionario FOREIGN KEY (id_funcionario) REFERENCES funcionario (id_funcionario);