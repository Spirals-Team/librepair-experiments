-- Create new database.
CREATE DATABASE storage_of_cars;

-- Start use database "storage_of_cars". Command for Postgres.
\c storage_of_cars;

-- Create new table "transmissions", with fields: name, type, cost.
CREATE TABLE transmissions(
	name character varying (50),
	type character varying (50),
	cost integer,
	PRIMARY KEY(name)
);

-- Add new data into table "transmissions".
INSERT INTO transmissions(name, type, cost)
VALUES ('quattro', 'mechanical', 500000);

-- Add new data into table "transmissions".
INSERT INTO transmissions(name, type, cost)
VALUES ('4Matic', 'combined', 900000);

-- Add new data into table "transmissions".
INSERT INTO transmissions(name, type, cost)
VALUES ('xDrive', 'hydrovolume', 400000);

-- Create new table "engines", with fields: name, type, cost, car.
CREATE TABLE engines(
	name character varying (50),
	type character varying (50),
	cost integer,
	PRIMARY KEY(name)
);

-- Add new data into table "engines".
INSERT INTO engines(name, type, cost)
VALUES ('EJ20', 'gasoline', 456000);

-- Add new data into table "engines".
INSERT INTO engines(name, type, cost)
VALUES ('1‑AZ', 'gasoline', 399123);

-- Add new data into table "engines".
INSERT INTO engines(name, type, cost)
VALUES ('R20', 'gasoline', 577890);

-- Create new table "suspensions", with fields: name, type, cost.
CREATE TABLE suspensions(
	name character varying (50),
	type character varying (50),
	cost integer,
	PRIMARY KEY(name)
);

-- Add new data into table "suspensions".
INSERT INTO suspensions(name, type, cost)
VALUES ('kayaba', 'air', 600890);

-- Add new data into table "suspensions".
INSERT INTO suspensions(name, type, cost)
VALUES ('koni', 'air', 500890);

-- Add new data into table "suspensions".
INSERT INTO suspensions(name, type, cost)
VALUES ('boge', 'air', 400890);


-- Create new table "cars", with fields: name, transmission, engine, suspension.
CREATE TABLE cars(
	name character varying (50),
	transmission character varying (50) references transmissions(name),
	engine character varying (50) references engines(name),
	suspension character varying (50) references suspensions(name),
	PRIMARY KEY(name)
);

-- Add new data into table "cars".
INSERT INTO cars(name, transmission, engine, suspension)
VALUES ('audi', 'quattro', 'EJ20', 'kayaba');

-- Add new data into table "cars".
INSERT INTO cars(name, transmission, engine, suspension)
VALUES ('mercedes', '4Matic', 'R20', 'koni');

-- Show all cars.
SELECT * FROM cars;

-- Show all dont use transmissions.
SELECT t.name
FROM cars as c
	RIGHT OUTER JOIN transmissions as t ON c.transmission = t.name
WHERE c.transmission is null;

-- Show all dont use engines.
SELECT e.name
FROM cars as c
	RIGHT OUTER JOIN engines as e ON c.engine = e.name
WHERE c.engine is null;

-- Show all dont use suspensions.
SELECT s.name
FROM cars as c
	RIGHT OUTER JOIN suspensions as s ON c.suspension = s.name
WHERE c.suspension is null;
