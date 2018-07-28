--1. Написать запрос получение всех продуктов с типом "СЫР"
select p.name, p.expired_date, p.price
from product p inner join type t
on p.type_id = t.id
where t.name = 'СЫР';

--2. Написать запрос получения всех продуктов,
--у кого в имени есть слово "мороженное"
select p.name, p.expired_date, p.price 
from product p
where p.name like '%мороженное%';

--3. Написать запрос, который выводит все продукты,
--срок годности которых заканчивается в следующем месяце
select p.name, p.expired_date, p.price 
from product p
where p.expired_date between '2018-07-01' and '2018-12-30';

--4. Написать запрос, который вывод самый дорогой продукт
select p.name, p.expired_date, p.price 
from product p
where p.price = (select max(price) from product);

--5. Написать запрос, который выводит количество 
--всех продуктов определенного типа
select count(*) 
from type t inner join product p 
on t.id = p.type_id
where t.name = 'СЫР';

--6. Написать запрос получение всех 
--продуктов с типом "СЫР" и "МОЛОКО"
select p.name, p.expired_date, p.price, p.type_id 
from product p inner join type t 
on p.type_id = t.id
where t.name in ('МОЛОКО', 'СЫР');

--7. Написать запрос, который выводит тип продуктов,
--которых осталось меньше 10 штук
select t.name 
from type t inner join product p 
on t.id = p.type_id 
group by (t.name)
having count(*) < 10;

--8. Вывести все продукты и их тип
select p.name, p.expired_date, p.price, t.name "type of product" 
from public.product p inner join public.type t 
on p.type_id = t.id;






