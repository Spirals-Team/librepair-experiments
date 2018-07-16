package br.com.rodolfo.pontointeligente.api.repositories;

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

import br.com.rodolfo.pontointeligente.api.entities.Empresa;
import br.com.rodolfo.pontointeligente.api.entities.Funcionario;
import br.com.rodolfo.pontointeligente.api.entities.Lancamento;
import br.com.rodolfo.pontointeligente.api.enums.PerfilEnum;
import br.com.rodolfo.pontointeligente.api.enums.TipoEnum;
import br.com.rodolfo.pontointeligente.api.utils.Passwordutils;

@RunWith(SpringRunner.class)
@SpringBootTest
//Faz com que o teste seja executado utilizando o profile de test que acrregará os dados do 'application-test.properties'
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    private Long funcionarioId;

    @Before
    public void setUp() {

        Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());

        Funcionario funcionario = this.funcionarioRepository.save(obterDadosFuncionario(empresa));
        this.funcionarioId = funcionario.getId();

        this.lancamentoRepository.save(obterDadosLancamentos(funcionario));
        this.lancamentoRepository.save(obterDadosLancamentos(funcionario));
    }


	@After
    public void tearDown() {

        this.empresaRepository.deleteAll();
    }

    @Test
    public void testBuscarLancamentosPorFuncionarioId() {
        
        List<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId);

        assertEquals(2, lancamentos.size(), 0.00001);
    }

    @Test
    public void testBuscarLancamentosPorFuncionarioIdPaginado() {
        
        PageRequest page = new PageRequest(0, 10);
        Page<Lancamento> lancamentos = this.lancamentoRepository.findByFuncionarioId(funcionarioId, page);

        assertEquals(2, lancamentos.getTotalElements(), 0.00001);
    }


    private Lancamento obterDadosLancamentos(Funcionario funcionario) {
        
        Lancamento lancamento = new Lancamento();

        lancamento.setData(new Date());
        lancamento.setDescricao("Saida para o almoco");
        lancamento.setLocalizacao("Rua aguas");
        lancamento.setTipo(TipoEnum.INICIO_ALMOCO);
        lancamento.setFuncionario(funcionario);
        
        return lancamento;
	}


    private Funcionario obterDadosFuncionario(Empresa empresa) {

        Funcionario funcionario = new Funcionario();
        Float d = Float.valueOf("10");

        funcionario.setNome("Fulano");
        funcionario.setQtdHorasAlmoco(d);
        funcionario.setQtdHorasTrabalhoDia(d);
        funcionario.setValorHora(new BigDecimal(d));
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
        funcionario.setSenha(Passwordutils.gerarBCrypt("123456"));
        funcionario.setCpf("105647874");
        funcionario.setEmail("teste@teste.com");
        funcionario.setEmpresa(empresa);

        return funcionario;
    }

    private Empresa obterDadosEmpresa() {

        Empresa empresa = new Empresa();

        empresa.setRazaoSocial("Empresa de exemplo");
        empresa.setCnpj("51463645000100");

        return empresa;
    }
    
}