CREATE TABLE telefones (
  cliente_id INT NOT NULL,
  numero VARCHAR(15) NOT NULL,
  PRIMARY KEY (cliente_id, numero),
  CONSTRAINT cliente_id FOREIGN KEY (cliente_id) REFERENCES cliente (id)
);
