package com.thiago.gerenciamentoTrafegoAereo.api.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Cidade;

public interface CidadeRepositorio extends JpaRepository<Cidade, Long>
{
	@Transactional(readOnly = true)
	public Cidade findByCodigo(Long id);
	
}
