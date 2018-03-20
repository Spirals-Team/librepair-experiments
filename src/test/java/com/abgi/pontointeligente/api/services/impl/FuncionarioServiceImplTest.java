package com.abgi.pontointeligente.api.services.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.abgi.pontointeligente.api.entities.Funcionario;
import com.abgi.pontointeligente.api.repositories.FuncionarioRepository;
import com.abgi.pontointeligente.api.services.FuncionarioService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioServiceImplTest {

	@MockBean
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private FuncionarioService funcionarioService;

	@Before
	public void setUp() throws Exception {
		BDDMockito.given(this.funcionarioRepository.save(Mockito.any(Funcionario.class))).willReturn(new Funcionario());
		BDDMockito.given(this.funcionarioRepository.getOne(Mockito.anyLong())).willReturn(new Funcionario());
		BDDMockito.given(this.funcionarioRepository.findByEmail(Mockito.anyString())).willReturn(new Funcionario());
		BDDMockito.given(this.funcionarioRepository.findByCpf(Mockito.anyString())).willReturn(new Funcionario());
	}

	@Test
	public void testPersistir() {
		Funcionario funcionario = this.funcionarioService.persistir(new Funcionario());
		assertNotNull(funcionario);
	}

	@Test
	public void testBuscarPorCpf() {
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorCpf("45678956754");
		assertTrue(funcionario.isPresent());
	}

	@Test
	public void testBuscarPorEmail() {
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorEmail("email@email.com");
		assertTrue(funcionario.isPresent());
	}

	@Test
	public void testBuscarPorId() {
		Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(1L);
		assertTrue(funcionario.isPresent());
	}

}
