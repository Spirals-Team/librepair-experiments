ALTER TABLE jogador ADD numero_camisa int(11) NOT NULL AFTER altura;
ALTER TABLE jogador ADD posicao VARCHAR(255) NOT NULL AFTER numero_camisa;

UPDATE jogador SET numero_camisa = 10, posicao = 'Meia esquerda' WHERE id = 1;
UPDATE jogador SET numero_camisa = 5, posicao = 'Volante' WHERE id = 2;
UPDATE jogador SET numero_camisa = 1, posicao = 'Goleiro' WHERE id = 3;


