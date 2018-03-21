-- create database
CREATE DATABASE auth_test_database;

-- user configuration
CREATE USER auth_test_user WITH PASSWORD 'auth_test_user_password';
GRANT ALL PRIVILEGES ON DATABASE auth_test_database TO auth_test_user;