package com.thiago.gerenciamentoTrafegoAereo.api.dao.interfaces;

import java.util.List;
import java.util.Optional;

import com.thiago.gerenciamentoTrafegoAereo.api.entidades.VooHistoricoStatus;

public interface DaoVooHistoricoStatusInterface
{
	Optional<VooHistoricoStatus> buscarPorId(Long id);

	Optional<VooHistoricoStatus> inserir(VooHistoricoStatus entidade);

	void deletar(Long id);

	List<VooHistoricoStatus> listar();
}
