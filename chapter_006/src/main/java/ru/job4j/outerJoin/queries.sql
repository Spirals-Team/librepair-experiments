-- Вывести все машины
SELECT * FROM car;



-- Вывести все неиспользуемые детали
select t.name from transmission as t left outer join car as c on c.transmission_id = t.id where c.id is null;
select e.name from engine as e left outer join car as c on c.engine_id = e.id where c.id is null;
select g.name from gearbox as g left outer join car as c on c.gearbox_id = g.id where c.id is null;