CREATE DATABASE items;

create table items(
  id serial primary key,
  login character varying(2000),
  password character varying(2000),
  items int
);

insert into items(login, password, items) values ('one','pass','3');
insert into items(login, password, items) values ('two','pass','5');
insert into items(login, password, items) values ('three','pass','7');

select * from items;