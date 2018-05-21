CREATE TABLE IF NOT EXISTS jogador (
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(255) NOT NULL,
  data_nascimento DATE NOT NULL,
  genero VARCHAR(255) NOT NULL,
  altura DOUBLE NOT NULL,
  ativo BOOLEAN NOT NULL,
  data_criacao DATETIME NOT NULL,
  data_atualizacao DATETIME NOT NULL,
  PRIMARY KEY (`id`)
  )engine=InnoDB DEFAULT CHARSET=utf8;

  INSERT INTO jogador(nome, data_nascimento, genero, altura, ativo, data_criacao, data_atualizacao)
        VALUES ('Mario Joaquim', '1998-03-12', 'mas', 1.78, true, '2018-04-26 18:30:47', '2018-04-26 18:30:47');

  INSERT INTO jogador(nome, data_nascimento, genero, altura, ativo, data_criacao, data_atualizacao)
        VALUES ('Fernandi', '2000-07-06', 'mas', 1.87, true, '2017-12-01 09:30:21', '2018-01-22 20:27:21');

