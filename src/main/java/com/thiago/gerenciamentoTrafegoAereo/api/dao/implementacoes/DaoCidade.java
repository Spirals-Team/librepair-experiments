package com.thiago.gerenciamentoTrafegoAereo.api.dao.implementacoes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiago.gerenciamentoTrafegoAereo.api.dao.interfaces.DaoCidadeInterface;
import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Cidade;
import com.thiago.gerenciamentoTrafegoAereo.api.repositorios.CidadeRepositorio;

@Service
public class DaoCidade implements DaoCidadeInterface
{
	@Autowired
	private CidadeRepositorio cidadeRepositorio;

	@Override
	public Optional<Cidade> buscarPorId(Long id)
	{
		return Optional.ofNullable(cidadeRepositorio.findByCodigo(id));
	}

	@Override
	public Optional<Cidade> inserir(Cidade entidade)
	{
		return Optional.ofNullable(cidadeRepositorio.save(entidade));
	}

	@Override
	public void deletar(Long id)
	{
		cidadeRepositorio.deleteById(id);		
	}

	@Override
	public List<Cidade> listar()
	{		
		return cidadeRepositorio.findAll();
	}

}
