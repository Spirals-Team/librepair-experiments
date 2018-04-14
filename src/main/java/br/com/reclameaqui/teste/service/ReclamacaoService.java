package br.com.reclameaqui.teste.service;

import java.util.List;

import br.com.reclameaqui.teste.dtos.ReclamacaoDTO;
import br.com.reclameaqui.teste.exceptions.CampoNaoEncontradoException;
import br.com.reclameaqui.teste.exceptions.CampoObrigatorioException;
import br.com.reclameaqui.teste.exceptions.ReclamacaoException;

public interface ReclamacaoService {
	
	ReclamacaoDTO atualizar(String id, ReclamacaoDTO blocoDTODetails) throws CampoNaoEncontradoException;
	
	void deletar(String id) throws CampoObrigatorioException;

	List<ReclamacaoDTO> buscarTodos();

	ReclamacaoDTO incluir(ReclamacaoDTO reclamacaoDTO);

	ReclamacaoDTO consultar(String idReclamacao);

}
