package com.concrete.ecommerce.api.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;

import com.concrete.ecommerce.api.dtos.CadastroUsuarioDto;
import com.concrete.ecommerce.api.entities.Usuario;
import com.concrete.ecommerce.api.services.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CadastroUsuarioControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UsuarioService usuarioService;

	private static final String URL_BASE = "/api/cadastrar-usuario/";
	private static final Long ID_USUARIO = 1L;
	private static final String SENHA_VALIDA = "teste1234";
	private static final String NOME_VALIDO = "Luiz Evangelista";
	private static final String EMAIL_VALIDO = "teste@teste.com.br";

	@Test
	@WithMockUser
	public void testCadastrarUsuario() throws Exception {
		Usuario usuario = obterDadosUsuario();
		BDDMockito.given(this.usuarioService.converterDtoParaUsuario(Mockito.any(CadastroUsuarioDto.class),
				Mockito.any(BindingResult.class))).willReturn(usuario);
		BDDMockito.given(this.usuarioService.persistir(Mockito.any(Usuario.class))).willReturn(usuario);

		mvc.perform(MockMvcRequestBuilders.post(URL_BASE).content(this.obterJsonRequisicaoPost())
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data.id").value(ID_USUARIO))
				.andExpect(jsonPath("$.data.nome").value(NOME_VALIDO))
				.andExpect(jsonPath("$.data.email").value(EMAIL_VALIDO))
				.andExpect(jsonPath("$.data.senha").value(SENHA_VALIDA))
				.andExpect(jsonPath("$.errors").isEmpty());
	}


	private String obterJsonRequisicaoPost() throws JsonProcessingException {
		CadastroUsuarioDto cadastroUsuarioDto = new CadastroUsuarioDto();
		cadastroUsuarioDto.setId(null);
		cadastroUsuarioDto.setEmail(EMAIL_VALIDO);
		cadastroUsuarioDto.setNome(NOME_VALIDO);
		cadastroUsuarioDto.setSenha(SENHA_VALIDA);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(cadastroUsuarioDto);
	}

	private Usuario obterDadosUsuario() {
		Usuario usuario = new Usuario();
		usuario.setId(ID_USUARIO);
		usuario.setNome(NOME_VALIDO);
		usuario.setEmail(EMAIL_VALIDO);
		usuario.setSenha(SENHA_VALIDA);
		return usuario;
	}
}
