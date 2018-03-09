package com.kazale.pontointeligente.api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.kazale.pontointeligente.api.entities.Lancamento;

@Transactional(readOnly = true)
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

	List<Lancamento> findByFuncionarioId(Long funcionarioId);
	
	Page<Lancamento> findByFuncionarioId(Long funcionarioId, Pageable pageable);
}
