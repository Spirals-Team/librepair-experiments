  CREATE TABLE public.avaliacoes
(
    id integer NOT NULL DEFAULT nextval('avaliacoes_id_seq'::regclass),
    descricao character varying(500) COLLATE pg_catalog."default",
    usuario integer,
    fornecedor integer,
    pontuacao integer,
    data_avaliacao date,
    CONSTRAINT avaliacoes_pkey PRIMARY KEY (id),
    CONSTRAINT avaliacoes_fornecedor_fkey FOREIGN KEY (fornecedor)
        REFERENCES public.usuario_fornecedor (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT avaliacoes_usuario_fkey FOREIGN KEY (usuario)
        REFERENCES public.usuario_comum (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.avaliacoes
    OWNER to postgres;
    
CREATE TABLE public.categoria
(
    id integer NOT NULL DEFAULT nextval('categoria_id_seq'::regclass),
    nome character varying(90) COLLATE pg_catalog."default",
    CONSTRAINT categoria_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.categoria
    OWNER to postgres;

CREATE TABLE public.cidade
(
    id integer NOT NULL DEFAULT nextval('cidade_id_seq'::regclass),
    nome character varying(60) COLLATE pg_catalog."default",
    estado character(60) COLLATE pg_catalog."default",
    sigla character(2) COLLATE pg_catalog."default",
    CONSTRAINT cidade_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.cidade
    OWNER to postgres;
    

CREATE TABLE public.favorito
(
    id integer NOT NULL DEFAULT nextval('favorito_id_seq'::regclass),
    produto integer,
    usuario integer,
    CONSTRAINT favorito_pkey PRIMARY KEY (id),
    CONSTRAINT favorito_produto_fkey FOREIGN KEY (produto)
        REFERENCES public.produto (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT favorito_usuario_fkey FOREIGN KEY (usuario)
        REFERENCES public.usuario (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.favorito
    OWNER to postgres;
    
CREATE TABLE public.fornecedor_categoria
(
    id integer NOT NULL DEFAULT nextval('fornecedor_categoria_id_seq'::regclass),
    fornecedor integer,
    categoria integer,
    CONSTRAINT fornecedor_categoria_pkey PRIMARY KEY (id),
    CONSTRAINT fornecedor_categoria_categoria_fkey FOREIGN KEY (categoria)
        REFERENCES public.categoria (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fornecedor_categoria_fornecedor_fkey FOREIGN KEY (fornecedor)
        REFERENCES public.usuario_fornecedor (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.fornecedor_categoria
    OWNER to postgres;
    
CREATE TABLE public.fornecedor_cidade
(
    id integer NOT NULL DEFAULT nextval('fornecedor_cidade_id_seq'::regclass),
    fornecedor integer,
    cidade integer,
    CONSTRAINT fornecedor_cidade_pkey PRIMARY KEY (id),
    CONSTRAINT fornecedor_cidade_cidade_fkey FOREIGN KEY (cidade)
        REFERENCES public.cidade (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fornecedor_cidade_fornecedor_fkey FOREIGN KEY (fornecedor)
        REFERENCES public.usuario_fornecedor (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.fornecedor_cidade
    OWNER to postgres;
    
CREATE TABLE public.fornecedor_galeria
(
    id integer NOT NULL DEFAULT nextval('fornecedor_galeria_id_seq'::regclass),
    fornecedor integer,
    image64 character varying COLLATE pg_catalog."default",
    CONSTRAINT fornecedor_galeria_pkey PRIMARY KEY (id),
    CONSTRAINT fornecedor_galeria_fornecedor_fkey FOREIGN KEY (fornecedor)
        REFERENCES public.usuario_fornecedor (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.fornecedor_galeria
    OWNER to postgres;
    
CREATE TABLE public.fornecedor_subcategoria
(
    id integer NOT NULL DEFAULT nextval('fornecedor_subcategoria_id_seq'::regclass),
    fornecedor integer,
    subcategoria integer,
    CONSTRAINT fornecedor_subcategoria_pkey PRIMARY KEY (id),
    CONSTRAINT fornecedor_subcategoria_fornecedor_fkey FOREIGN KEY (fornecedor)
        REFERENCES public.usuario_fornecedor (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fornecedor_subcategoria_subcategoria_fkey FOREIGN KEY (subcategoria)
        REFERENCES public.subcategoria (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.fornecedor_subcategoria
    OWNER to postgres;
    
CREATE TABLE public.produto
(
    id integer NOT NULL DEFAULT nextval('produto_id_seq'::regclass),
    nome character varying(60) COLLATE pg_catalog."default",
    valor numeric(10,2),
    categoria integer,
    descricao character varying(500) COLLATE pg_catalog."default",
    desconto numeric(10,2),
    img1 bytea,
    img2 bytea,
    img3 bytea,
    qnt integer,
    fornecedor integer,
    img4 bytea,
    CONSTRAINT produto_pkey PRIMARY KEY (id),
    CONSTRAINT produto_categoria_fkey FOREIGN KEY (categoria)
        REFERENCES public.categoria (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT produto_fornecedor_fkey FOREIGN KEY (fornecedor)
        REFERENCES public.usuario_fornecedor (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.produto
    OWNER to postgres;
    
CREATE TABLE public.produto_subcategoria
(
    id integer NOT NULL DEFAULT nextval('produto_subcategoria_id_seq'::regclass),
    subcategoria integer,
    produto integer,
    CONSTRAINT produto_subcategoria_pkey PRIMARY KEY (id),
    CONSTRAINT produto_subcategoria_produto_fkey FOREIGN KEY (produto)
        REFERENCES public.produto (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT produto_subcategoria_subcategoria_fkey FOREIGN KEY (subcategoria)
        REFERENCES public.subcategoria (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.produto_subcategoria
    OWNER to postgres;
    
CREATE TABLE public.subcategoria
(
    id integer NOT NULL DEFAULT nextval('subcategoria_id_seq'::regclass),
    nome character varying(100) COLLATE pg_catalog."default",
    categoria integer,
    CONSTRAINT subcategoria_pkey PRIMARY KEY (id),
    CONSTRAINT subcategoria_categoria_fkey FOREIGN KEY (categoria)
        REFERENCES public.categoria (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.subcategoria
    OWNER to postgres;
    
CREATE TABLE public.usuario
(
    id integer NOT NULL DEFAULT nextval('usuario_id_seq'::regclass),
    nome character varying(60) COLLATE pg_catalog."default",
    sobrenome character varying(60) COLLATE pg_catalog."default",
    cpf character varying(14) COLLATE pg_catalog."default",
    email character varying(60) COLLATE pg_catalog."default",
    senha character varying(60) COLLATE pg_catalog."default",
    tipo integer,
    primeiro_login boolean,
    CONSTRAINT usuario_pkey PRIMARY KEY (id),
    CONSTRAINT cpf_unique UNIQUE (cpf),
    CONSTRAINT email_unique UNIQUE (email)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.usuario
    OWNER to postgres;
    
CREATE TABLE public.usuario_comum
(
    id integer NOT NULL DEFAULT nextval('usuario_comum_id_seq'::regclass),
    telefone character varying(30) COLLATE pg_catalog."default",
    celular character varying(30) COLLATE pg_catalog."default",
    cidade character varying(40) COLLATE pg_catalog."default",
    bairro character varying(40) COLLATE pg_catalog."default",
    endereco character varying(60) COLLATE pg_catalog."default",
    numero character varying(10) COLLATE pg_catalog."default",
    cep character varying(13) COLLATE pg_catalog."default",
    usuario integer,
    CONSTRAINT usuario_comum_pkey PRIMARY KEY (id),
    CONSTRAINT usuario_comum_usuario_fkey FOREIGN KEY (usuario)
        REFERENCES public.usuario (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.usuario_comum
    OWNER to postgres;
    
CREATE TABLE public.usuario_fornecedor
(
    id integer NOT NULL DEFAULT nextval('usuario_fornecedor_id_seq'::regclass),
    cnpj character varying(20) COLLATE pg_catalog."default",
    razao character varying(60) COLLATE pg_catalog."default",
    nome character varying(40) COLLATE pg_catalog."default",
    telefone character varying(30) COLLATE pg_catalog."default",
    celular character varying(30) COLLATE pg_catalog."default",
    atendimento_seg_sex character varying(12) COLLATE pg_catalog."default",
    atendimento_sab character varying(12) COLLATE pg_catalog."default",
    endereco character varying(60) COLLATE pg_catalog."default",
    bairro character varying(40) COLLATE pg_catalog."default",
    numero character varying(10) COLLATE pg_catalog."default",
    cidade character varying(40) COLLATE pg_catalog."default",
    usuario integer,
    cep character varying(13) COLLATE pg_catalog."default",
    estado character varying(2) COLLATE pg_catalog."default",
    descricao character varying(500) COLLATE pg_catalog."default",
    logo character varying COLLATE pg_catalog."default",
    email character varying(100) COLLATE pg_catalog."default",
    CONSTRAINT usuario_fornecedor_pkey PRIMARY KEY (id),
    CONSTRAINT usuario_fornecedor_usuario_fkey FOREIGN KEY (usuario)
        REFERENCES public.usuario (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.usuario_fornecedor
    OWNER to postgres;