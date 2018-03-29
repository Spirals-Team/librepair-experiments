CREATE TABLE transmission(
  id SERIAL PRIMARY KEY,
  name VARCHAR(256)
);
INSERT INTO transmission(name) VALUES('механическая'), ('электрическая');

CREATE TABLE engine(
  id SERIAL PRIMARY KEY,
  name VARCHAR(256)
);
INSERT INTO engine(name) VALUES('бензиновые'), ('дизельные'), ('газовые');

CREATE TABLE gearbox(
  id SERIAL PRIMARY KEY,
  name VARCHAR(256)
);
INSERT INTO gearbox(name) VALUES('механическая'), ('автоматическая'), ('роботизированная'), ('вариативная');

CREATE TABLE car(
  id SERIAL PRIMARY KEY,
  name VARCHAR(256),
  transmission_id INT REFERENCES transmission(id),
  engine_id INT REFERENCES engine(id),
  gearbox_id INT REFERENCES gearbox(id)
);
INSERT INTO car(name, transmission_id, engine_id, gearbox_id) VALUES
  ('Reno', 1, 1, 1),
  ('Mazda', 1, 2, 1),
  ('Opel', 1, 1, 2);
