-- Добавляем роли  в таблицу ролей
INSERT INTO roles (role) VALUES ('Administrator');
INSERT INTO roles (role) VALUES ('User');

-- Добавляем парава в таблицу прав
INSERT INTO rules (description) VALUES ('Добавлять пользователей');
INSERT INTO rules (description) VALUES ('Удалить пользователя');
INSERT INTO rules (description) VALUES ('Добавить файл');
INSERT INTO rules (description) VALUES ('Добавить комментарий');
INSERT INTO rules (description) VALUES ('Измменить состояние заявки');
INSERT INTO rules (description) VALUES ('Измменить права пользователя');
INSERT INTO rules (description) VALUES ('Создать заявку');
INSERT INTO rules (description) VALUES ('Удалить заявку');

-- Добавляем ролям права
INSERT INTO roles_rules (role_id, rules_id) VALUES (1, 1);
INSERT INTO roles_rules (role_id, rules_id) VALUES (1, 2);
INSERT INTO roles_rules (role_id, rules_id) VALUES (1, 3);
INSERT INTO roles_rules (role_id, rules_id) VALUES (1, 4);
INSERT INTO roles_rules (role_id, rules_id) VALUES (1, 5);
INSERT INTO roles_rules (role_id, rules_id) VALUES (1, 6);
INSERT INTO roles_rules (role_id, rules_id) VALUES (1, 7);
INSERT INTO roles_rules (role_id, rules_id) VALUES (1, 8);
INSERT INTO roles_rules (role_id, rules_id) VALUES (2, 3);
INSERT INTO roles_rules (role_id, rules_id) VALUES (2, 4);
INSERT INTO roles_rules (role_id, rules_id) VALUES (2, 5);
INSERT INTO roles_rules (role_id, rules_id) VALUES (2, 7);
INSERT INTO roles_rules (role_id, rules_id) VALUES (2, 8);

-- Добавляем пользователей
INSERT INTO users (login, password, role_id) VALUES ('Admin', '12345', 1);
INSERT INTO users (login, password, role_id) VALUES ('User', '54321', 2);

-- Добавляем категории для заявок
INSERT INTO category (category) VALUES ('Бухгалтерия');
INSERT INTO category (category) VALUES ('Доставка');
INSERT INTO category (category) VALUES ('Склад');

-- Добавляем статусы заявок
INSERT INTO state (status) VALUES ('Новая');
INSERT INTO state (status) VALUES ('В обработке');
INSERT INTO state (status) VALUES ('Заверщена');

-- Добавляем список заявок
INSERT INTO items (name, state_id, user_id, category_id) VALUES ('Выставить счет клиенту', 1, 2, 1);
INSERT INTO items (name, state_id, user_id, category_id) VALUES ('Доставить груз адресату', 2, 1, 2);
INSERT INTO items (name, state_id, user_id, category_id) VALUES ('Собрать груз на складе', 3, 2, 3);

-- Добавляем коментарии к заявкам
INSERT INTO comments (comment, item_id) VALUES ('Отправить счет фактуры', 1);
INSERT INTO comments (comment, item_id) VALUES ('Забыл указать адрес ул. Мира 350', 2);
INSERT INTO comments (comment, item_id) VALUES ('Просьба не ошибиться при сборке клиент из далека', 3);

-- Добавляем файлы к заявкам
INSERT INTO attached (file, item_id) VALUES ('1.jpg', 1);
INSERT INTO attached (file, item_id) VALUES ('Накладная.jpg', 2);
INSERT INTO attached (file, item_id) VALUES ('Список.jpg', 3);
