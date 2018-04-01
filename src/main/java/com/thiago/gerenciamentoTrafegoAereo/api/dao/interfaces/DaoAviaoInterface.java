package com.thiago.gerenciamentoTrafegoAereo.api.dao.interfaces;

import java.util.List;
import java.util.Optional;

import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Aviao;

public interface DaoAviaoInterface
{
	Optional<Aviao> buscarPorId(Long id);
	
	Optional<Aviao> inserir(Aviao entidade);
	
	void deletar(Long id);
	
	List<Aviao> listar();
}
