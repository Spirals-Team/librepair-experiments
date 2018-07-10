package br.com.syntax.controledeponto.services;

import br.com.syntax.controledeponto.entities.Funcionario;
import br.com.syntax.controledeponto.repositories.FuncionarioRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioServiceTest {

  @Autowired private FuncionarioService service;
  @MockBean private FuncionarioRepository repository;

  private final String CPF = "09088585660";
  private final String EMAIL = "marcos@gmail.com";

  @Before
  public void setUp() {
    BDDMockito.given(  this.repository.getByCpf(Mockito.anyString()) ).willReturn( new Funcionario() );
    BDDMockito.given(  this.repository.getByEmail(Mockito.anyString()) ).willReturn( new Funcionario() );
    BDDMockito.given(  this.repository.getOne(Mockito.anyLong()) ).willReturn( new Funcionario() );
    BDDMockito.given(  this.repository.save(Mockito.any(Funcionario.class)) ).willReturn( new Funcionario() );
  }

  @After
  public void tearDown() {

  }

  @Test
  public void testBuscaPorCpf() {
    Optional<Funcionario> funcionarioOptional = this.service.buscaPorCpf(CPF);
    Assert.assertTrue(funcionarioOptional.isPresent());
  }

  @Test
  public void testBuscaPorEmail() {
    Optional<Funcionario> funcionarioOptional = this.service.buscaPorEmail(EMAIL);
    Assert.assertTrue(funcionarioOptional.isPresent());
  }

  @Test
  public void testBuscaPorId() {
    Optional<Funcionario> funcionarioOptional = this.service.buscaPorId(1L);
    Assert.assertTrue(funcionarioOptional.isPresent());
  }

  @Test
  public void testGravar() {
    Funcionario funcionario = this.service.salva( new Funcionario() );
    Assert.assertNotNull(funcionario);
  }

}
