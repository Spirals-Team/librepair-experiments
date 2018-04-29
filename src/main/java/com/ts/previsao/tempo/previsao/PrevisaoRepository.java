package com.ts.previsao.tempo.previsao;

public class PrevisaoRepository {
	private Integer id;
	private String dia;
	private String tempo;
	private Double minima;
	private Double maxima;
	private Double iuv;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getTempo() {
		return tempo;
	}

	public void setTempo(String tempo) {
		this.tempo = tempo;
	}

	public Double getMinima() {
		return minima;
	}

	public void setMinima(Double minima) {
		this.minima = minima;
	}

	public Double getMaxima() {
		return maxima;
	}

	public void setMaxima(Double maxima) {
		this.maxima = maxima;
	}

	public Double getIuv() {
		return iuv;
	}

	public void setIuv(Double iuv) {
		this.iuv = iuv;
	}
}
