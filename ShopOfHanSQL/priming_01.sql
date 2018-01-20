DROP DATABASE IF EXISTS shop_of_han_database;

CREATE DATABASE IF NOT EXISTS shop_of_han_database;

USE shop_of_han_database;

CREATE TABLE IF NOT EXISTS stock
(
ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
  product_name VARCHAR (150) NOT NULL,
  product_id VARCHAR (20) NOT NULL,
  amount INT,PRIMARY KEY (ID)
);

INSERT INTO stock (product_name, product_id, amount) VALUES
('Joy OF JAVA ', 'JOJ1', 4),
('SQL THE sequel', 'STS1', 0);