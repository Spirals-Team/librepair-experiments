create database new_database;

create table states (
	id serial primary key,
	name character varying (1000),
	description text
);

create table category (
	id serial primary key,
	name character varying (1000),
	description text
);

create table comments (
	id serial primary key,
	description text
);

create table attaches (
	id serial primary key,
	description text
);

create table rules (
	id serial primary key,
	name character varying (1000),
	description text
);

create table rolee (
	id serial primary key,
	name character varying (1000),
	description text,
	id_rules integer references rules(id)
);

create table users (
	id serial primary key,
	login character varying (1000),
	password character varying (1000),
	id_role integer references rolee(id)							
);

create table items (
	id serial primary key,
	description text,
	state_id integer references states(id),
	category_id integer references category(id),
	comments_id integer references comments(id),
	attaches_id integer references attaches(id),
	user_id integer references users(id)
);

insert into states(name, description) values('h1', 'high state');
insert into states(name, description) values('h2', 'middle state');
insert into states(name, description) values('h3', 'low state');

insert into category(name, description) values('auto', 'about auto');
insert into category(name, description) values('work', 'about work');
insert into category(name, description) values('business', 'about business');

insert into comments(description) values('its ok');
insert into comments(description) values('this is spam');

insert into attaches(description) values('its link to .rar');
insert into attaches(description) values('its link to .exe');
insert into attaches(description) values('its link to .java');

insert into rules(name, description) values('number 1', 'dont sleep');
insert into rules(name, description) values('number 2', 'dont smoke');
insert into rules(name, description) values('number 3', 'dont drink');

insert into rolee(name, description, rules_id) values('admin', 'admin', 1);
insert into rolee(name, description, rules_id) values('user', 'user', 3);
insert into rolee(name, description, rules_id) values('author', 'author', 2);

insert into users(login, password, id_role) values('alex', 'a', 9);
insert into users(login, password, id_role) values('mike', 'ab', 9);
insert into users(login, password, id_role) values('john', 'abc', 11);

insert into items(description, state_id, category_id, comments_id, attaches_id, user_id) values('first', 2, 1, 1, 1, 4);
insert into items(description, state_id, category_id, comments_id, attaches_id, user_id) values('first', 3, 3, 2, 1, 6);
insert into items(description, state_id, category_id, comments_id, attaches_id, user_id) values('first', 1, 2, 1, 3, 5);
select * from items;
