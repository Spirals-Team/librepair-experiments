package JoaoVFG.com.github.dto.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EnderecoEntregaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public String cep;
	public String numeroLogradouro;
	
	
	public EnderecoEntregaDTO(String cep, String numeroLogradouro) {
		super();
		this.cep = cep;
		this.numeroLogradouro = numeroLogradouro;
	}
	
	
}
