# Create TestUser
CREATE USER 'springboot'@'localhost' IDENTIFIED BY 'password';

# Privileges to TestUser
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP ON *.* TO 'springboot'@'localhost';

# Create DB
CREATE DATABASE IF NOT EXISTS `db_example` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `db_example`;

# Create Table
CREATE TABLE IF NOT EXISTS `sql_grid` (
  `id` int PRIMARY KEY,
  `matrix` TEXT,
  `n` int
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# Add Data (queries if you want)