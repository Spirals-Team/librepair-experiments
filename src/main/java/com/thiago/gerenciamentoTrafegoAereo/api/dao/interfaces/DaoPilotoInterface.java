package com.thiago.gerenciamentoTrafegoAereo.api.dao.interfaces;

import java.util.List;
import java.util.Optional;

import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Piloto;

public interface DaoPilotoInterface
{
	Optional<Piloto> buscarPorId(Long id);
	
	Optional<Piloto> inserir(Piloto entidade);
	
	void deletar(Long id);
	
	List<Piloto> listar();
}
