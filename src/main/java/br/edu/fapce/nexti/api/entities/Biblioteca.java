package br.edu.fapce.nexti.api.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "biblioteca")
public class Biblioteca {
	
	private Long id;
	private String nome;
	private Set<PalavrasBiblioteca> palavras;
	
	public Biblioteca() {
	}

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "nome", nullable = false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@OneToMany(mappedBy = "Biblioteca", cascade = CascadeType.ALL)
    public Set<PalavrasBiblioteca> getPalavras() {
        return palavras;
    }
	
	public void setBiblioteca(Set<PalavrasBiblioteca> palavras) {
		this.palavras = palavras;
	}
	
}
