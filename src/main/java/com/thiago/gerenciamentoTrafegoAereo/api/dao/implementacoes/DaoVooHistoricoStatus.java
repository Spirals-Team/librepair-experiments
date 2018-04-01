package com.thiago.gerenciamentoTrafegoAereo.api.dao.implementacoes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiago.gerenciamentoTrafegoAereo.api.dao.interfaces.DaoVooHistoricoStatusInterface;
import com.thiago.gerenciamentoTrafegoAereo.api.entidades.VooHistoricoStatus;
import com.thiago.gerenciamentoTrafegoAereo.api.repositorios.VooHistoricoStatusRepositorio;

@Service
public class DaoVooHistoricoStatus implements DaoVooHistoricoStatusInterface
{
	@Autowired
	private VooHistoricoStatusRepositorio vooHistoricoRepositorio;

	@Override
	public Optional<VooHistoricoStatus> buscarPorId(Long id)
	{
		return Optional.ofNullable(vooHistoricoRepositorio.findByCodigo(id));
	}

	@Override
	public Optional<VooHistoricoStatus> inserir(VooHistoricoStatus entidade)
	{
		return Optional.ofNullable(vooHistoricoRepositorio.save(entidade));
	}

	@Override
	public void deletar(Long id)
	{		
		vooHistoricoRepositorio.deleteById(id);
	}

	@Override
	public List<VooHistoricoStatus> listar()
	{
		return vooHistoricoRepositorio.findAll();
	}
}
