package br.com.syntax.controledeponto.repositories;

import br.com.syntax.controledeponto.FabricaDeModelsParaTeste;
import br.com.syntax.controledeponto.entities.Empresa;
import br.com.syntax.controledeponto.entities.Funcionario;
import br.com.syntax.controledeponto.enums.Perfil;
import br.com.syntax.controledeponto.utils.PasswordUtils;
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
public class FuncionarioRepositoryTest {

  @Autowired private FuncionarioRepository funcionarioRepository;
  @Autowired private EmpresaRepository empresaRepository;


  private final String EMAIL = "marcospaulo.diasteixeira@gmail.com";
  private final String CPF = "0908858560";

  @Before
  public void setUp() {
    Empresa empresa = FabricaDeModelsParaTeste.criarEmpresa("19327916000189");
    empresa = this.empresaRepository.save(empresa);


    Funcionario funcionario = FabricaDeModelsParaTeste.criarFuncionario(CPF, EMAIL, empresa);
    this.funcionarioRepository.save(funcionario);
  }

  @After
  public void tearDown() throws Exception {
    this.empresaRepository.deleteAll();
  }

  @Test
  public void testBuscarPorEmail() {
    Funcionario funcionario = this.funcionarioRepository.getByEmail(EMAIL);
    Assert.assertEquals(EMAIL, funcionario.getEmail());
  }

  @Test
  public void testBuscarPorCpf() {
    Funcionario funcionario = this.funcionarioRepository.getByCpf(CPF);
    Assert.assertEquals(CPF, funcionario.getCpf());
  }


}
