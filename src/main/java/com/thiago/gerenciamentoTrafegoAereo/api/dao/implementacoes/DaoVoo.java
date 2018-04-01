package com.thiago.gerenciamentoTrafegoAereo.api.dao.implementacoes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.thiago.gerenciamentoTrafegoAereo.api.dao.interfaces.DaoVooInterface;
import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Voo;
import com.thiago.gerenciamentoTrafegoAereo.api.repositorios.VooRepositorio;

@Service
public class DaoVoo implements DaoVooInterface
{
	@Autowired
	private VooRepositorio vooRepositorio;

	@Override
	public Optional<Voo> buscarPorId(Long id)
	{		
		return Optional.ofNullable(vooRepositorio.findByCodigo(id));
	}

	@Override
	public Optional<Voo> inserir(Voo entidade)
	{
		return Optional.ofNullable(vooRepositorio.save(entidade));
	}

	@Override
	public void deletar(Long id)
	{
		vooRepositorio.deleteById(id);		
	}

	@Override
	public List<Voo> listar()
	{
		return vooRepositorio.findAll();
	}

	@Override
	public List<Voo> buscarPorCidadeOrigemAndDestino(Long cidadeOrigemCodigo, Long cidadeDestinoCodigo)
	{		
		return vooRepositorio.findByCidadeOrigemCodigoAndCidadeDestinoCodigo(cidadeOrigemCodigo, cidadeDestinoCodigo);
	}

	@Override
	public List<Voo> buscarPorCidadeOrigemAndDestino(Long cidadeOrigemCodigo, Long cidadeDestinoCodigo, Pageable paginacao)
	{
		return vooRepositorio.findByCidadeOrigemCodigoAndCidadeDestinoCodigo(cidadeOrigemCodigo, cidadeDestinoCodigo, paginacao);
	}
}
