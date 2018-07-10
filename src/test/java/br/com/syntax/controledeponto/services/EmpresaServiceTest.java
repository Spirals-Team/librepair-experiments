package br.com.syntax.controledeponto.services;

import br.com.syntax.controledeponto.FabricaDeModelsParaTeste;
import br.com.syntax.controledeponto.entities.Empresa;
import br.com.syntax.controledeponto.repositories.EmpresaRepository;
import org.junit.After;
import org.junit.Assert;
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

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaServiceTest {

  @Autowired private EmpresaService service;
  @MockBean private EmpresaRepository repository;

  private final String CNPJ = "19327916000189";

  @Before
  public void setUp() {
    BDDMockito.given(  this.repository.findByCnpj(Mockito.anyString()) ).willReturn( new Empresa("", CNPJ) );
    BDDMockito.given(  this.repository.save(Mockito.any(Empresa.class)) ).willReturn( new Empresa() );
  }

  @After
  public void tearDown() {

  }

  @Test
  public void testBuscarPorCnpj() {
    Optional<Empresa> empresa = this.service.buscaPorCnpj(CNPJ);
    Assert.assertTrue(empresa.isPresent());
  }

  @Test
  public void testGravar() {
    Empresa empresa = FabricaDeModelsParaTeste.criarEmpresa(CNPJ);
    Optional<Empresa> optionalEmpresa = this.service.salva( new Empresa() );
    Assert.assertNotNull(optionalEmpresa);
  }

}
