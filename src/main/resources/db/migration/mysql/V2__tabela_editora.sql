CREATE TABLE `Biblioteca`.`editora` ( 
	`id` INT NOT NULL AUTO_INCREMENT , 
	`codigo` VARCHAR(255) NOT NULL , 
	`nome` VARCHAR(255) NOT NULL , 
	`nacional` BOOLEAN NOT NULL , 
	PRIMARY KEY (`id`)
) ENGINE = InnoDB;