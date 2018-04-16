package br.com.reclameaqui.teste.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.reclameaqui.teste.dtos.ClienteDTO;
import br.com.reclameaqui.teste.dtos.ReclamantesRequestDTO;
import br.com.reclameaqui.teste.dtos.ReclamantesResponseDTO;
import br.com.reclameaqui.teste.exceptions.ClienteNaoEncontradoException;

public interface ClienteService {
	
	ClienteDTO incluir(ClienteDTO clienteDTO);
	
	ClienteDTO atualizar(String id, ClienteDTO clienteDTODetails) throws ClienteNaoEncontradoException;
	
	List<ClienteDTO> buscarTodos();

	ResponseEntity<ClienteDTO> deletar(String id) throws ClienteNaoEncontradoException;

	ClienteDTO consultar(String id) throws ClienteNaoEncontradoException;

	ReclamantesResponseDTO buscarClientesQueReclamam(ReclamantesRequestDTO consulta);

	ClienteDTO consultarPorCpf(String cpf) throws ClienteNaoEncontradoException;

	ResponseEntity<ClienteDTO> deletarPorCpf(String cpf) throws ClienteNaoEncontradoException;

	ClienteDTO atualizarPorCpf(String cpf, ClienteDTO clienteDTODetails) throws ClienteNaoEncontradoException;

}
