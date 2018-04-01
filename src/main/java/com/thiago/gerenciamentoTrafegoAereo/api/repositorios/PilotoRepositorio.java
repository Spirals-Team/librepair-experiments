package com.thiago.gerenciamentoTrafegoAereo.api.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Piloto;

public interface PilotoRepositorio extends JpaRepository<Piloto, Long>
{
	@Transactional(readOnly = true)
	public Piloto findByCodigo(Long id); 
}
