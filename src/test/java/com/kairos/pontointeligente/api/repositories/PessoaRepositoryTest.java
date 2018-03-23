package com.kairos.pontointeligente.api.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.security.NoSuchAlgorithmException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.kairos.pontointeligente.api.entities.Pessoa;
import com.kairos.pontointeligente.api.utils.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PessoaRepositoryTest {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	private static final String EMAIL = "email@email.com";
	private static final String CPF = "24291173474";

	@Before
	public void setUp() throws Exception {
		this.pessoaRepository.save(obterDadosPessoa());
	}

	@After
	public void tearDown() throws Exception {
		this.pessoaRepository.deleteAll();
	}

	@Test
	public void testBuscarPessoaPorEmail() {
		Pessoa pessoa = this.pessoaRepository.findByEmail(EMAIL);

		assertEquals(EMAIL, pessoa.getEmail());
	}

	@Test
	public void testBuscarPessoaPorCpf() {
		Pessoa pessoa = this.pessoaRepository.findByCpf(CPF);

		assertEquals(CPF, pessoa.getCpf());
	}

	@Test
	public void testBuscarPessoaPorEmailECpf() {
		Pessoa pessoa = this.pessoaRepository.findByCpfOrEmail(CPF, EMAIL);

		assertNotNull(pessoa);
	}

	@Test
	public void testBuscarPessoaPorEmailOuCpfParaEmailInvalido() {
		Pessoa pessoa = this.pessoaRepository.findByCpfOrEmail(CPF, "email@invalido.com");

		assertNotNull(pessoa);
	}

	@Test
	public void testBuscarPessoaPorEmailECpfParaCpfInvalido() {
		Pessoa pessoa = this.pessoaRepository.findByCpfOrEmail("12345678901", EMAIL);

		assertNotNull(pessoa);
	}

	private Pessoa obterDadosPessoa() throws NoSuchAlgorithmException {
		Pessoa pessoa = new Pessoa();
		pessoa.setNome("Fulano de Tal");
		pessoa.setSenha(PasswordUtils.gerarBCrypt("123456"));
		pessoa.setCpf(CPF);
		pessoa.setEmail(EMAIL);
		
		return pessoa;
	}
}
