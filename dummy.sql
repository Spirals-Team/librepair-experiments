CREATE TABLE IF NOT EXISTS dummy(
	socialGroup	VARCHAR,
	num	INT,
	name	VARCHAR,
	PRIMARY KEY (num)
);

INSERT INTO dummy (socialGroup, num, name) VALUES
('100', 0, 'CONNIE'),
('100', 1, 'RONNIE');
