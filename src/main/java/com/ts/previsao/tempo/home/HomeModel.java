package com.ts.previsao.tempo.home;

import com.ts.previsao.tempo.previsao.Previsao;
import com.ts.previsao.tempo.previsao.PrevisaoModel;

public class HomeModel {

    public String info() throws Exception {
        PrevisaoModel previsao = new PrevisaoModel();
        System.out.println(previsao.getXMLPrevisao(4963));
        Previsao[] previsoes = previsao.xmlToObjectPrevisao(previsao.getXMLPrevisao(4963));
        System.out.println(previsoes[0].getMaxima());
        return "all good";
    }
}
