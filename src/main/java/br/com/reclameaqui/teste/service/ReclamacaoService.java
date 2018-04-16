package br.com.reclameaqui.teste.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.reclameaqui.teste.dtos.ReclamacaoDTO;
import br.com.reclameaqui.teste.exceptions.ReclamacaoNaoEncontradaException;

public interface ReclamacaoService {
	
	ReclamacaoDTO atualizar(String id, ReclamacaoDTO blocoDTODetails) throws ReclamacaoNaoEncontradaException;
	
	ResponseEntity<ReclamacaoDTO> deletar(String id) throws ReclamacaoNaoEncontradaException;

	List<ReclamacaoDTO> buscarTodos();

	ReclamacaoDTO incluir(ReclamacaoDTO reclamacaoDTO);

	ReclamacaoDTO consultar(String idReclamacao) throws ReclamacaoNaoEncontradaException;

}

