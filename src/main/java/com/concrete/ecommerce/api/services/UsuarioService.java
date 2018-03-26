package com.concrete.ecommerce.api.services;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.validation.BindingResult;

import com.concrete.ecommerce.api.dtos.CadastroUsuarioDto;
import com.concrete.ecommerce.api.entities.Usuario;

public interface UsuarioService {

	/**
	 * Persiste um usuário na base de dados.
	 * 
	 * @param usuario
	 * @return Usuario
	 */
	Usuario persistir(Usuario usuario);

	/**
	 * Busca e retorna um usuário dado um email.
	 * 
	 * @param email
	 * @return Optional<Usuario>
	 */
	Optional<Usuario> buscarPorEmail(String email);

	/**
	 * Busca e retorna um usuário por ID.
	 * 
	 * @param id
	 * @return Optional<Usuario>
	 */
	Optional<Usuario> buscarPorId(Long id);

	/**
	 * Converte DTO para Entidade Usuario.
	 * 
	 * @param cadastroUsuarioDto
	 * @param result
	 * @return Usuario
	 */
	Usuario converterDtoParaUsuario(CadastroUsuarioDto cadastroUsuarioDto, BindingResult result)
			throws NoSuchAlgorithmException;

	/**
	 * Popula o DTO de cadastro com os dados do usuario.
	 * 
	 * @param usuario
	 * @return cadastroUsuarioDto
	 */
	CadastroUsuarioDto converterUsuarioParaDto(Usuario usuario);

	/**
	 * Verifica se o usuário não existe na base de dados.
	 * 
	 * @param cadastroUsuarioDto
	 * @param result
	 * @return 
	 */
	void validarDadosExistentes(CadastroUsuarioDto cadastroUsuarioDto, BindingResult result);

}
