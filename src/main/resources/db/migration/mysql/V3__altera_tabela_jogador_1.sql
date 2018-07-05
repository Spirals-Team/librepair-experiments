ALTER TABLE jogador DROP COLUMN capitao;

ALTER TABLE jogador ADD CONSTRAINT fk_time
FOREIGN KEY (id_time) REFERENCES time (id) ;