-- Create db
CREATE DATABASE manufacture;

CREATE TABLE transmission (
    id          SERIAL PRIMARY KEY,
    type        CHARACTER VARYING(100) UNIQUE NOT NULL,
    description TEXT
);

-- Create tables
CREATE TABLE engine (
    id          SERIAL PRIMARY KEY,
    type        CHARACTER VARYING(100) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE gearbox (
    id          SERIAL PRIMARY KEY,
    type        CHARACTER VARYING(100) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE cars (
    id              SERIAL PRIMARY KEY,
    manufacturer    CHARACTER VARYING(100) NOT NULL,
    model           CHARACTER VARYING(100) NOT NULL,
    transmission_id INTEGER                NOT NULL REFERENCES transmission (id),
    engine_id       INTEGER                NOT NULL REFERENCES engine (id),
    gearbox_id      INTEGER                NOT NULL REFERENCES gearbox (id),
    create_date     TIMESTAMP              NOT NULL DEFAULT now()
);

INSERT INTO transmission (type, description)
VALUES ('auto', 'auto transmission');
INSERT INTO transmission (type, description)
VALUES ('manual', 'manual transmission');
INSERT INTO transmission (type, description)
VALUES ('robot', 'robot transmission');

INSERT INTO engine (type, description) VALUES ('electric', 'electric engine');
INSERT INTO engine (type, description) VALUES ('fuel', 'fuel engine');
INSERT INTO engine (type, description) VALUES ('disel', 'disel engine');
INSERT INTO engine (type, description) VALUES ('hybrid', 'hybrid engine');

INSERT INTO gearbox (type, description) VALUES ('4x', '4x gearbox');
INSERT INTO gearbox (type, description) VALUES ('5x', '5x gearbox');
INSERT INTO gearbox (type, description) VALUES ('6x', '6x gearbox');
INSERT INTO gearbox (type, description) VALUES ('2x', '2x gearbox');

INSERT INTO cars (manufacturer, model, transmission_id, engine_id, gearbox_id)
VALUES ('volvo', 'xc60', 1, 2, 3);
INSERT INTO cars (manufacturer, model, transmission_id, engine_id, gearbox_id)
VALUES ('volvo', 'v40', 1, 2, 2);
INSERT INTO cars (manufacturer, model, transmission_id, engine_id, gearbox_id)
VALUES ('tesla', 'roadster', 1, 1, 2);
INSERT INTO cars (manufacturer, model, transmission_id, engine_id, gearbox_id)
VALUES ('McLaren', 'formula', 2, 2, 3);

-- Вывести все машины.
SELECT
    c.manufacturer,
    c.model,
    t.type,
    e.type,
    g.type
FROM cars c
    JOIN transmission t ON c.transmission_id = t.id
    JOIN engine e ON c.engine_id = e.id
    JOIN gearbox g ON c.gearbox_id = g.id;

-- Вывести все неиспользуемые детали.
SELECT COALESCE(t.type, e.type, g.type) AS unused_parts
FROM cars c
    FULL JOIN transmission t ON c.transmission_id = t.id
    FULL JOIN engine e ON c.engine_id = e.id
    FULL JOIN gearbox g ON c.gearbox_id = g.id
WHERE c.id IS NULL;