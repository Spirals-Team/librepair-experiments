-- Cria a tabela piloto
CREATE TABLE piloto
(
   piloto_codigo serial PRIMARY KEY NOT NULL,
   piloto_nome varchar(255),
   piloto_identificacao varchar(255)
);