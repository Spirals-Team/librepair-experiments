package com.concrete.ecommerce.api.components;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.concrete.ecommerce.api.dtos.CadastroUsuarioDto;
import com.concrete.ecommerce.api.entities.Usuario;
import com.concrete.ecommerce.api.enums.PerfilEnum;
import com.concrete.ecommerce.api.utils.PasswordUtils;

@Component
public class UsuarioConversorComponent {
	
	public Usuario converterDtoParaUsuario(CadastroUsuarioDto cadastroUsuarioDto, BindingResult result) throws NoSuchAlgorithmException {
		Usuario usuario = new Usuario();
		usuario.setNome(cadastroUsuarioDto.getNome());
		usuario.setEmail(cadastroUsuarioDto.getEmail());
		usuario.setPerfil(PerfilEnum.ROLE_USUARIO);
		usuario.setSenha(PasswordUtils.gerarBCrypt(cadastroUsuarioDto.getSenha()));

		return usuario;
	}
	

	public CadastroUsuarioDto converterUsuarioParaDto(Usuario usuario) {
		CadastroUsuarioDto cadastroUsuarioDto = new CadastroUsuarioDto();
		cadastroUsuarioDto.setId(usuario.getId());
		cadastroUsuarioDto.setNome(usuario.getNome());
		cadastroUsuarioDto.setEmail(usuario.getEmail());

		return cadastroUsuarioDto;
	}
}
