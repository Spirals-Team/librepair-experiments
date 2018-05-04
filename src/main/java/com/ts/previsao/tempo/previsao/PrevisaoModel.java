package com.ts.previsao.tempo.previsao;

import static com.ts.previsao.tempo.utils.CommonsUtils.removeXMLMetaData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.ts.previsao.tempo.utils.Acoes;
import com.ts.previsao.tempo.utils.CommonsUtils;
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

	public List<Previsao> xmlToObjectPrevisao(String xml) throws Exception {
		StringReader sr = new StringReader(xml);
		JAXBContext context = JAXBContext.newInstance(Previsoes.class);
		Unmarshaller un = context.createUnmarshaller();
		Previsoes previsoes = (Previsoes) un.unmarshal(sr);
		return Arrays.asList(previsoes.getPrevisao());
	}

	public List<Previsao> getPrevisao(Integer id) throws Exception {
		PrevisaoModel previsaoModel = new PrevisaoModel();
		return previsaoModel.xmlToObjectPrevisao(previsaoModel.getXMLPrevisao(id));
	}

	public List<PrevisaoRepository> filtraPrevisao(Integer id, String atualizacao) throws Exception {
		PrevisaoDAO previsaoDao = new PrevisaoDAO();
		List<PrevisaoRepository> previsaoRepositoryList = null;
		if (CommonsUtils.formataDataAtual().equals(atualizacao)) {
			previsaoRepositoryList = previsaoDao.selectAllPrevisao(id);
			if(previsaoRepositoryList.isEmpty()) {
				previsaoRepositoryList = atualizaPrevisoes(id);
			}
		} else {
			previsaoDao.removeAllPrevisao(id);
			previsaoRepositoryList = atualizaPrevisoes(id);
		}
		return previsaoRepositoryList;
	}
	
	public List<PrevisaoRepository> atualizaPrevisoes(Integer id) throws Exception{
		List<Previsao> previsoes = getPrevisao(id);
		return inserePrevisoes(previsoes, id);	
	}
	

	public List<PrevisaoRepository> inserePrevisoes(List<Previsao> previsoes, Integer id) {
		PrevisaoRepository previsaoRepository = new PrevisaoRepository();
		PrevisaoDAO previsaoDao = new PrevisaoDAO();
		List<PrevisaoRepository> previsaoRepositoryList = new ArrayList<>();
		for (Previsao previsao : previsoes) {
			previsaoRepository.setId(id);
			previsaoRepository.setDia(CommonsUtils.convertSeparadorData(previsao.getDia()));
			previsaoRepository.setIuv(previsao.getIuv());
			previsaoRepository.setMaxima(previsao.getMaxima());
			previsaoRepository.setMinima(previsao.getMinima());
			previsaoRepository.setTempo(previsao.getTempo());
			previsaoRepositoryList.add(previsaoRepository);
			previsaoDao.insertPrevisao(previsaoRepository);
		}
		return previsaoRepositoryList;
	}
}
