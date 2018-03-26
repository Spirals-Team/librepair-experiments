package com.concrete.ecommerce.api.controllers;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.concrete.ecommerce.api.dtos.CadastroUsuarioDto;
import com.concrete.ecommerce.api.entities.Usuario;
import com.concrete.ecommerce.api.response.Response;
import com.concrete.ecommerce.api.services.UsuarioService;

@RestController
@RequestMapping("/api/cadastrar-usuario")
public class CadastroUsuarioController {

	private static final Logger log = LoggerFactory.getLogger(CadastroUsuarioController.class);

	private final UsuarioService usuarioService;

	@Autowired
	public CadastroUsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	/**
	 * Cadastra um usuário no sistema.
	 * 
	 * @param cadastroUsuarioDto
	 * @param result
	 * @return ResponseEntity<Response<CadastroUsuarioDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<CadastroUsuarioDto>> cadastrar(
			@Valid @RequestBody CadastroUsuarioDto cadastroUsuarioDto, BindingResult result)
			throws NoSuchAlgorithmException {
		log.info("Cadastrando Usuario: {}", cadastroUsuarioDto.toString());
		Response<CadastroUsuarioDto> response = new Response<CadastroUsuarioDto>();

		usuarioService.validarDadosExistentes(cadastroUsuarioDto, result);
		Usuario usuario = usuarioService.converterDtoParaUsuario(cadastroUsuarioDto, result);

		if (result.hasErrors()) {
			log.error("Erro validando dados de cadastro Usuario: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.usuarioService.persistir(usuario);

		response.setData(usuarioService.converterUsuarioParaDto(usuario));
		return ResponseEntity.ok(response);
	}

}
