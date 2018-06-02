CREATE TABLE IF NOT EXISTS produto(
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(255) NOT NULL,
  preco DECIMAL(10,2) NULL,
  ativo BOOLEAN NOT NULL,
  PRIMARY KEY (id)
) engine=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO produto (nome, preco, ativo) VALUES ('Computador All-in-one Positivo', '2199.90', true);
INSERT INTO produto (nome, preco, ativo) VALUES ('Notebook Samsung Core i7', '2099.50', true);
INSERT INTO produto (nome, preco, ativo) VALUES ('HD SSD 500gb', '849.00', true);
