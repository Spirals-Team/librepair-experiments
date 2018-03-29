CREATE TABLE type(
  id SERIAL PRIMARY KEY,
  name character varying(256)
);

insert into type(name) values
  ('СЫР'),
  ('МОЛОКО'),
  ('ХЛЕБОБУЛОЧНЫЕ'),
  ('СЛАДКОЕ');

CREATE TABLE products(
  id SERIAL PRIMARY KEY,
  name character varying(256),
  type_id int REFERENCES type(id),
  expiry_date int,
  price int
);

insert into products(name, type_id, expiry_date, price) values
  ('Сыр обычный', 1, 25, 110),
  ('Молоко', 2, 10, 70),
  ('Хлеб', 3, 5, 40),
  ('Банановое мороженое', 4, 7, 90),
  ('Ванильное мороженое', 4, 7, 50),
  ('Рокфор', 1, 90, 500);
select * from items;