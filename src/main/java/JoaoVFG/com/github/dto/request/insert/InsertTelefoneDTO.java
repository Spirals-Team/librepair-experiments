package JoaoVFG.com.github.dto.request.insert;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InsertTelefoneDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String tipoNumero;
	
	private String numero;
	
	private Integer idPessoa;

	public InsertTelefoneDTO(String tipoNumero, String numero, Integer idPessoa) {
		super();
		this.tipoNumero = tipoNumero;
		this.numero = numero;
		this.idPessoa = idPessoa;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InsertTelefoneDTO [tipoNumero=");
		builder.append(tipoNumero);
		builder.append(", numero=");
		builder.append(numero);
		builder.append(", idPessoa=");
		builder.append(idPessoa);
		builder.append("]");
		return builder.toString();
	}
	
	
}
