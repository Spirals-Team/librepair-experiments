package com.concrete.ecommerce.api.services.impl;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.concrete.ecommerce.api.components.UsuarioConversorComponent;
import com.concrete.ecommerce.api.dtos.CadastroUsuarioDto;
import com.concrete.ecommerce.api.entities.Usuario;
import com.concrete.ecommerce.api.repositories.UsuarioRepository;
import com.concrete.ecommerce.api.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private static final Logger log = LoggerFactory.getLogger(UsuarioServiceImpl.class);

	private final UsuarioRepository usuarioRepository;
	private final UsuarioConversorComponent usuarioComponent;

	@Autowired
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository, UsuarioConversorComponent usuarioComponent) {
		this.usuarioRepository = usuarioRepository;
		this.usuarioComponent = usuarioComponent;
	}

	public Usuario persistir(Usuario usuario) {
		log.info("Persistindo usuário: {}", usuario);
		return this.usuarioRepository.save(usuario);
	}

	public Optional<Usuario> buscarPorEmail(String email) {
		log.info("Buscando usuário pelo email {}", email);
		return Optional.ofNullable(this.usuarioRepository.findByEmail(email));
	}

	public Optional<Usuario> buscarPorId(Long id) {
		log.info("Buscando usuário pelo IDl {}", id);
		return Optional.ofNullable(this.usuarioRepository.findOne(id));
	}

	public Usuario converterDtoParaUsuario(CadastroUsuarioDto cadastroUsuarioDto, BindingResult result)
			throws NoSuchAlgorithmException {
		return usuarioComponent.converterDtoParaUsuario(cadastroUsuarioDto, result);
	}

	public CadastroUsuarioDto converterUsuarioParaDto(Usuario usuario) {
		return usuarioComponent.converterUsuarioParaDto(usuario);
	}

	public void validarDadosExistentes(CadastroUsuarioDto cadastroUsuarioDto, BindingResult result) {
		this.buscarPorEmail(cadastroUsuarioDto.getEmail())
				.ifPresent(func -> result.addError(new ObjectError("usuario", "Email já existente.")));
	}
}
