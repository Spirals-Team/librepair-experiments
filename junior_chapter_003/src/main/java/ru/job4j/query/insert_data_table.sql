-- Добавляем тип продукта
INSERT INTO type (name) VALUES ('Сыр');
INSERT INTO type (name) VALUES ('Молоко');

-- Добавляем продукты
INSERT INTO product (name, type_id, expired_date, price) VALUES ('Сырные палочки', 1, '2018-02-05 12:00:00', 10);
INSERT INTO product (name, type_id, expired_date, price) VALUES ('Хачапури', 1, '2018-03-05 12:00:00', 3);
INSERT INTO product (name, type_id, expired_date, price) VALUES ('Сырные шарики', 1, '2018-02-15 12:00:00', 20);
INSERT INTO product (name, type_id, expired_date, price) VALUES ('Гренки с сыром', 1, '2018-02-11 12:00:00', 7);
INSERT INTO product (name, type_id, expired_date, price) VALUES ('Мороженое', 2, '2018-02-07 12:00:00', 20);
INSERT INTO product (name, type_id, expired_date, price) VALUES ('Молоко 3.5%', 2, '2018-02-10 12:00:00', 9);
INSERT INTO product (name, type_id, expired_date, price) VALUES ('Йогурт', 2, '2018-02-15 12:00:00', 13);
INSERT INTO product (name, type_id, expired_date, price) VALUES ('Кефир', 2, '2018-02-13 12:00:00', 8);