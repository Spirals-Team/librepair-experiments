--0. Создать базу данных
CREATE database car_service;

--1. Создать структур данных в базе
CREATE TABLE engine (
    id_e serial NOT NULL PRIMARY KEY,
    name_e VARCHAR(45) NOT NULL
);

CREATE TABLE frame (
    id_f serial NOT NULL PRIMARY KEY,
    name_f VARCHAR(45) NOT NULL
);

CREATE TABLE transmission (
    id_t serial NOT NULL PRIMARY KEY,
    name_t VARCHAR(45) NOT NULL
);

--2. Создать структуру Машина. 
--Машина не может существовать без данных из п.1
CREATE TABLE car (
    id_c serial NOT NULL PRIMARY KEY,
    name_c VARCHAR(45) NOT NULL,
    id_f INT NOT NULL,
    id_e INT NOT NULL,
    id_t INT NOT NULL,
    CONSTRAINT fk_engine FOREIGN KEY (id_e)
        REFERENCES engine (id_e)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_frame FOREIGN KEY (id_f)
        REFERENCES frame (id_f)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_transmission FOREIGN KEY (id_t)
        REFERENCES transmission (id_t)
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

--3. Заполнить таблицы через insert
INSERT INTO engine(id_e, name_e)	VALUES (1, 'engine1');
INSERT INTO engine(id_e, name_e)	VALUES (2, 'engine2');
INSERT INTO engine(id_e, name_e)	VALUES (3, 'engine3');
INSERT INTO engine(id_e, name_e)	VALUES (4, 'engine4');

INSERT INTO frame(id_f, name_f) VALUES (1, 'frame1');
INSERT INTO frame(id_f, name_f) VALUES (2, 'frame2');
INSERT INTO frame(id_f, name_f) VALUES (3, 'frame3');

INSERT INTO transmission(id_t, name_t) VALUES (1, 'transmission1');
INSERT INTO transmission(id_t, name_t) VALUES (2, 'transmission2');
INSERT INTO transmission(id_t, name_t) VALUES (3, 'transmission3');
INSERT INTO transmission(id_t, name_t) VALUES (4, 'transmission4');

INSERT INTO car(id_c, name_c, id_f, id_e, id_t)
	VALUES (1, 'Type1', 1, 1, 1);
INSERT INTO car(id_c, name_c, id_f, id_e, id_t)
	VALUES (2, 'Type2', 2, 1, 1);
INSERT INTO car(id_c, name_c, id_f, id_e, id_t)
	VALUES (3, 'Type3', 3, 1, 2);
INSERT INTO car(id_c, name_c, id_f, id_e, id_t)
	VALUES (4, 'Type4', 1, 2, 3);
INSERT INTO car(id_c, name_c, id_f, id_e, id_t)
	VALUES (5, 'Type5', 3, 3, 3);

--Создать SQL запросы:	
--1. Вывести список всех машин и все привязанные к ним детали
SELECT c.name_c, f.name_f, e.name_e, t.name_t FROM car c INNER JOIN frame f
ON c.id_f = f.id_f INNER JOIN engine e
ON c.id_e = e.id_e INNER JOIN transmission t
ON c.id_t = t.id_t;

--2. Вывести отдельно детали, которые не используются в машине, 
--кузова, двигатели, коробки передач
SELECT f.id_f, f.name_f FROM frame f LEFT OUTER JOIN car c
ON f.id_f = c.id_f
WHERE c.id_c IS NULL;

SELECT e.id_e, e.name_e FROM engine e LEFT OUTER JOIN car c
ON e.id_e = c.id_e
WHERE c.id_c IS NULL;

SELECT t.id_t, t.name_t FROM transmission t LEFT OUTER JOIN car c
ON t.id_t = c.id_t
WHERE c.id_c IS NULL;

