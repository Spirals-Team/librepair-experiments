
CREATE DATABASE unqdb;

USE unqdb;

TRUNCATE TABLE `unqdb`.`User`;
DROP TABLE `unqdb`.`User`;

CREATE TABLE `unqdb`.`User` (
  `CUIL` varchar(11) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `surname` varchar(30) DEFAULT NULL,
  `address` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`CUIL`),
  UNIQUE KEY `CUIL_UNIQUE` (`CUIL`)
);

select * from User;
