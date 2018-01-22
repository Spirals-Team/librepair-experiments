
-- Rename country table

ALTER TABLE system.country RENAME to countries;

ALTER TABLE system.countries RENAME country_id to id;

DROP SEQUENCE system.country_country_id_seq CASCADE;

CREATE SEQUENCE system.countries_id_seq;
ALTER TABLE system.countries ALTER COLUMN id SET NOT NULL;
ALTER TABLE system.countries ALTER COLUMN id SET DEFAULT nextval('system.countries_id_seq');
ALTER SEQUENCE system.countries_id_seq OWNED BY system.countries.id;


-- Rename city table
ALTER TABLE system.city RENAME to cities;

ALTER TABLE system.cities RENAME city_id to id;

DROP SEQUENCE system.city_city_id_seq CASCADE;

CREATE SEQUENCE system.cities_id_seq;
ALTER TABLE system.cities ALTER COLUMN id SET NOT NULL;
ALTER TABLE system.cities ALTER COLUMN id SET DEFAULT nextval('system.cities_id_seq');
ALTER SEQUENCE system.cities_id_seq OWNED BY system.cities.id;

-- Inserts default values
INSERT INTO system.countries (country) VALUES ('Paraguay');
INSERT INTO system.cities (city) VALUES ('Asunción');