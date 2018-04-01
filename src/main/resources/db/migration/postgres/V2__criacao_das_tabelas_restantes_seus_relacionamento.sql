-- Cria a tabela onde seram salvos os aviões
CREATE TABLE aviao
(
   aviao_codigo serial PRIMARY KEY NOT NULL,
   aviao_nome varchar(255)   
);

-- cria a tabela que seram salvos os voos
create table voo
(
   voo_codigo serial primary key not null,
   piloto_codigo int,
   aviao_codigo int,
   cidade_codigo_origem int,
   cidade_codigo_destino int,
   voo_data_partida timestamp,
   voo_data_chegada timestamp,
   voo_status varchar(100)   
);

-- cria a tabela que ira armazenar o histórico de alterações do voo
create table voo_historico_status
(
   voo_historico_status_codigo serial primary key not null,
   voo_historico_status_descrica varchar(255),
   voo_codigo int   
);

create table cidade
(
   cidade_codigo serial primary key not null,
   cidade_nome varchar(255)  
);

-- cria chave estrangeira entre voo e piloto
alter table voo add constraint fk_piloto_codigo foreign key (piloto_codigo) 
references piloto(piloto_codigo) on delete no action on update no action;

-- cria chave estrangeira entre voo e aviao
alter table voo add constraint fk_aviao_codigo foreign key (aviao_codigo) 
references aviao(aviao_codigo) on delete no action on update no action;

-- cria chave estrangeira entre voo e cidade origem
alter table voo add constraint fk_cidade_codigo_origem foreign key (cidade_codigo_origem) 
references cidade(cidade_codigo) on delete no action on update no action;

-- cria chave estrangeira entre voo e cidade destino
alter table voo add constraint fk_cidade_codigo_destino foreign key (cidade_codigo_destino) 
references cidade(cidade_codigo) on delete no action on update no action;

-- cria chave estrangeira entre voo e voo histórico status
alter table voo_historico_status add constraint fk_voo_codigo foreign key (voo_codigo) 
references voo(voo_codigo) on delete no action on update no action;



