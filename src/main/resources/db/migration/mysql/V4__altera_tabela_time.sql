ALTER TABLE time ADD id_capitao int(11) AFTER nome;

ALTER TABLE time ADD CONSTRAINT fk_jogador
FOREIGN KEY (id_capitao) REFERENCES jogador (id);

UPDATE time SET id_capitao = 1 WHERE id = 1;
UPDATE time SET id_capitao = 3  WHERE id = 2;


