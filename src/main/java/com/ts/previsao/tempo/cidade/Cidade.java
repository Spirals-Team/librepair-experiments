package com.ts.previsao.tempo.cidade;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "cidade")
@XmlType(propOrder = { "nome", "uf", "id" })
public class Cidade {
	@XmlElement(name = "id")
	private Integer id;
	@XmlElement(name = "nome")
	private String nome;
	@XmlElement(name = "uf")
	private String uf;

	public String getNome() {
		return this.nome;
	}

	public String getUf() {
		return this.uf;
	}

	public Integer getId() {
		return this.id;
	}
}