package JoaoVFG.com.github.entity.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EnderecoClienteDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public String cep;
	public String numeroLogradouro;
	
	
	public EnderecoClienteDTO(String cep, String numeroLogradouro) {
		super();
		this.cep = cep;
		this.numeroLogradouro = numeroLogradouro;
	}
	
	
}
