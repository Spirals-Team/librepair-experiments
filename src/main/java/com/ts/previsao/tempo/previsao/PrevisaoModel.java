package com.ts.previsao.tempo.previsao;

import static com.ts.previsao.tempo.utils.CommonsUtils.removeXMLMetaData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.ts.previsao.tempo.utils.Acoes;
import com.ts.previsao.tempo.utils.UrlBuilder;

public class PrevisaoModel {

	private UrlBuilder urlBuilder;

	public PrevisaoModel() {
		this.urlBuilder = new UrlBuilder();
	}

	public String getXMLPrevisao(Integer codigoCidade) throws Exception {
		String linha;
		String resultado = "";
		String urlListaCidade = this.urlBuilder.make(Acoes.PREVISAO_7_DIAS, codigoCidade.toString());
		URL url = new URL(urlListaCidade);
		URLConnection conexao = url.openConnection();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(conexao.getInputStream(), Charset.forName("ISO-8859-1")));
		while ((linha = reader.readLine()) != null) {
			resultado += linha;
		}
		return removeXMLMetaData(resultado);
	}

	public Previsao[] xmlToObjectPrevisao(String xml) throws Exception {
		StringReader sr = new StringReader(xml);
		JAXBContext context = JAXBContext.newInstance(Previsoes.class);
		Unmarshaller un = context.createUnmarshaller();
		Previsoes previsoes = (Previsoes) un.unmarshal(sr);
		return previsoes.getPrevisao();
	}
}
