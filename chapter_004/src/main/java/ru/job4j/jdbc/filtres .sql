
create table type (
	id serial primary key,
	name character varying (1000)
);


create table product (
	id serial primary key,
	name character varying (1000),
	type_id integer references type(id),
	expired_date timestamp not null,
	price money
);

insert into type(name) values('СЫР');
insert into type(name) values('КОЛБАСА');
insert into type(name) values('МОЛОКО');

insert into product(name, type_id, expired_date, price) values('сыр голландский', 1, '2018-05-16 04:02:01', 50);
insert into product(name, type_id, expired_date, price) values('сыр чеддер', 1, '2018-05-15 04:02:01', 55);
insert into product(name, type_id, expired_date, price) values('сыр российский', 1, '2018-05-7 04:02:01', 40);
insert into product(name, type_id, expired_date, price) values('колбаса краковская', 2, '2018-05-16 04:02:01', 47);
insert into product(name, type_id, expired_date, price) values('колбаса докторская', 2, '2018-05-16 04:02:01', 40);
insert into product(name, type_id, expired_date, price) values('мороженное инмарко', 3, '2018-04-16 04:02:01', 10);
insert into product(name, type_id, expired_date, price) values('мороженное пломбир', 3, '2018-04-16 04:02:01', 5);

--1. Написать запрос получение всех продуктов с типом "СЫР"
select * from product as p 
inner join type as t on t.name='СЫР' AND p.type_id = t.id;

--2. Написать запрос получения всех продуктов, у кого в имени есть слово "мороженное"
select * from product where name like '%мороженное%';

--3. Написать запрос, который выводит все продукты, срок годности которых заканчивается в следующем месяце.
select * from product where expired_date between '2018-05-01 00:00:00' and '2018-05-30 23:59:59';

--4. Написать запрос, который вывод самый дорогой продукт.
select * from product where price=(select max(price) from product);

--5. Написать запрос, который выводит количество всех продуктов определенного типа.
select count(id) from product where type_id=1;

--6. Написать запрос получение всех продуктов с типом "СЫР" и "МОЛОКО"
select * from product as p 
inner join type as t on t.name in ('СЫР', 'МОЛОКО') AND p.type_id = t.id;

--7. Написать запрос, который выводит тип продуктов, которых осталось меньше 10 штук.  
select tp.name from  
(select p.type_id, count(p.name)
from type as t, product as p 
where p.type_id = t.id 
group by p.type_id
having count(p.name) < 10
order by p.type_id) as res, type as tp
where res.type_id = tp.id;

--8. Вывести все продукты и их тип.
select p.name, p.type_id, p.expired_date, p.price, t.name from product as p 
inner join type as t on p.type_id = t.id;

