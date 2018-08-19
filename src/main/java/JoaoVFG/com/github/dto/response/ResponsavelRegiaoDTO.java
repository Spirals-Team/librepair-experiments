package JoaoVFG.com.github.dto.response;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponsavelRegiaoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String Cep;
	private String Empresa;
	
	public ResponsavelRegiaoDTO(String cep, String empresa) {
		super();
		Cep = cep;
		Empresa = empresa;
	}
	
	

}
