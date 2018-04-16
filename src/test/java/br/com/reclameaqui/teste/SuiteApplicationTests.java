package br.com.reclameaqui.teste;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.com.reclameaqui.teste.controller.TestClienteController;
import br.com.reclameaqui.teste.controller.TestReclamacaoController;
import br.com.reclameaqui.teste.integracao.TestClienteIntegracao;
import br.com.reclameaqui.teste.integracao.TesteApplicationTests;
import br.com.reclameaqui.teste.service.TestClienteService;
import br.com.reclameaqui.teste.service.TestReclamacaoService;
import br.com.reclameaqui.teste.utils.TestUtils;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestClienteController.class,
        TestReclamacaoController.class,
        TestClienteService.class,
        TestReclamacaoService.class,
        TestUtils.class,
        TestClienteIntegracao.class,
        TesteApplicationTests.class
})
public class SuiteApplicationTests {
    @Test
    public void contextLoads() {
    	TesteApplication.main(new String[] {});
		assertTrue(Boolean.TRUE);
    }
}