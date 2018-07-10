package br.com.syntax.controledeponto.repositories;

import br.com.syntax.controledeponto.FabricaDeModelsParaTeste;
import br.com.syntax.controledeponto.entities.Empresa;
import br.com.syntax.controledeponto.entities.Funcionario;
import br.com.syntax.controledeponto.entities.Lancamento;
import br.com.syntax.controledeponto.enums.TipoDeControle;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

  @Autowired private LancamentoRespository lancamentoRespository;
  @Autowired private FuncionarioRepository funcionarioRepository;
  @Autowired private EmpresaRepository empresaRepository;

  private Long funcionarioId;

  @Before
  public void setUp() {
    Empresa empresa = FabricaDeModelsParaTeste.criarEmpresa("19327916000189");
    empresa = this.empresaRepository.save(empresa);

    Funcionario funcionario = FabricaDeModelsParaTeste.criarFuncionario("09088585660", "marcos@teste.com.br", empresa);
    funcionario = this.funcionarioRepository.save(funcionario);
    this.funcionarioId = funcionario.getId();

    Lancamento lancamento1 = FabricaDeModelsParaTeste.criarLancamento(funcionario, TipoDeControle.INICIO_TRABALHO);
    Lancamento lancamento2 = FabricaDeModelsParaTeste.criarLancamento(funcionario, TipoDeControle.INICIO_PAUSA);
    this.lancamentoRespository.saveAll( Arrays.asList(lancamento1, lancamento2) );
  }

  @After
  public void tearDown() throws Exception {
    this.empresaRepository.deleteAll();
  }

  @Test
  public void testBuscarPorFuncionarioId() {
    List<Lancamento> lancamentos = this.lancamentoRespository.findByFuncionarioId(this.funcionarioId);
    Assert.assertEquals(2, lancamentos.size());
  }

  @Test
  public void testBuscarPorFuncionarioIdPaginado() {
    PageRequest pageRequest = new PageRequest(0, 100);
    Page<Lancamento> lancamentos = this.lancamentoRespository.findByFuncionarioId(this.funcionarioId, pageRequest);
    Assert.assertEquals(2, lancamentos.getTotalElements());
  }
}
