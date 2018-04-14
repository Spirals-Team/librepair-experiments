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
import br.com.reclameaqui.teste.dtos.ReclamacaoDTO;
import br.com.reclameaqui.teste.exceptions.CampoNaoEncontradoException;
import br.com.reclameaqui.teste.exceptions.CampoObrigatorioException;
import br.com.reclameaqui.teste.exceptions.ReclamacaoException;
import br.com.reclameaqui.teste.service.ReclamacaoServiceImpl;

@RestController
@RequestMapping("")
public class ReclamacaoController {

	@Autowired
	private ReclamacaoServiceImpl reclamacaoService;
	private final Logger logger = Logger.getLogger(ReclamacaoServiceImpl.class);

	@GetMapping("/reclamacoes")
	public List<ReclamacaoDTO> buscarTodasReclamacoes() {

		return reclamacaoService.buscarTodos();
	}

	@GetMapping("/reclamacao/{idReclamacao}")
	public ResponseEntity<ReclamacaoDTO> consultar(@PathVariable(value = "idReclamacao") String idReclamacao)
			throws ReclamacaoException, CampoNaoEncontradoException {
		logger.info("Rest consultar reclamacao");
		ReclamacaoDTO reclamacaoDTO = reclamacaoService.consultar(idReclamacao);

		return new ResponseEntity<ReclamacaoDTO>(reclamacaoDTO, HttpStatus.OK);
	}

	@PostMapping("/reclamacao")
	public ResponseEntity<ReclamacaoDTO> incluir(@RequestBody ReclamacaoDTO reclamacaoDTO)
			throws ReclamacaoException, CampoObrigatorioException {
		logger.info("Rest incluir reclamacao");

		ReclamacaoDTO responseReclamacaoDTO = reclamacaoService.incluir(reclamacaoDTO);
		return new ResponseEntity<ReclamacaoDTO>(responseReclamacaoDTO, HttpStatus.OK);
	}

	@PutMapping("/reclamacao/{id}")
	public ResponseEntity<ReclamacaoDTO> atualizar(@PathVariable(value = "id") String id,
			@RequestBody @Valid ReclamacaoDTO reclamacaoDTODetails) {
		logger.info("Rest atualizar reclamacao");

		ReclamacaoDTO reclamacaoDTO;
		try {
			reclamacaoDTO = reclamacaoService.atualizar(id, reclamacaoDTODetails);
		} catch (CampoNaoEncontradoException e) {
			return ResponseEntity.notFound().build();
		}

		return new ResponseEntity<ReclamacaoDTO>(reclamacaoDTO, HttpStatus.OK);
	}

	@DeleteMapping("/reclamacao/{id}")
	public ResponseEntity<ReclamacaoDTO> deletar(@PathVariable(value = "id") String id)
			throws ReclamacaoException, CampoObrigatorioException {
		logger.info("Rest deletar reclamacao");

		reclamacaoService.deletar(id);
		return new ResponseEntity<ReclamacaoDTO>(HttpStatus.OK);
	}

}