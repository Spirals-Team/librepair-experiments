package br.com.rodolfo.pontointeligente.api.services;

import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.rodolfo.pontointeligente.api.entities.Funcionario;
import br.com.rodolfo.pontointeligente.api.repositories.FuncionarioRepository;

/**
 * FuncionarioServiceTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@TestPropertySource(locations = "application-test.properties")
//Faz com que o teste seja executado utilizando o profile de test que acrregará os dados do 'application-test.properties'
@ActiveProfiles("test")
public class FuncionarioServiceTest {

    @MockBean
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioService funcionarioService;
    
    @Before
    public void setUp() throws Exception {

        BDDMockito.given(this.funcionarioRepository.save(Mockito.any(Funcionario.class))).willReturn(new Funcionario());
        BDDMockito.given(this.funcionarioRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new Funcionario()));
        BDDMockito.given(this.funcionarioRepository.findByCpf(Mockito.anyString())).willReturn(new Funcionario());
        BDDMockito.given(this.funcionarioRepository.findByEmail(Mockito.anyString())).willReturn(new Funcionario());

    }

    @Test
    public void testPersistirFuncionario() {

        Funcionario funcionario = this.funcionarioService.persistir(new Funcionario());

        assertNotNull(funcionario);
    }

    @Test
    public void testBuscarFuncionarioPorId() {

        Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(1l);

        assertNotNull(funcionario.isPresent());
    }

    @Test
    public void testBuscarFuncionarioPorCpf() {

        Optional<Funcionario> funcionario = this.funcionarioService.buscarPorCpf("123456");

        assertNotNull(funcionario.isPresent());
    }


}