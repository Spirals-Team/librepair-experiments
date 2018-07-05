CREATE TABLE IF NOT EXISTS jogador (
  id INTEGER NOT NULL AUTO_INCREMENT,
  nome VARCHAR(255) NOT NULL ,
  data_nascimento DATE NOT NULL,
  genero VARCHAR(255) NOT NULL ,
  altura DOUBLE NOT NULL,
  ativo BOOLEAN NOT NULL,
  capitao BOOLEAN NULL,
  id_time INTEGER NOT NULL,
  PRIMARY KEY (`id`)
    )engine=InnoDB DEFAULT CHARSET=utf8;

  INSERT INTO jogador(nome, data_nascimento, genero, altura, ativo, capitao, id_time)
        VALUES ('Mario Joaquim', '1998-03-12', 'M', 1.78, true, false,1);

  INSERT INTO jogador(nome, data_nascimento, genero, altura, ativo, capitao, id_time)
        VALUES ('Fernandi', '2000-07-06', 'M', 1.87, true, true, 1);

  INSERT INTO jogador(nome, data_nascimento, genero, altura, ativo, capitao, id_time)
        VALUES ('Pedro', '2000-07-06', 'M', 1.87, false, true, 2);