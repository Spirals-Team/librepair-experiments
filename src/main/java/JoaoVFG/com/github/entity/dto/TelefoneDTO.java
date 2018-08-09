package JoaoVFG.com.github.entity.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TelefoneDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String tipoNumero;
	
	private String numero;
	
	private Integer idPessoa;

	public TelefoneDTO(String tipoNumero, String numero, Integer idPessoa) {
		super();
		this.tipoNumero = tipoNumero;
		this.numero = numero;
		this.idPessoa = idPessoa;
	}
	
	
}
