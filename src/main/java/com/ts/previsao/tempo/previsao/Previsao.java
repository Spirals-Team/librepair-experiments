package com.ts.previsao.tempo.previsao;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "previsao")
@XmlType(propOrder = {"dia", "tempo", "maxima", "minima", "iuv"})
public class Previsao {
    @XmlElement
    private String dia;
    @XmlElement
    private String tempo;
    @XmlElement
    private Double maxima;
    @XmlElement
    private Double minima;
    @XmlElement
    private Double iuv;

    public String getDia() {
        return dia;
    }

    public String getTempo() {
        return tempo;
    }

    public Double getMaxima() {
        return maxima;
    }

    public Double getMinima() {
        return minima;
    }

    public Double getIuv() {
        return iuv;
    }

}
