package com.thiago.gerenciamentoTrafegoAereo.api.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.thiago.gerenciamentoTrafegoAereo.api.entidades.VooHistoricoStatus;

public interface VooHistoricoStatusRepositorio extends JpaRepository<VooHistoricoStatus, Long>
{
	@Transactional(readOnly = true)
	public VooHistoricoStatus findByCodigo(Long id);
}
