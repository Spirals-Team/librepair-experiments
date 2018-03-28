
--1 Написать запрос получение всех продуктов с типом "СЫР"
SELECT * FROM products AS p
INNER JOIN type AS t ON p.type_id = t.id WHERE t.name = 'СЫР';



--2 Написать запрос получения всех продуктов, у кого в имени есть слово "мороженое"
SELECT * FROM products WHERE name LIKE '%мороженое%';



--3 Написать запрос, который выводит все продукты, срок годности которых заканчивается в следующем месяце.
SELECT * FROM products WHERE expiry_date > 30;



--4 Написать запрос, который вывод самый дорогой продукт.
SELECT * FROM products WHERE
  price = (SELECT MAX (price) FROM products);



--5 Написать запрос, который выводит количество всех продуктов определенного типа.
SELECT COUNT(t.name) FROM products AS p
  INNER JOIN type AS t ON p.type_id = t.id WHERE t.name = 'СЫР';



--6 Написать запрос получение всех продуктов с типом "СЫР" и "МОЛОКО"
SELECT * FROM products AS p
  INNER JOIN type AS t ON p.type_id = t.id WHERE t.name = 'СЫР' OR t.name = 'МОЛОКО';



--7 Написать запрос, который выводит тип продуктов, у которых срок годности меньше 30 дней.
SELECT * FROM products WHERE expiry_date < 30;



--8 Вывести все продукты и их тип.
SELECT * FROM products AS p
  INNER JOIN type AS t ON p.type_id = t.id;