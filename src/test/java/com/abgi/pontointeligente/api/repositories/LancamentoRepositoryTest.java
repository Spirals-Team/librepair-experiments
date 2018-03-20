package com.abgi.pontointeligente.api.repositories;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
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

import com.abgi.pontointeligente.api.entities.Empresa;
import com.abgi.pontointeligente.api.entities.Funcionario;
import com.abgi.pontointeligente.api.entities.Lancamento;
import com.abgi.pontointeligente.api.enums.PerfilEnum;
import com.abgi.pontointeligente.api.enums.TipoEnum;
import com.abgi.pontointeligente.api.utils.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {
	
	private static final String EMAIL = "abimaelr.sergio@gmail.com";
	private static final String CPF = "16591578308";
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	private Long funcionarioId;

	@Before
	public void setUp() throws Exception {
		Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
		
		Funcionario funcionario = this.funcionarioRepository.save(obterDadosFuncionario(empresa));
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
	public void testBuscarLancamentosPorFuncionarioIdPaginado() {
		PageRequest page = new PageRequest(0, 10);
		Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId, page);
		
		assertEquals(2, lancamentos.getTotalElements());
	}
	
	private Lancamento obterDadosLancamentos(Funcionario funcionario) {
		Lancamento lancamento = new Lancamento();
		lancamento.setData(new Date());
		lancamento.setTipo(TipoEnum.INICIO_ALMOCO);
		lancamento.setFuncionario(funcionario);
		lancamento.setDescricao("Inicio Almoço");
		lancamento.setLocalizacao("São Paulo");
		return lancamento;
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
		empresa.setRazaoSocial("Empresa de exemplo");
		empresa.setCnpj("51463645000100");
		return empresa;
	}

}
