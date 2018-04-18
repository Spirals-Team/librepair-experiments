CREATE TABLE ESTADO(
    id   BIGSERIAL 	  NOT NULL,
    nome VARCHAR(255) NOT NULL UNIQUE,
    uf   VARCHAR(2) NOT NULL UNIQUE
    PRIMARY KEY(id)
);

CREATE TABLE CIDADE(
    id   	   BIGSERIAL    NOT NULL,
    nome 	   VARCHAR(255) NOT NULL UNIQUE,
    idEstado   INT          NOT NULL UNIQUE REFERENCES ESTADO(id),
    PRIMARY KEY(id)
);

CREATE TABLE BAIRRO(
    id        BIGSERIAL 	NOT NULL,
    nome      VARCHAR(255)	NOT NULL UNIQUE,
    idCidade  INT 		    NOT NULL UNIQUE REFERENCES CIDADE(id),
    PRIMARY KEY(id)
);

CREATE TABLE CLIENTE(
    id          BIGSERIAL    NOT NULL,
    nome        VARCHAR(255) NOT NULL,
    sobrenome   VARCHAR(255) NOT NULL,
    senha       VARCHAR(255) NOT NULL,
    cpf         VARCHAR(255) NOT NULL UNIQUE,
    email       VARCHAR(255) NOT NULL UNIQUE,
    telefone    VARCHAR(255) NOT NULL,
    celular     VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE RUA(
    cep         VARCHAR(255) NOT NULL,
    logradouro  VARCHAR(255) NOT NULL,
    PRIMARY KEY(cep)
)

CREATE TABLE ENDERECO(
    id          BIGSERIAL    NOT NULL,
    cepRua      VARCHAR(255) NOT NULL UNIQUE REFERENCES RUA(cep),
    idCliente   INT          NOT NULL UNIQUE REFERENCES CLIENTE(id),
    idBairro    INT          NOT NULL UNIQUE REFERENCES BAIRRO(id),
    numero	    INT          NOT NULL,
    complemento VARCHAR(255),
    PRIMARY KEY(id)
);

CREATE TABLE MARCA(
    id         BIGSERIAL    NOT NULL,
    descricao  VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE CATEGORIA(
    id         BIGSERIAL    NOT NULL,
    nome       VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE PRODUTO(
    id              BIGSERIAL    NOT NULL,
    nome            VARCHAR(255) NOT NULL,
    precoVenda      FLOAT        NOT NULL,
    precoCusto      FLOAT        NOT NULL,
    descontoMax     FLOAT        NOT NULL,
    idMarca         INT          NOT NULL UNIQUE REFERENCES MARCA(id),
    idCategoria     INT          NOT NULL UNIQUE REFERENCES CATEGORIA(id),
    descricao       VARCHAR(255) NOT NULL,
    unidadeMedida   VARCHAR(255) NOT NULL,
    url             VARCHAR(255) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE PEDIDO(
    id         BIGSERIAL NOT NULL,
    idCliente  INT       NOT NULL UNIQUE REFERENCES CLIENTE(id),
	idEndereco INT       NOT NULL UNIQUE REFERENCES ENDERECO(id),
	idItem	   INT       NOT NULL REFERENCES ITEM(id),
	PRIMARY KEY(id)
);

CREATE TABLE ESTOQUE(
    id          BIGSERIAL    NOT NULL,
    idProduto   INT          NOT NULL UNIQUE REFERENCES PRODUTO(id),
    quantidade  INT,
    PRIMARY KEY(id)
);

CREATE TABLE CARRINHO(
	id			BIGSERIAL    NOT NULL,
	idItem   	INT 		 NOT NULL UNIQUE REFERENCES ITEM(id),
	precoTotal	FLOAT,
	PRIMARY KEY(id)
);

CREATE TABLE ITEM(
	id 			BIGSERIAL    NOT NULL,
	idProduto	INT          NOT NULL UNIQUE REFERENCES PRODUTO(id),
	quantidade	INT,
	valorTotal	FLOAT,
	PRIMARY KEY(id)
);


