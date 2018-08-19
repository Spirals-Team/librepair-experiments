package JoaoVFG.com.github.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "TIPO_ID")
	private TipoPessoa tipo;
	
	private String nome;
	
	private String razaoSocial;
	
	private String cpf;
	
	private String cnpj;
	
	private String dataNascimento;
	
	private String sexo;
	
	//Construtor Pessoa Fisica
	public Pessoa(Integer id,TipoPessoa tipo, String nome, String cpf, String dataNascimento, String sexo) {
		this.id = id;
		this.tipo = tipo;
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
	}
	
	//Construtor Pessoa juridica
	public Pessoa(Integer id, TipoPessoa tipo, String razaoSocial, String cnpj) {
		this.id = id;
		this.tipo = tipo;
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pessoa [id=");
		builder.append(id);
		builder.append(", tipo=");
		builder.append(tipo);
		builder.append(", nome=");
		builder.append(nome);
		builder.append(", razaoSocial=");
		builder.append(razaoSocial);
		builder.append(", cpf=");
		builder.append(cpf);
		builder.append(", cnpj=");
		builder.append(cnpj);
		builder.append(", dataNascimento=");
		builder.append(dataNascimento);
		builder.append(", sexo=");
		builder.append(sexo);
		builder.append("]");
		return builder.toString();
	}
	
	
}
