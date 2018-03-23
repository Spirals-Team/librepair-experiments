package com.kairos.pontointeligente.api.repositories;

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

import com.kairos.pontointeligente.api.entities.Empresa;
import com.kairos.pontointeligente.api.entities.Funcionario;
import com.kairos.pontointeligente.api.entities.Pessoa;
import com.kairos.pontointeligente.api.enums.PerfilEnum;
import com.kairos.pontointeligente.api.utils.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {

	private static final String NOME_PESSOA = "Fulano de Tal";

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	private String nomeFuncionario;
	
	private Pessoa pessoaPesquisa;

	private static final String EMAIL = "email@email.com";
	private static final String CPF = "24291173474";

	@Before
	public void setUp() throws Exception {
		Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());

		Pessoa pessoa = this.pessoaRepository.save(obterDadosPessoa());
		pessoaPesquisa = pessoa;

		this.funcionarioRepository.save(obterDadosFuncionario(empresa, pessoa));
	}

	@After
	public final void tearDown() {
		this.empresaRepository.deleteAll();
		this.pessoaRepository.deleteAll();
		
	}

	@Test
	public void testBuscarFuncionarioPorCnpj() throws NoSuchAlgorithmException {

		Funcionario funcionario = this.funcionarioRepository.findByPessoa(pessoaPesquisa);
		nomeFuncionario = funcionario.getPessoa().getNome();
		
		assertEquals(NOME_PESSOA, nomeFuncionario);
	}

	private Funcionario obterDadosFuncionario(Empresa empresa, Pessoa pessoa) throws NoSuchAlgorithmException {
		Funcionario funcionario = new Funcionario();
		funcionario.setPessoa(pessoa);
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setEmpresa(empresa);
		return funcionario;
	}

	private Empresa obterDadosEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Empresa de exemplo");
		empresa.setCnpj("51463645000100");
		return empresa;
	}

	private Pessoa obterDadosPessoa() throws NoSuchAlgorithmException {
		Pessoa pessoa = new Pessoa();
		pessoa.setNome(NOME_PESSOA);
		pessoa.setSenha(PasswordUtils.gerarBCrypt("123456"));
		pessoa.setCpf(CPF);
		pessoa.setEmail(EMAIL);

		return pessoa;
	}

}
