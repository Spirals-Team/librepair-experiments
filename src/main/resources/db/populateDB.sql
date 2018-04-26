DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM users;
DELETE FROM meals;
DELETE FROM menus;
DELETE FROM restaurants;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO USERS (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@yandex.ru','admin');

INSERT INTO USER_ROLES (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001),
  ('ROLE_USER', 100001);

INSERT INTO RESTAURANTS (NAME) VALUES
  ('Russia'),
  ('Ukraine'),
  ('U Kolyana'),
  ('Almaz'),
  ('Fart');

INSERT INTO MENUS (ID_REST, ADD_DATE) VALUES
  (100002, '2015-05-01'),
  (100003, '2015-05-02'),
  (100004, '2015-05-03'),
  (100005, '2015-05-01'),
  (100006, '2015-05-02'),
  (100002, '2015-05-02'),
  (100002, CURRENT_DATE);

INSERT INTO MEALS (ID_MENU, NAME, PRICE) VALUES
  (100007, 'Apple', 5),
  (100008, 'Juice', 10),
  (100009, 'Potato', 20),
  (100010, 'Bananas', 30),
  (100011, 'Bread', 40),
  (100007, 'Bottle of water', 50),
  (100013, 'Mango', 100);

INSERT INTO VOTES (USER_ID, DATE, RESTAURANT) VALUES
  (100000, '2015-05-01', 100002),
  (100000, '2015-05-02', 100003),
  (100000, '2015-05-03', 100004),
  (100001, '2015-05-01', 100005),
  (100001, '2015-05-02', 100006),
  (100001, '2015-05-03', 100002);

