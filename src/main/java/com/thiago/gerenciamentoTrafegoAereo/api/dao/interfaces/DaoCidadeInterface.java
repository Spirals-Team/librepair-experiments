package com.thiago.gerenciamentoTrafegoAereo.api.dao.interfaces;

import java.util.List;
import java.util.Optional;

import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Cidade;

public interface DaoCidadeInterface
{
	Optional<Cidade> buscarPorId(Long id);
	
	Optional<Cidade> inserir(Cidade entidade);
	
	void deletar(Long id);
	
	List<Cidade> listar();
}
