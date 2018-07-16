package br.com.rodolfo.pontointeligente.api.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.rodolfo.pontointeligente.api.entities.Empresa;

@RunWith(SpringRunner.class)
@SpringBootTest
//@TestPropertySource("/my-test.properties")
//Faz com que o teste seja executado utilizando o profile de test que acrregará os dados do 'application-test.properties'
@ActiveProfiles("test")
public class EmpresaRepositoryTest {

    @Autowired
    private EmpresaRepository empresaRepository;

    private static final String CNPJ = "5146364500100";

    @Before
    public void setUp() throws Exception {
        
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("Empresa de exemplo");
        empresa.setCnpj(CNPJ);

        this.empresaRepository.save(empresa);
    }

    @After
    public final void tearDown() {
        
        this.empresaRepository.deleteAll();
    }

    @Test
    public void testBucarPorCnpj() {

        Empresa empresa = this.empresaRepository.findByCnpj(CNPJ);

        assertEquals(CNPJ, empresa.getCnpj());
    }



}