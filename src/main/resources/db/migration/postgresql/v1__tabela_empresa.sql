CREATE SEQUENCE sq_empresa INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE empresa
(
  id bigint NOT NULL DEFAULT nextval('sq_empresa'::regclass),
  cnpj character varying(255),
  razao_social character varying(255),
  data_atualizacao TIMESTAMP WITH TIME ZONE,
  data_criacao TIMESTAMP WITH TIME ZONE,
  CONSTRAINT plano_pkey PRIMARY KEY (id)
);
