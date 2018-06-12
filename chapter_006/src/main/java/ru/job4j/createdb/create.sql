create database sample;

create table rules (
	id_rule serial not null primary key,
	name_rule varchar(45) not null
);

create table roles_rules (
	id_role int not null,
	id_rule int not null,
	constraint role_rules_pkey primary key(id_role, id_rule)
);

create table roles (
	id_role serial not null primary key,
	name_role varchar(45) not null
);

create table users (
	id_user serial not null primary key,
	name_user varchar(45) not null,
	address_user varchar(45) not null,
	phone_user varchar(45) not null,
	id_role int not null
);

create table items (
	id_user serial not null primary key,
	describe_item text,
	create_item date,
	id_category int,
	id_state int
);

create table categories (
	id_category int not null primary key,
	name_category varchar(45) not null
);

create table coments (
	id_comment serial not null primary key,
	text_comment text not null,
	id_item int not null
);

create table states (
	id_state serial not null primary key,
	name_state varchar(45) not null
);

create table attaches (
	id_attach serial not null primary key,
	path_attch varchar(45),
	id_item int not null
);

alter table roles_rules
	add constraint fk_rules foreign key(id_rule)
	references rules(id_rule)
	on update cascade
	on delete cascade,
	add constraint fk_roles foreign key(id_role)
	references roles(id_role)
	on update cascade
	on delete cascade;
	
alter table users
	add constraint fk_roles foreign key(id_role)
	references roles(id_role)
	on update cascade
	on delete cascade,
	add constraint fk_items foreign key(id_user)
	references items(id_user)
	on update cascade
	on delete cascade;
	
alter table items
	add constraint fk_categories foreign key(id_category)
	references categories(id_category)
	on update cascade 
	on delete cascade,
	add constraint fk_states foreign key(id_state) 
	references states(id_state)
	on update cascade 
	on delete cascade;
	
alter table coments
	add constraint fk_items foreign key(id_item)
	references items(id_user)
	on update cascade 
	on delete cascade;
	
alter table attaches
	add constraint fk_items foreign key(id_item)
	references items(id_user)
	on update cascade 
	on delete cascade;
	
begin;	
insert into roles(id_role, name_role) values(1, 'user');
insert into roles(id_role, name_role) values(2, 'master');

insert into categories(id_category, name_category) values(1, 'car');
insert into categories(id_category, name_category) values(2, 'vehicle');

insert into states(id_state, name_state) values(1, 'work');
insert into states(id_state, name_state) values(2, 'completed');
insert into states(id_state, name_state) values(3, 'canceled');

insert into items(id_user, describe_item, create_item, id_category, id_state)
values(1, 'change of suspension', '2018-10-05', 1, 2);
insert into items(id_user, describe_item, create_item, id_category, id_state)
values(2, null, null, null, null);
insert into items(id_user, describe_item, create_item, id_category, id_state)
values(3, 'print of doors', '2018-10-20', 1, 1);
insert into items(id_user, describe_item, create_item, id_category, id_state)
values(4, 'repair of engine', '2018-10-25', 2, 1);

insert into users(id_user, name_user, address_user, phone_user, id_role)
values(1, 'Matskevich Yury', 'Brest', '222-11-11', 1);
insert into users(id_user, name_user, address_user, phone_user, id_role)
values(2, 'Verenich Andrey', 'Brest', '333-11-11', 2);
insert into users(id_user, name_user, address_user, phone_user, id_role)
values(3, 'Kostuchik Maksim', 'Brest', '444-11-11', 1);
insert into users(id_user, name_user, address_user, phone_user, id_role)
values(4, 'Pomidorov Igor', 'Vitebsk', '555-11-11', 1);

insert into rules(id_rule, name_rule) values(1, 'browse');
insert into rules(id_rule, name_rule) values(2, 'search');
insert into rules(id_rule, name_rule) values(3, 'add');
insert into rules(id_rule, name_rule) values(4, 'delete');
insert into rules(id_rule, name_rule) values(5, 'modify');

insert into coments(id_comment, text_comment, id_item)
values(1, 'first comment of item1', 1);
insert into coments(id_comment, text_comment, id_item)
values(2, 'second comment of item1', 1);
insert into coments(id_comment, text_comment, id_item)
values(3, 'first comment of item3', 3);
insert into coments(id_comment, text_comment, id_item)
values(4, 'first comment of item4', 4);

insert into attaches(id_attach, path_attch, id_item)
values(1, 'D:\drive_license\Matskevich.png', 1);
insert into attaches(id_attach, path_attch, id_item)
values(2, 'D:\damages\photo1.png', 1);
insert into attaches(id_attach, path_attch, id_item)
values(3, 'D:\damages\photo2.png', 1);
insert into attaches(id_attach, path_attch, id_item)
values(4, 'D:\drive_license\Kostuchik.png', 3);
insert into attaches(id_attach, path_attch, id_item)
values(5, 'D:\drive_license\Pomidorov.png', 4);
		
insert into roles_rules(id_role, id_rule) values(1, 1);
insert into roles_rules(id_role, id_rule) values(2, 1);
insert into roles_rules(id_role, id_rule) values(1, 2);
insert into roles_rules(id_role, id_rule) values(2, 2);
insert into roles_rules(id_role, id_rule) values(2, 3);
insert into roles_rules(id_role, id_rule) values(2, 4);
insert into roles_rules(id_role, id_rule) values(2, 5);
commit;