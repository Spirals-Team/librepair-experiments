package com.angelorobson.pontointeligente.api.services;

import com.angelorobson.pontointeligente.api.entities.Funcionario;
import com.angelorobson.pontointeligente.api.repositories.FuncionarioRepository;
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
public class FuncionarioServiceTest {

  @MockBean
  FuncionarioRepository funcionarioRepository;

  @Autowired
  FuncionarioService funcionarioService;

  private static final String CPF = "753.311.230-07";
  private static final String EMAIL = "angelorobsonmelo@gmail.com";
  private static final String ID = "22";

  @Before
  public void setup() throws Exception {
    BDDMockito.given(this.funcionarioRepository.save(Mockito.any(Funcionario.class))).willReturn(new Funcionario());
    BDDMockito.given(this.funcionarioRepository.findByCpf(Mockito.anyString())).willReturn(new Funcionario());
    BDDMockito.given(this.funcionarioRepository.findByEmail(Mockito.anyString())).willReturn(new Funcionario());
    BDDMockito.given(this.funcionarioRepository.findOne(Mockito.anyLong())).willReturn(new Funcionario());
  }

  @Test
  public void testPersistirFuncionario() {
    Funcionario funcionario = funcionarioService.persistir(new Funcionario());

    Assert.assertNotNull(funcionario);
  }

  @Test
  public void testBuscarPorCpf() {
    Optional funcionario = funcionarioService.buscarPorCpf(CPF);

    Assert.assertTrue(funcionario.isPresent());
  }

  @Test
  public void testBuscarPorEmail() {
    Optional funcionario = funcionarioService.buscarPorEmail(EMAIL);

    Assert.assertTrue(funcionario.isPresent());
  }

  @Test
  public void testBuscarPorID() {
    Optional funcionario = funcionarioService.buscarPorEmail(ID);

    Assert.assertTrue(funcionario.isPresent());
  }



}
