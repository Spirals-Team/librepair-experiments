-- Create new database.
CREATE DATABASE system_of_applications;

-- Start use database "system_of_applications". Command for Postgres.
\c system_of_applications;

-- Create new table "users", with fields: name, role, roleRights.
-- Primary key "name".
CREATE TABLE users(
	name character varying (50),
	role character varying (50),
	roleRights character varying (50),
	PRIMARY KEY(name)
);

-- Create new table "aplications", with fields: name, comments, status,
-- categories, responsible, role, permission. Primary key "name".
CREATE TABLE aplications (
	name character varying (50),
	comments character varying (250),
	status boolean,
	categories character varying (50),
	responsible character varying (50) references users(name),
	role character varying (50),
	permission boolean,
	PRIMARY KEY(name)
);

-- Add new data into table "users".
INSERT INTO users(name, role, roleRights)
VALUES ('anton', 'student', 'study');

-- Add new data into table "aplications".
INSERT INTO aplications(name, comments, status, categories, responsible, role,
permission)
VALUES ('task 1', 'my first task', TRUE, 'sql', 'anton', 'student', TRUE);
