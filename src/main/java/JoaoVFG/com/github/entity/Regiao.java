package JoaoVFG.com.github.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Regiao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "EMPRESA_ID")
	private Empresa empresa;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "REGIAO_CEP", joinColumns = { @JoinColumn(name = "REGIAO_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "CEP_ID") })
	private Set<Cep> ceps;

	
	public Regiao(Integer id, Empresa empresa, Set<Cep> ceps) {
		super();
		this.id = id;
		this.empresa = empresa;
		this.ceps = ceps;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Regiao [id=");
		builder.append(id);
		builder.append(", descricao=");
		builder.append(descricao);
		builder.append(", empresa=");
		builder.append(empresa);
		builder.append(", ceps=");
		builder.append(ceps);
		builder.append("]");
		return builder.toString();
	}
	
	

}
