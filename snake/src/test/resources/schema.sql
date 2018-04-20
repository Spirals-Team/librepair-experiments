
CREATE TABLE usuario (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  username varchar(255),
  password varchar(255) NOT NULL DEFAULT '123456',
  enabled bit(1) NOT NULL DEFAULT 1,
  ultimo_acceso datetime,
  PRIMARY KEY (id),
  UNIQUE KEY (username)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE billetera (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  usuario_id bigint(20) NOT NULL,
  moneda_nombre varchar(50),
  cantidad decimal(21,12),
  PRIMARY KEY (id),
  FOREIGN KEY (usuario_id) REFERENCES usuario(id),
  UNIQUE KEY (usuario_id, moneda_nombre)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;


CREATE TABLE transaccion (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  billetera_id bigint(20) NOT NULL,
  moneda_nombre varchar(50),
  tipo varchar(50),
  cantidad decimal(21,12),
  cotizacion decimal(21,12),
  fecha datetime,
  PRIMARY KEY (id),
  FOREIGN KEY (billetera_id) REFERENCES billetera(id),
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;




