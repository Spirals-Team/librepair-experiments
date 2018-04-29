package com.ts.previsao.tempo.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class UrlBuilderTest {

	private UrlBuilder urlBuilder;

	@Before
	public void setUp() {
		this.urlBuilder = new UrlBuilder();
	}

	@Test
	public void testa_acao_de_procura_cidade() {
		String esperado = "listaCidades?city=%PARAMETRO";
		assertEquals(esperado, this.urlBuilder.defineParametros(Acoes.PROCURAR_CIDADE));
	}

	@Test
	public void testa_acao_de_previsao_de_7_dias() {
		String esperado = "cidade/7dias/PARAMETRO/previsao.xml";
		assertEquals(esperado, this.urlBuilder.defineParametros(Acoes.PREVISAO_7_DIAS));
	}

	@Test
	public void testa_acao_desconhecida() {
		String esperado = "";
		assertEquals(esperado, this.urlBuilder.defineParametros(Acoes.PROCURA_ESTADO));
	}

	// TODO - implementar testes para metodo make
	// public String make(Acoes acao, String parametro) {
	// StringBuilder urlBuilder = new StringBuilder();
	// urlBuilder.append(this.url).append("/");
	// urlBuilder.append(this.dataType).append("/");
	// urlBuilder.append(defineParametros(acao));
	// return urlBuilder.toString().replace("PARAMETRO", parametro);
	// }

}
