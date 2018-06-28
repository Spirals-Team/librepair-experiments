create table transmission(
	id serial primary key,
	name varchar (200)
);

create table carbody(
	id serial primary key,
	name varchar (200)
);

create table engine(
	id serial primary key,
	name varchar (200)
);

create table car(
	id serial primary key,
	name varchar (200),
	carbody_id int references carbody(id),
	transmission_id int references transmission(id),
	engine_id int references engine(id)
);

select c.name, t.name, cb.name, e.name from car as c 
left outer join transmission as t on c.id_transmission = t.id
left outer join carbody as cb on c.id_carbody = cb.id
left outer join engine as e on c.id_engine = e.id;

select t.name from transmission as t 
left outer join car as c on c.id_transmission = t.id 
where c.id is null;

select cb.name from carbody as cb
left outer join car as c on c.id_carbody = cb.id 
where c.id is null;

select e.name from engine as e 
left outer join car as c on c.id_engine = e.id 
where c.id is null;




