-- Start use database "system_of_applications". Command for Postgres.
\c system_of_applications;

-- Add new data into table "users".
INSERT INTO users(name, role, roleRights)
VALUES ('andrey', 'student', 'study');

-- Add new data into table "users".
INSERT INTO users(name, role, roleRights)
VALUES ('ivan', 'student', 'study');

-- Add new data into table "users".
INSERT INTO users(name, role, roleRights)
VALUES ('petr', 'teacher', 'learn');

-- Add new data into table "users".
INSERT INTO users(name, role, roleRights)
VALUES ('victor', 'teacher', 'learn');

-- Add new data into table "aplications".
INSERT INTO aplications
(name, comments, status, categories, responsible, role, permission)
VALUES ('filter', 'make sql filter', TRUE, 'sql', 'anton', 'student', TRUE);

-- Add new data into table "aplications".
INSERT INTO aplications
(name, comments, status, categories, responsible, role, permission)
VALUES ('youtube', 'add new video', TRUE, 'youtube', 'petr', 'teacher', TRUE);

-- Add first filter: get name all teachers.
SELECT u.name FROM users as u
WHERE u.role = 'teacher';

-- Add second filter: get name of users and name of aplications, with true status.
SELECT u.name, a.name FROM users as u
INNER JOIN aplications as a on u.name = a.responsible
WHERE a.status = TRUE;

-- Add third filter: get name and role of users, where name "an...".
SELECT u.name, u.role FROM users as u
WHERE u.name LIKE 'an%';

-- Add fourth filter: get user name, user role, aplication name,
-- aplication categories, aplication status where user name start from a to p.
SELECT u.name, u.role, a.name, a.categories, a.status FROM users as u
INNER JOIN aplications as a on u.name = a.responsible
WHERE u.name between 'a' and 'q';
