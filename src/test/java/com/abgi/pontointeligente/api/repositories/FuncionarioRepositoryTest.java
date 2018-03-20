package com.abgi.pontointeligente.api.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.abgi.pontointeligente.api.entities.Empresa;
import com.abgi.pontointeligente.api.entities.Funcionario;
import com.abgi.pontointeligente.api.enums.PerfilEnum;
import com.abgi.pontointeligente.api.utils.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private EmpresaRepository empresaRepository;

	private static final String CNPJ = "44306987657";
	private static final String EMAIL = "abimaelr.sergio@gmail.com";
	private static final String CPF = "16591578308";

	@Before
	public void setUp() throws Exception {
		Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
		this.funcionarioRepository.save(obterDadosFuncionario(empresa));
	}

	@After
	public void tearDown() throws Exception {
		this.empresaRepository.deleteAll();
	}

	@Test
	public void testBuscarFuncionarioPorEmail() {
		Funcionario funcionario = this.funcionarioRepository.findByEmail(EMAIL);

		assertEquals(EMAIL, funcionario.getEmail());
	}

	@Test
	public void testBuscarFuncionarioPorCPF() {
		Funcionario funcionario = this.funcionarioRepository.findByCpf(CPF);

		assertEquals(CPF, funcionario.getCpf());
	}

	@Test
	public void testBuscarFuncionarioPorEmailECpf() {
		Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, EMAIL);

		assertNotNull(funcionario);
	}

	@Test
	public void testBuscarFuncionarioPorEmailOuCpfParaEmailInvalido() {
		Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, "email@invalido.com");

		assertNotNull(funcionario);
	}	

	@Test
	public void testBuscarFuncionarioPorEmailOuCpfParaCpfInvalido() {
		Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail("90567889", EMAIL);

		assertNotNull(funcionario);
	}		
	
	private Funcionario obterDadosFuncionario(Empresa empresa) {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Abimael");
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setSenha(PasswordUtils.gerarBCrypt("123mudar"));
		funcionario.setCpf(CPF);
		funcionario.setEmail(EMAIL);
		funcionario.setEmpresa(empresa);
		funcionario.setQtdHorasAlmoco(1f);
		funcionario.setQtdHorasTrabalhoDia(8f);
		funcionario.setValorHora(BigDecimal.valueOf(45d));
		return funcionario;
	}

	private Empresa obterDadosEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Abgi");
		empresa.setCnpj(CNPJ);
		return empresa;
	}
}
