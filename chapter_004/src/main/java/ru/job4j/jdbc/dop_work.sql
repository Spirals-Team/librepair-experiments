--CREATE TABLE company
--(
--id integer NOT NULL,
--name character varying,
--CONSTRAINT company_pkey PRIMARY KEY (id)
--);
--CREATE TABLE person
--(
--id integer NOT NULL,
--name character varying,
--company_id integer,
--CONSTRAINT person_pkey PRIMARY KEY (id)
--);
--insert into company(id, name) values(1, 'газпром');
--insert into company(id, name) values(2, 'сургутнефтегаз');
--insert into company(id, name) values(3, 'роснефть');
--insert into company(id, name) values(4, 'лукойл');
--insert into company(id, name) values(5, 'татнефть');
--insert into company(id, name) values(6, 'новатэк');

--insert into person(id, name, company_id) values(1, 'Alex1', 1);
--insert into person(id, name, company_id) values(2, 'Alex2', 1);
--insert into person(id, name, company_id) values(3, 'Alex3', 2);
--insert into person(id, name, company_id) values(4, 'Alex4', 2);
--insert into person(id, name, company_id) values(5, 'Alex5', 3);
--insert into person(id, name, company_id) values(6, 'Alex6', 3);
--insert into person(id, name, company_id) values(7, 'Alex7', 4);
--insert into person(id, name, company_id) values(8, 'Alex8', 5);
--insert into person(id, name, company_id) values(9, 'Alex9', 5);
--insert into person(id, name, company_id) values(10, 'Alex10', 6);
--insert into person(id, name, company_id) values(11, 'Alex11', 1);
--select * from person;

-- names of all persons that are NOT in the company with id = 5
--select p.name from person as p
--inner join company as c on c.id = p.company_id
--where p.company_id <> 5;

-- company name for each person
--select p.name, c.name from person as p
--inner join company as c on c.id = p.company_id;

--Select the name of the company with the maximum number of persons + number of persons in this company
select countbydept.*
from
(
  select company_id, count(*) as counter
  from person
  group by company_id
  order by counter desc
  limit 1
) as maxcount
inner join
(
  select
    company.id,
    company.name,
    count(*) as NumberOfEmployees
  from company
  inner join person on person.company_id = company.id
  group by company.id, company.name
) countbydept on countbydept.numberofemployees = maxcount.counter;



