package com.ts.previsao.tempo.utils;

public class UrlBuilder {
    private String url;
    private String dataType;

    public UrlBuilder() {
        this.url = "http://servicos.cptec.inpe.br";
        this.dataType = "XML";
    }

    public String make(Acoes acao, String parametro) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(this.url).append("/");
        urlBuilder.append(this.dataType).append("/");
        urlBuilder.append(defineParametros(acao));
        return urlBuilder.toString().replace("PARAMETRO", parametro).replace(" ", "%20");
    }

    public String defineParametros(Acoes acao) {
        String acaoRealizada;
        if (acao == Acoes.PROCURAR_CIDADE)
            acaoRealizada = "listaCidades?city=PARAMETRO";
        else if (acao == Acoes.PREVISAO_7_DIAS)
            acaoRealizada = "cidade/7dias/PARAMETRO/previsao.xml";
        else
            acaoRealizada = "";
        return acaoRealizada;
    }
}
