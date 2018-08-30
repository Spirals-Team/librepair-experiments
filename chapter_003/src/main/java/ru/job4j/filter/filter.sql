-- 1. Написать запрос получение всех продуктов с типом "СЫР"
SELECT *
FROM type t
    JOIN product p ON t.id = p.type_id
WHERE upper(t.name) = 'СЫР';

-- 2. Написать запрос получения всех продуктов, у кого в имени есть слово "мороженное"
SELECT *
FROM product
WHERE lower(name) LIKE '%мороженное%';

-- 3. Написать запрос, который выводит все продукты, срок годности которых заканчивается в следующем месяце.
WITH exp_d AS (
    SELECT
        id,
        date_part('month', expired_date) AS month,
        date_part('year', expired_date)  AS year
    FROM product),
        cy AS (SELECT EXTRACT(YEAR FROM CURRENT_DATE) AS current_y),
        cm AS (SELECT EXTRACT(MONTH FROM CURRENT_DATE) AS current_m)
SELECT *
FROM product p
    JOIN exp_d ON p.id = exp_d.id
    JOIN cy ON exp_d.year = cy.current_y
    JOIN cm ON exp_d.month = cm.current_m + 1;

-- 4. Написать запрос, который вывод самый дорогой продукт.
SELECT max(price) AS max_price
FROM product;

-- 5. Написать запрос, который выводит количество всех продуктов определенного типа.
SELECT count(*) AS quantity
FROM type t
    JOIN product p ON t.id = p.type_id
WHERE upper(t.name) = 'СЫР';

-- 6. Написать запрос получение всех продуктов с типом "СЫР" и "МОЛОКО"
SELECT *
FROM type t
    JOIN product p ON t.id = p.type_id
WHERE upper(t.name) IN ('СЫР', 'МОЛОКО');

-- 7. Написать запрос, который выводит тип продуктов, которых осталось меньше 10 штук.
SELECT
    t.name,
    count(p.type_id) AS quantity
FROM type t
    JOIN product p ON t.id = p.type_id
GROUP BY t.name
HAVING count(p.type_id) < 10;

-- 8. Вывести все продукты и их тип.
SELECT *
FROM type t
    JOIN product p ON t.id = p.type_id;