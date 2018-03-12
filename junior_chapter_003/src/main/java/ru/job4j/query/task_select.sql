-- 1. Написать запрос получение всех продуктов с типом "СЫР"
SELECT product.name, t.name FROM product
INNER JOIN type t ON product.type_id = t.id
WHERE t.name = 'Сыр';
-- 2. Написать запрос получения всех продуктов, у кого в имени есть слово "мороженное"
SELECT * FROM product AS p WHERE p.name LIKE '%Мороженое%';
-- 3. Написать запрос, который выводит все продукты, срок годности которых заканчивается в следующем месяце.
SELECT * FROM product AS p WHERE p.expired_date > '2018-03-01 12:00:00.000000';
-- 4. Написать запрос, который вывод самый дорогой продукт.
SELECT * FROM product AS p WHERE p.price = (SELECT max(product.price) FROM product);
-- 5. Написать запрос, который выводит количество всех продуктов определенного типа.
SELECT count(*) FROM product AS p WHERE p.type_id = 1;
-- 6. Написать запрос получение всех продуктов с типом "СЫР" и "МОЛОКО"
SELECT * FROM product AS p WHERE p.type_id IN (1, 2);
-- 7. Написать запрос, который выводит тип продуктов, которых осталось меньше 10 штук.
SELECT p.name, p.type_id FROM product AS p WHERE p.price < 10;
-- 8. Вывести все продукты и их тип.
SELECT p.name, t.name FROM product AS p INNER JOIN type as t ON p.type_id = t.id;