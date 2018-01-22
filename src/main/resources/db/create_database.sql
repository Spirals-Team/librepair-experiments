-- Run this with psql -U YourUser -f create_database.sql
CREATE USER "fp_psp_db" WITH ENCRYPTED PASSWORD 'fp_psp_db';
ALTER ROLE "fp_psp_db" WITH createdb;
\c "dbname=postgres user=fp_psp_db password=fp_psp_db";
CREATE database "fp_psp_db";
SELECT datname FROM pg_database WHERE datistemplate = false;