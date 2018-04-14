package br.com.reclameaqui.teste.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.reclameaqui.teste.dtos.ClienteDTO;
import br.com.reclameaqui.teste.dtos.ReclamantesRequestDTO;
import br.com.reclameaqui.teste.dtos.ReclamantesResponseDTO;
import br.com.reclameaqui.teste.exceptions.ClienteNaoEncontradoException;
import br.com.reclameaqui.teste.service.ClienteService;
import br.com.reclameaqui.teste.service.ClienteServiceImpl;

@RestController
@RequestMapping("")
public class ClienteController {

	private ClienteService clienteService;

	@Autowired
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	private final Logger logger = Logger.getLogger(ClienteServiceImpl.class);

	@GetMapping()
	@RequestMapping("/clientes")
	public ResponseEntity<List<ClienteDTO>> buscarTodasClientes() {

		List<ClienteDTO> listClientes = clienteService.buscarTodos();

		return new ResponseEntity<List<ClienteDTO>>(listClientes, HttpStatus.OK);
	}

	@GetMapping()
	@RequestMapping("/teste")
	public String teste() {
		return "teste ok";
	}

	@GetMapping("/cliente/{id}")
	public ResponseEntity<ClienteDTO> consultar(@PathVariable(value = "id") String id) {
		logger.info("Rest consultar cliente");

		ClienteDTO clienteDTO;
		try {
			clienteDTO = clienteService.consultar(id);
		} catch (ClienteNaoEncontradoException e) {
			return ResponseEntity.notFound().build();
		}

		return new ResponseEntity<ClienteDTO>(clienteDTO, HttpStatus.OK);
	}

	@GetMapping("/cliente/cpf/{cpf}")
	public ResponseEntity<ClienteDTO> consultarPorCpf(@PathVariable(value = "cpf") String cpf) {
		logger.info("Rest consultar cliente");
		ClienteDTO clienteDTO;
		try {
			clienteDTO = clienteService.consultarPorCpf(cpf);
		} catch (ClienteNaoEncontradoException e) {
			return ResponseEntity.notFound().build();
		}

		return new ResponseEntity<ClienteDTO>(clienteDTO, HttpStatus.OK);
	}

	@PostMapping("/cliente")
	public ResponseEntity<ClienteDTO> incluir(@RequestBody @Valid ClienteDTO clienteDTO) {
		logger.info("Rest incluir cliente");

		ClienteDTO responseClienteDTO = clienteService.incluir(clienteDTO);
		return new ResponseEntity<ClienteDTO>(responseClienteDTO, HttpStatus.CREATED);
	}

	@PutMapping("/cliente/{id}")
	public ResponseEntity<ClienteDTO> atualizar(@PathVariable(value = "id") String id,
			@RequestBody @Valid ClienteDTO clienteDTODetails) {
		logger.info("Rest atualizar cliente");

		ClienteDTO clienteDTO;

		try {
			clienteDTO = clienteService.atualizar(id, clienteDTODetails);
		} catch (ClienteNaoEncontradoException e) {
			return ResponseEntity.notFound().build();
		}

		return new ResponseEntity<ClienteDTO>(clienteDTO, HttpStatus.NO_CONTENT);
	}

	@PutMapping("/cliente/cpf/{cpf}")
	public ResponseEntity<ClienteDTO> atualizarPorCpf(@PathVariable(value = "cpf") String cpf,
			@RequestBody @Valid ClienteDTO clienteDTODetails) {
		logger.info("Rest atualizar cliente");

		ClienteDTO clienteDTO;

		try {
			clienteDTO = clienteService.atualizarPorCpf(cpf, clienteDTODetails);
		} catch (ClienteNaoEncontradoException e) {
			return ResponseEntity.notFound().build();
		}

		return new ResponseEntity<ClienteDTO>(clienteDTO, HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/cliente/{id}")
	public ResponseEntity<ClienteDTO> deletar(@PathVariable(value = "id") String id) {
		logger.info("Rest deletar cliente");

		ResponseEntity<ClienteDTO> response;
		try {
			response = clienteService.deletar(id);
		} catch (ClienteNaoEncontradoException e) {
			return ResponseEntity.notFound().build();
		}

		return response;
	}

	@DeleteMapping("/cliente/cpf/{cpf}")
	public ResponseEntity<ClienteDTO> deletarPorCpf(@PathVariable(value = "cpf") String cpf) {
		logger.info("Rest deletar cliente");

		ResponseEntity<ClienteDTO> response;
		try {
			response = clienteService.deletarPorCpf(cpf);
		} catch (ClienteNaoEncontradoException e) {
			return ResponseEntity.notFound().build();
		}

		return response;
	}

	@PostMapping("/clientes/consultar/reclamantes")
	public ResponseEntity<ReclamantesResponseDTO> buscarClientesQueReclamam(
			@RequestBody @Valid ReclamantesRequestDTO consultaReclamacaoRequestDTO) {

		ReclamantesResponseDTO reclamantes = clienteService.buscarClientesQueReclamam(consultaReclamacaoRequestDTO);
		
		return new ResponseEntity<ReclamantesResponseDTO>(reclamantes, HttpStatus.OK);
	}

}