package com.thiago.gerenciamentoTrafegoAereo.api.dao.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Voo;

public interface DaoVooInterface
{
	Optional<Voo> buscarPorId(Long id);

	Optional<Voo> inserir(Voo entidade);

	void deletar(Long id);

	List<Voo> listar();
	
	List<Voo> buscarPorCidadeOrigemAndDestino(Long cidadeOrigemCodigo, Long cidadeDestinoCodigo);
	
	List<Voo> buscarPorCidadeOrigemAndDestino(Long cidadeOrigemCodigo, Long cidadeDestinoCodigo, Pageable paginacao);
}
