-- 1. Вывести все машины.
SELECT
  c.id,
  c.name,
  e.type,
  c2.type,
  e2.type
FROM car AS c
  INNER JOIN engine e ON c.engine_id = e.id
  INNER JOIN chassis c2 ON c.chassis_id = c2.id
  INNER JOIN engine e2 ON c.engine_id = e2.id;

-- 2. Вывести все неиспользуемые детали.
SELECT e.type
FROM car AS c
  RIGHT OUTER JOIN engine AS e ON c.engine_id = e.id
--RIGHT OUTER JOIN transmission AS e1 ON c.TRANSMISSION_ID = e1.id
--RIGHT OUTER JOIN chassis AS e2 ON c.CHASSIS_ID = e2.id
WHERE c.id IS NULL