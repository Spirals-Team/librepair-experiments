CREATE TABLE `Biblioteca`.`livro` ( 
	`id` INT NOT NULL AUTO_INCREMENT , 
	`codigo` VARCHAR(255) NOT NULL , 
	`tipo` INT NOT NULL , 
	`nome` VARCHAR(255) NOT NULL , 
	`data` VARCHAR(255) NOT NULL , 
	`edicao` INT NOT NULL , 
	`volume` INT NOT NULL , 
	`editora` INT NOT NULL , 
	`autor` INT NOT NULL , 
	`quantidade` INT NOT NULL , 
	`quantidadeEmprestimo` INT NOT NULL , 
	PRIMARY KEY (`id`)
) ENGINE = InnoDB;