-- The root user will be used: login: postgres, pswrd: ""

CREATE DATABASE testing_db;
\c testing_db;
CREATE TABLE testing_db.public.test_table (id SERIAL PRIMARY KEY, comment VARCHAR);
CREATE UNIQUE INDEX test_table_id_uindex ON testing_db.public.test_table (id);
