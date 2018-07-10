package br.com.syntax.controledeponto.services;


import br.com.syntax.controledeponto.entities.Funcionario;
import br.com.syntax.controledeponto.entities.Lancamento;
import br.com.syntax.controledeponto.repositories.LancamentoRespository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoServiceTest {

  @Autowired private LancamentoService service;
  @MockBean private LancamentoRespository respository;

  @Before
  public void setUp() {
    BDDMockito.given( this.respository.findByFuncionarioId(Mockito.anyLong()) ).willReturn( new ArrayList<>());
    BDDMockito.given( this.respository.findByFuncionarioId(Mockito.anyLong(), Mockito.any(PageRequest.class)) ).willReturn( new PageImpl<Lancamento>( new ArrayList<>()));
    BDDMockito.given( this.respository.getOne(Mockito.anyLong())).willReturn( new Lancamento() );
    BDDMockito.given( this.respository.save(Mockito.any(Lancamento.class))).willReturn( new Lancamento() );
  }

  @Test
  public void testBuscaPorFuncionario() {
    List<Lancamento> lancamentos = this.service.getByFuncionario( new Funcionario() );
    Assert.assertNotNull(lancamentos);
  }

//  @Test
//  public void testBuscaPorFuncionarioComPaginacao() {
//    Page<Lancamento> lancamentos = this.service.getByFuncionario( new Funcionario(), new PageRequest(0, 100) );
//    Assert.assertNotNull(lancamentos);
//  }

  @Test
  public void testBuscaPeloId() {
    Optional<Lancamento> lancamento = this.service.getById(1L);
    Assert.assertTrue(lancamento.isPresent());
  }

  @Test
  public void testGravar() {
    Lancamento lancamento = this.service.grava( new Lancamento());
    Assert.assertNotNull(lancamento);
  }

}
