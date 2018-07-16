package br.com.rodolfo.pontointeligente.api.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.rodolfo.pontointeligente.api.entities.Empresa;
import br.com.rodolfo.pontointeligente.api.entities.Funcionario;
import br.com.rodolfo.pontointeligente.api.enums.PerfilEnum;
import br.com.rodolfo.pontointeligente.api.utils.Passwordutils;

@RunWith(SpringRunner.class)
@SpringBootTest
//@TestPropertySource("/my-test.properties")
//Faz com que o teste seja executado utilizando o profile de test que acrregará os dados do 'application-test.properties'
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {

    @Autowired
    private FuncionarioRepository funcionarioRepository;
    
    @Autowired
    private EmpresaRepository empresaRepository;

    private static final String EMAIL = "teste@teste.com";
    private static final String CPF = "10547874415";

    @Before
    public void setUp() throws Exception {

        Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
        this.funcionarioRepository.save(obterDadosFuncionario(empresa));
    }

    @After
    public void tearDown() {

        this.empresaRepository.deleteAll();
    }

    @Test
    public void testBuscarFuncionarioPorEmail() {

        Funcionario funcionario = funcionarioRepository.findByEmail(EMAIL);

        assertEquals(EMAIL, funcionario.getEmail());
    }

    @Test
    public void testBuscarFuncionarioPorCpf() {
        
        Funcionario funcionario = funcionarioRepository.findByCpf(CPF);

        assertEquals(CPF, funcionario.getCpf());
    }

    @Test
    public void testBuscarFuncionarioPorEmailECpf() {
        
        Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, EMAIL);

        assertNotNull(funcionario);
    }

    @Test
    public void testBuscarFuncionarioPorEmailECpfParaEmailInavalido() {
        
        Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, "inavalido@invalido.com");

        assertNotNull(funcionario);
    }

    @Test
    public void testBuscarFuncionarioPorEmailECpfParaCpfInvalido() {
        
        Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail("123456", EMAIL);

        assertNotNull(funcionario);
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
        funcionario.setCpf(CPF);
        funcionario.setEmail(EMAIL);
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