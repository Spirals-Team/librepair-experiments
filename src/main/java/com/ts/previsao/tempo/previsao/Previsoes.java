package com.ts.previsao.tempo.previsao;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "cidade")
@XmlType(propOrder = {"nome", "uf", "atualizacao", "previsao"})
public class Previsoes {
    @XmlElement
    private String nome;
    @XmlElement
    private String uf;
    @XmlElement
    private String atualizacao;
    @XmlElement
    private Previsao[] previsao;

    public String getNome() {
        return nome;
    }

    public String getUf() {
        return uf;
    }

    public String getAtualizacao() {
        return atualizacao;
    }

    public Previsao[] getPrevisao() {
        return previsao;
    }
}
