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
public class Cep implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String cep;

	private String nomeRua;

	private String bairro;

	@ManyToOne
	@JoinColumn(name = "CIDADE_ID")
	private Cidade cidade;

	public Cep(Integer id, String cep, String nomeRua, String bairro, Cidade cidade) {
		super();
		this.id = id;
		this.cep = cep;
		this.nomeRua = nomeRua;
		this.bairro = bairro;
		this.cidade = cidade;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cep [id=");
		builder.append(id);
		builder.append(", cep=");
		builder.append(cep);
		builder.append(", nomeRua=");
		builder.append(nomeRua);
		builder.append(", bairro=");
		builder.append(bairro);
		builder.append(", cidade=");
		builder.append(cidade);
		builder.append("]");
		return builder.toString();
	}

	
}
