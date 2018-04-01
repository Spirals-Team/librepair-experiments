package com.thiago.gerenciamentoTrafegoAereo.api.dao.implementacoes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiago.gerenciamentoTrafegoAereo.api.dao.interfaces.DaoAviaoInterface;
import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Aviao;
import com.thiago.gerenciamentoTrafegoAereo.api.repositorios.AviaoRepositorio;

@Service
public class DaoAviao implements DaoAviaoInterface
{
	@Autowired
	private AviaoRepositorio aviaoRepositorio;

	@Override
	public Optional<Aviao> buscarPorId(Long id)
	{
		return Optional.ofNullable(this.aviaoRepositorio.findByCodigo(id));
	}

	@Override
	public Optional<Aviao> inserir(Aviao aviao)
	{
		return Optional.ofNullable(this.aviaoRepositorio.save(aviao));
	}

	@Override
	public void deletar(Long id)
	{
		this.aviaoRepositorio.deleteById(id);
		
	}

	@Override
	public List<Aviao> listar()
	{
		return  this.aviaoRepositorio.findAll();
	}

}
