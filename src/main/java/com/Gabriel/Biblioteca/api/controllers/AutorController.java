package com.Gabriel.Biblioteca.api.controllers;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Gabriel.Biblioteca.api.dtos.AutorDTO;
import com.Gabriel.Biblioteca.api.entities.Autor;
import com.Gabriel.Biblioteca.api.response.Response;
import com.Gabriel.Biblioteca.api.services.AutorService;

@RestController
@RequestMapping("/api/autor")
public class AutorController {

	private static final Logger log = LoggerFactory.getLogger(AutorController.class);

	@Autowired
	private AutorService autorService;

	public AutorController() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Consulta autor por código
	 * 
	 * @param codigo
	 * @return
	 */
	@GetMapping(value = "/{codigo}")
	public String consulta(@PathVariable("codigo") String codigo) {
		return "Código apresentado: " + codigo;
	}

	@PostMapping
	public ResponseEntity<Response<AutorDTO>> cadatsrar(@Valid @RequestBody AutorDTO autorDTO, BindingResult result)
			throws NoSuchAlgorithmException {
		log.info("Cadastrando autor {}", autorDTO.toString());

		Response<AutorDTO> response = new Response<AutorDTO>();
		validaSeAutorExiste(autorDTO, result);
		Autor autor = this.converteDTOParaAutor(autorDTO);

		if (result.hasErrors()) {
			log.error("Erro au validar informalções: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.autorService.persistir(autor);
		response.setData(this.converteAutorParaDTO(autor));
		return ResponseEntity.ok(response);
	}

	private Autor converteDTOParaAutor(AutorDTO autorDTO) {
		Autor autor = new Autor();
		autor.setCodigo(autorDTO.getCodigo());
		autor.setNome(autorDTO.getNome());
		autor.setSobrenome(autorDTO.getSobrenome());
		return autor;
	}

	private AutorDTO converteAutorParaDTO(Autor autor) {
		AutorDTO autorDTO = new AutorDTO();
		autorDTO.setCodigo(autor.getCodigo());
		autorDTO.setNome(autor.getNome());
		autorDTO.setSobrenome(autor.getSobrenome());
		autorDTO.setId(autor.getId());
		return autorDTO;
	}

	private void validaSeAutorExiste(AutorDTO autorDTO, BindingResult result) {
		this.autorService.buscaAutorPorCodigo(autorDTO.getCodigo())
				.ifPresent(aut -> result.addError(new ObjectError("autor", "Autor já existe")));
	}
}
