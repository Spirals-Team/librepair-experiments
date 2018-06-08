INSERT INTO usuario (nome, rg, email, senha, foto, tipo, ativo) 
VALUES ("Rafael Calearo", "1091507911", "rafaelcalearo@theevents.com", "12345", "-", "ADMIN", "0");
INSERT INTO usuario (nome, rg, email, senha, foto, tipo, ativo) 
VALUES ("Michel Luz", "1068475704", "michelluz@theevents.com", "54321", "-", "ADMIN", "0");
INSERT INTO usuario (nome, rg, email, senha, foto, tipo, ativo) 
VALUES ("Silvio Santos", "1000000105", "silvinho.santos@theevents.com", "silviorico", "-", "USER", "0");

INSERT INTO tipoevento (descricao_tipo_evento, usuario_id) VALUES ("Festa Junina", 1);
INSERT INTO tipoevento (descricao_tipo_evento, usuario_id) VALUES ("Festa Country", 2);

INSERT INTO evento (usuario_id, tipoevento_id, titulo, data_criacao, descricao, local, foto, ativo) 
VALUES (1, 1, "Array do Gugu", "30 mar 2019", "Bla, Bla, Bla, e pipoca.", "Teatro Guarani", "-", "0");
INSERT INTO evento (usuario_id, tipoevento_id, titulo, data_criacao, descricao, local, foto, ativo) 
VALUES (2, 2, "Barreto in Fest", "30 nov 2018", "Bla, Bla, Bla, e vaca.", "AV. Bento Gonçalves", "-", "0");