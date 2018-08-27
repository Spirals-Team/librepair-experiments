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
public class Endereco implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "PESSOA_ID")
	private Pessoa pessoa;
	
	@ManyToOne
	@JoinColumn(name = "CEP_ID")
	private Cep cep;
	
	private Integer numeroLogradouro;
	
	private String complemento;
	

	public Endereco(Integer id, Pessoa pessoa, Cep cep, Integer numeroLogradouro, String complemento) {
		super();
		this.id = id;
		this.pessoa = pessoa;
		this.cep = cep;
		this.numeroLogradouro = numeroLogradouro;
		this.complemento = complemento;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Endereco [id=");
		builder.append(id);
		builder.append(", pessoa=");
		builder.append(pessoa);
		builder.append(", cep=");
		builder.append(cep);
		builder.append(", numeroLogradouro=");
		builder.append(numeroLogradouro);
		builder.append(", complemento=");
		builder.append(complemento);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
