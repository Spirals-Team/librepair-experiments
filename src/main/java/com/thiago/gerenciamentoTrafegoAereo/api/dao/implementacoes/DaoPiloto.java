package com.thiago.gerenciamentoTrafegoAereo.api.dao.implementacoes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiago.gerenciamentoTrafegoAereo.api.dao.interfaces.DaoPilotoInterface;
import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Piloto;
import com.thiago.gerenciamentoTrafegoAereo.api.repositorios.PilotoRepositorio;

@Service
public class DaoPiloto implements DaoPilotoInterface
{
	@Autowired
	private PilotoRepositorio pilotoRespositorio;

	@Override
	public Optional<Piloto> buscarPorId(Long codigo)
	{
		return Optional.ofNullable(this.pilotoRespositorio.findByCodigo(codigo));
	}

	@Override
	public Optional<Piloto> inserir(Piloto piloto)
	{
		return Optional.ofNullable(this.pilotoRespositorio.save(piloto));
	}

	@Override
	public void deletar(Long codigo)
	{
		this.pilotoRespositorio.deleteById(codigo);
	}

	@Override
	public List<Piloto> listar()
	{
		return this.pilotoRespositorio.findAll();
	}

}
