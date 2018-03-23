package com.kairos.pontointeligente.api.repositories;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.kairos.pontointeligente.api.entities.Empresa;
import com.kairos.pontointeligente.api.entities.Funcionario;
import com.kairos.pontointeligente.api.entities.Lancamento;
import com.kairos.pontointeligente.api.entities.Pessoa;
import com.kairos.pontointeligente.api.enums.PerfilEnum;
import com.kairos.pontointeligente.api.enums.TipoEnum;
import com.kairos.pontointeligente.api.utils.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

	@Autowired
	private LancamentoRepository lancamentoRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	private Long funcionarioId;

	private static final String EMAIL = "email@email.com";

	private static final String CPF = "24291173474";

	@Before
	public void setUp() throws Exception {
		Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());

		Pessoa pessoa = this.pessoaRepository.save(obterDadosPessoa());

		Funcionario funcionario = this.funcionarioRepository.save(obterDadosFuncionario(empresa, pessoa));

		this.funcionarioId = funcionario.getId();

		this.lancamentoRepository.save(obterDadosLancamentos(funcionario));
		this.lancamentoRepository.save(obterDadosLancamentos(funcionario));
	}

	@After
	public void tearDown() throws Exception {
		this.empresaRepository.deleteAll();
	}

	@Test
	public void testBuscarLancamentosPorFuncionarioId() {
		List<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId);

		assertEquals(2, lancamentos.size());
	}

	@Test
	@SuppressWarnings("deprecation")
	public void testBuscarLancamentosPorFuncionarioIdPaginado() {
		PageRequest page = new PageRequest(0, 10);
		Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId, page);

		assertEquals(2, lancamentos.getTotalElements());
	}

	private Lancamento obterDadosLancamentos(Funcionario funcionario) {
		Lancamento lancameto = new Lancamento();
		lancameto.setData(new Date());
		lancameto.setTipo(TipoEnum.INICIO_ALMOCO);
		lancameto.setFuncionario(funcionario);
		return lancameto;
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
		pessoa.setNome("Fulano de Tal");
		pessoa.setSenha(PasswordUtils.gerarBCrypt("123456"));
		pessoa.setCpf(CPF);
		pessoa.setEmail(EMAIL);

		return pessoa;
	}

}
