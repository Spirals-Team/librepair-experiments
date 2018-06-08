
CREATE TABLE evento (
  id bigint(20) NOT NULL,
  TITULO varchar(100) NOT NULL,
  DATA varchar(100) NOT NULL,
  DESCRICAO varchar(100) NOT NULL,
  FOTO varchar(100) NOT NULL,
  tipoevento_id bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estrutura da tabela flyway_schema_history
--



-- --------------------------------------------------------

--
-- Estrutura da tabela tipoevento
--

CREATE TABLE tipoevento (
  id bigint(20) NOT NULL,
  descricao varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table evento
--
ALTER TABLE evento
  ADD PRIMARY KEY (id);

--
-- Indexes for table flyway_schema_history
--


--
-- Indexes for table tipoevento
--
ALTER TABLE tipoevento
  ADD PRIMARY KEY (id);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table evento
--
ALTER TABLE evento
  MODIFY id bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table tipoevento
--
ALTER TABLE tipoevento
  MODIFY id bigint(20) NOT NULL AUTO_INCREMENT;

  
insert into tipoevento (descricao) values ("Festa");
insert into tipoevento (descricao) values ("Churrasco");

insert into evento (TITULO, DATA, DESCRICAO, FOTO, tipoevento_id) values ("Encontro de Carros", "Seg 18 Mai 2018", "Praesent tincidunt sed tellus ut rutrum sed vitae justo...", "1.jpg", 1);
insert into evento (TITULO, DATA, DESCRICAO, FOTO, tipoevento_id) values ("Festival de Rua", "Seg 18 Mai 2019", "Praesent tincidunt sed tellus ut rutrum sed vitae justo...", "2.jpg", 1);
insert into evento (TITULO, DATA, DESCRICAO, FOTO, tipoevento_id) values ("Passeio em Veneza", "Seg 18 Mai 2020", "Praesent tincidunt sed tellus ut rutrum sed vitae justo...", "3.jpg", 1);
insert into evento (TITULO, DATA, DESCRICAO, FOTO, tipoevento_id) values ("Travessia", "Seg 18 Mai 2020", "Praesent tincidunt sed tellus ut rutrum sed vitae justo...", "4.jpg", 1);