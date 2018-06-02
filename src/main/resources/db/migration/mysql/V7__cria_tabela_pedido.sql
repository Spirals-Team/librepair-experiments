CREATE TABLE pedido (
  id INT NOT NULL AUTO_INCREMENT,
  instante_criacao DATETIME NOT NULL,
  observacoes TEXT NULL,
  valor_desconto DECIMAL(10,2) NULL DEFAULT 0.0,
  valor_frete DECIMAL(10,2) NULL DEFAULT 0.0,
  cliente_id INT NOT NULL,
  entrega_logradouro VARCHAR(150) NOT NULL,
  entrega_numero VARCHAR(20) NOT NULL,
  entrega_cep VARCHAR(45) NOT NULL,
  PRIMARY KEY (id),
  INDEX fkCliente_id_idx (cliente_id ASC),
  CONSTRAINT fkCliente_id
    FOREIGN KEY (cliente_id)
    REFERENCES pedidovendas.cliente (id)

)ENGINE = InnoDB;

INSERT INTO pedido (instante_criacao, observacoes, valor_frete, cliente_id, entrega_logradouro, entrega_numero, entrega_cep)
VALUES ('2018-05-31 13:50:0059', 'Uma observacoes aqui!', '50', '1', 'Rua Sem fim', '10', '65000000');
