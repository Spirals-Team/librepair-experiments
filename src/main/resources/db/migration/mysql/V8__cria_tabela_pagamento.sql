CREATE TABLE pagamento (
  pedido_id INT NOT NULL,
  situacao VARCHAR(30) NULL,
  PRIMARY KEY (pedido_id),
  CONSTRAINT fkPedidoIdPagamento FOREIGN KEY (pedido_id) REFERENCES pedido(id)
)ENGINE=InnoDB;

CREATE TABLE pagamento_cartao (
  pedido_id INT NOT NULL,
  numero_parcelas SMALLINT NOT NULL,
  PRIMARY KEY (pedido_id),
  CONSTRAINT fkPedidoIdCartao FOREIGN KEY (pedido_id) REFERENCES pedido(id)
)ENGINE=InnoDB;


CREATE TABLE pagamento_boleto (
  pedido_id INT NOT NULL,
  data_vencimento DATE NOT NULL,
  data_pagamento DATE NULL,
  PRIMARY KEY (pedido_id),
  CONSTRAINT fkPedidoIdBoleto FOREIGN KEY (pedido_id) REFERENCES pedido(id)
)ENGINE=InnoDB;

INSERT INTO pagamento(pedido_id, situacao) VALUES (1, 'QUITADO');
INSERT INTO pagamento_cartao(pedido_id, numero_parcelas) VALUES (1, 3);

