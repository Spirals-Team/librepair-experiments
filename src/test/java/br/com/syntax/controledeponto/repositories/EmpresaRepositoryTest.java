package br.com.syntax.controledeponto.repositories;


import br.com.syntax.controledeponto.FabricaDeModelsParaTeste;
import br.com.syntax.controledeponto.entities.Empresa;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaRepositoryTest {

  @Autowired
  private EmpresaRepository empresaRepository;

  private final String CNPJ = "19327916000189";

  @Before
  public void setUp() {
    Empresa empresa = FabricaDeModelsParaTeste.criarEmpresa(CNPJ);
    this.empresaRepository.save(empresa);
  }

  @After
  public void tearDown() throws Exception {
    this.empresaRepository.deleteAll();
  }

  @Test
  public void testBuscarPorCnpj() {
    Empresa empresa = this.empresaRepository.findByCnpj(CNPJ);
    Assert.assertEquals(CNPJ, empresa.getCnpj());
  }


}
