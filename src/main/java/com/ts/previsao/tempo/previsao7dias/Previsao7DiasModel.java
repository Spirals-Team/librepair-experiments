package com.ts.previsao.tempo.previsao7dias;

import com.google.gson.Gson;
import com.ts.previsao.tempo.cidade.Cidade;
import com.ts.previsao.tempo.cidade.CidadeModel;
import com.ts.previsao.tempo.utils.CommonsUtils;

public class Previsao7DiasModel {

	public String getPrevisao(String uf, String cidade) throws Exception {
		Cidade cidadeEncontrada = this.filtraCidade(uf, cidade);
		return new Gson().toJson(cidadeEncontrada);
	}

	public Cidade filtraCidade(String uf, String cidade) throws Exception {
		cidade = CommonsUtils.padronizaNomeDeCidade(cidade);
		CidadeModel cidadeModel = new CidadeModel();
		Cidade[] cidades = cidadeModel.xmlToObjectCidade(cidadeModel.getXMLCidade(cidade));
		return cidadeModel.selecionaCidade(cidades, uf, cidade);

	}

}
