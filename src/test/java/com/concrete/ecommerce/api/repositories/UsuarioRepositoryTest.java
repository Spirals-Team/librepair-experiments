package com.concrete.ecommerce.api.repositories;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.concrete.ecommerce.api.entities.Usuario;
import com.concrete.ecommerce.api.enums.PerfilEnum;
import com.concrete.ecommerce.api.utils.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {


	@Autowired
	private UsuarioRepository usuarioRepository;

	private static final String EMAIL = "email@email.com";
	private static final Long ID = 1L;

	@Before
	public void setUp() throws Exception {
		this.usuarioRepository.save(obterDadosUsuario());
	}

	@After
	public final void tearDown() {
		this.usuarioRepository.deleteAll();
	}

	@Test
	public void testBuscarUsuarioPorId() {
		Usuario usuario = this.usuarioRepository.findOne(ID);
		assertEquals(EMAIL, usuario.getEmail());
	}

	private Usuario obterDadosUsuario() throws NoSuchAlgorithmException {
		Usuario usuario = new Usuario();
		usuario.setNome("Fulano de Tal");
		usuario.setPerfil(PerfilEnum.ROLE_USUARIO);
		usuario.setSenha(PasswordUtils.gerarBCrypt("123456"));
		usuario.setEmail(EMAIL);
		return usuario;
	}

}
