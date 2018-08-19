package JoaoVFG.com.github.dto.request.insert;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InsertEnderecoDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer idPessoa;
	
	private String cep;
	
	private Integer numeroLogradouro;
	
	private String complemento;

	public InsertEnderecoDTO(Integer idPessoa, String cep, Integer numeroLogradouro, String complemento) {
		super();
		this.idPessoa = idPessoa;
		this.cep = cep;
		this.numeroLogradouro = numeroLogradouro;
		this.complemento = complemento;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InsertEnderecoDTO [idPessoa=");
		builder.append(idPessoa);
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
