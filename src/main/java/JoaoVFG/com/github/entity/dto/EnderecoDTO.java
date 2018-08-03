package JoaoVFG.com.github.entity.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EnderecoDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer idPessoa;
	
	private String cep;
	
	private Integer numeroLogradouro;
	
	private String complemento;

	public EnderecoDTO(Integer idPessoa, String cep, Integer numeroLogradouro, String complemento) {
		super();
		this.idPessoa = idPessoa;
		this.cep = cep;
		this.numeroLogradouro = numeroLogradouro;
		this.complemento = complemento;
	}
	
	
	
}
