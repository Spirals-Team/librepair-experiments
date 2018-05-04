package com.ts.previsao.tempo.previsao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ts.previsao.tempo.cidade.CidadeModel;

public class PrevisaoModelTest {

	private PrevisaoModel previsaoModel;

	@Before
	public void setuUp() {
		this.previsaoModel = new PrevisaoModel();
	}

	@Test
	public void testa_se_pega_xml_da_previsao() throws Exception {
		assertTrue(this.previsaoModel.getXMLPrevisao(0000).contains("<previsao>"));
	}

	@Test(expected = Exception.class)
	public void testa_se_lanca_xml_lanca_exception() throws Exception {
		this.previsaoModel.getXMLPrevisao(-1);
	}

	@Test
	public void testa_se_converte_xml_para_objeto() throws Exception {
		List<Previsao> previsoes = this.previsaoModel.xmlToObjectPrevisao(this.previsaoModel.getXMLPrevisao(4963));
		assertFalse(previsoes.isEmpty());

	}

	@Test(expected = Exception.class)
	public void testa_se_lanca_excecao_na_conversao_de_xml_para_objeto() throws Exception {
		this.previsaoModel.xmlToObjectPrevisao(new CidadeModel().getXMLCidade("sao jose"));
	}

	@Test
	public void testa_se_pega_previsao() throws Exception {
		this.previsaoModel.getPrevisao(4963);
	}

	@Test(expected = Exception.class)
	public void testa_se_lanca_excecao() throws Exception {
		this.previsaoModel.getPrevisao(-1);
	}

	@Test
	public void testa_se_filtra_previsao() throws Exception {
		assertFalse(this.previsaoModel.filtraPrevisao(4963, "01/01/0001").isEmpty());
	}
	
	
	@Test
	public void testa_se_atualiza_previsoes() throws Exception {
		assertFalse(this.previsaoModel.atualizaPrevisoes(4963).isEmpty());
	}
	
	@Test
	public void testa_se_insere_previsoes() throws Exception{
		assertFalse(this.previsaoModel.inserePrevisoes(this.previsaoModel.getPrevisao(4963), 4963).isEmpty());
	}
}
