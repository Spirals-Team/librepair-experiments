package com.ts.previsao.tempo.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CommonsUtilsTest {

	@Test
	public void testa_se_metodo_retira_metadado_de_xml() {
		String metadado = "<?xml version='1.0' encoding='ISO-8859-1'?>";
		assertEquals("", CommonsUtils.removeXMLMetaData(metadado));
	}
	
	@Test
	public void testa_se_remove_acentos_de_strings() {
		assertEquals("eeeaaaaooooiiicuuu", CommonsUtils.removeAcentos("éèêáâãàóòôõíìîçúùû"));
	}
	
	@Test
	public void testa_se_converte_nome_de_cidades() {
		assertEquals("sao jose dos campos", CommonsUtils.padronizaNomeDeCidade("São José dos Campos"));
	}
	
	@Test
	public void testa_se_converte_nome_de_cidades_com_hifen() {
		assertEquals("sao jose dos campos", CommonsUtils.padronizaNomeDeCidade("São-José-dos-Campos"));
	}
	
}
