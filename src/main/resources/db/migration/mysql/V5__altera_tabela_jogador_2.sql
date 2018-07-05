ALTER TABLE jogador ADD numero_camisa int(11) NOT NULL AFTER altura;
ALTER TABLE jogador ADD posicao VARCHAR(255) NOT NULL AFTER numero_camisa;

update jogador set numero_camisa = 10 posicao = 'Meia esquerda' where id=1;
update jogador set numero_camisa = 5 posicao = 'Volante' where id=2;
update jogador set numero_camisa = 1 posicao = 'Goleiro' where id=3;

