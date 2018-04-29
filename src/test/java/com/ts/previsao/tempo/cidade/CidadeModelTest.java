package com.ts.previsao.tempo.cidade;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class CidadeModelTest {

	private CidadeModel cidadeModel;
	private String fakeXml;
	private Cidade[] fakeCidades;

	@Before
	public void setUp() throws Exception {
		this.cidadeModel = new CidadeModel();
		this.fakeXml = this.createFakeXml();
		this.fakeCidades = this.cidadeModel.xmlToObjectCidade(this.fakeXml);
	}

	@Test
	public void testa_cidade_valida() throws Exception {
		String cidadeProcurada = this.cidadeModel.getXMLCidade("sao paulo");
		boolean contemTagCidade = cidadeProcurada.contains("<cidade>");
		boolean contemTagNome = cidadeProcurada.contains("<nome>");
		boolean contemTagUf = cidadeProcurada.contains("<uf>");
		boolean contemTagId = cidadeProcurada.contains("<id>");
		boolean resultado = contemTagCidade && contemTagNome && contemTagUf && contemTagId;
		assertTrue(resultado);
	}
	
	@Test
	public void testa_se_transforma_xml_para_objeto() throws Exception {
		assertTrue((this.cidadeModel.xmlToObjectCidade(this.fakeXml) instanceof Cidade[]));
	}
	
	@Test
	public void testa_se_acha_cidade() {
		assertSame(fakeCidades[2], this.cidadeModel.selecionaCidade(fakeCidades, "SC", "sao jose"));
	}
	
	@Test
	public void testa_se_nao_acha_cidade() {
		assertSame(null, this.cidadeModel.selecionaCidade(fakeCidades, "OO", "Enexistente"));
	}
	
	private String createFakeXml() {
		StringBuilder xmlBuilder = new StringBuilder();
		xmlBuilder.append("<cidades>");
		xmlBuilder.append("<cidade>");
		xmlBuilder.append("<nome>");
		xmlBuilder.append("Essa cidade Não existe");
		xmlBuilder.append("</nome>");
		xmlBuilder.append("<uf>");
		xmlBuilder.append("NN");
		xmlBuilder.append("</uf>");
		xmlBuilder.append("<id>");
		xmlBuilder.append("0000");
		xmlBuilder.append("</id>");
		xmlBuilder.append("</cidade>");
		xmlBuilder.append("<cidade>");
		xmlBuilder.append("<nome>");
		xmlBuilder.append("Outra José");
		xmlBuilder.append("</nome>");
		xmlBuilder.append("<uf>");
		xmlBuilder.append("SC");
		xmlBuilder.append("</uf>");
		xmlBuilder.append("<id>");
		xmlBuilder.append("0001");
		xmlBuilder.append("</id>");
		xmlBuilder.append("</cidade>");	
		xmlBuilder.append("<cidade>");
		xmlBuilder.append("<nome>");
		xmlBuilder.append("São José");
		xmlBuilder.append("</nome>");
		xmlBuilder.append("<uf>");
		xmlBuilder.append("SC");
		xmlBuilder.append("</uf>");
		xmlBuilder.append("<id>");
		xmlBuilder.append("4909");
		xmlBuilder.append("</id>");
		xmlBuilder.append("</cidade>");		
		xmlBuilder.append("</cidades>");
		return xmlBuilder.toString();
	}

}
