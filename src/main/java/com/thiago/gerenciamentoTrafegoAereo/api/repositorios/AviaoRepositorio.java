package com.thiago.gerenciamentoTrafegoAereo.api.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Aviao;

public interface AviaoRepositorio extends JpaRepository<Aviao, Long>
{
	@Transactional(readOnly = true)
	public Aviao findByCodigo(Long id); 
}
