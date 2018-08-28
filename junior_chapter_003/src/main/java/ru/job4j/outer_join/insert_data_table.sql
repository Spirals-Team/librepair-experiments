-- Добавляем данные в таблицу двигатель.
INSERT INTO engine (type) VALUES ('Бензиновый');
INSERT INTO engine (type) VALUES ('Дизельный');
INSERT INTO engine (type) VALUES ('Гибрид');

-- Добавляем данные в таблицу трансмиссия.
INSERT INTO transmission (type) VALUES ('Механическая');
INSERT INTO transmission (type) VALUES ('Автоматическая');

-- Добавить данные в таблицу шасси.
INSERT INTO chassis (type) VALUES ('Рамное');
INSERT INTO chassis (type) VALUES ('Без рамное');

-- Добаляем данные в таблицу автомабилей.
INSERT INTO car (name, engine_id, transmission_id, chassis_id) VALUES ('Ленд Ровер Defender', 1, 2, 1);
INSERT INTO car (name, engine_id, transmission_id, chassis_id) VALUES ('Инфинити QX80', 2, 1, 1);
INSERT INTO car (name, engine_id, transmission_id, chassis_id) VALUES ('Лада Калина', 1, 1, 2);