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
public class Empresa implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "PESSOA_ID")
	private Pessoa pessoa;
	
	@ManyToOne
	@JoinColumn(name = "TIPO_EMPRESA")
	private TipoEmpresa tipoEmpresa;
	
	private Integer transportadora;

	private Integer empresaMatrizId;

	public Empresa(Integer id, Pessoa pessoa, TipoEmpresa tipoEmpresa, Integer transportadora, Integer empresaMatrizId) {
		super();
		this.id = id;
		this.pessoa = pessoa;
		this.tipoEmpresa = tipoEmpresa;
		this.transportadora = transportadora;
		this.empresaMatrizId = empresaMatrizId;
	}
	
	
}
