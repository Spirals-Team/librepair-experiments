package JoaoVFG.com.github.dto.request.insert;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InsertEmpresaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer pessoa;
	
	private Integer tipoEmpresa;
	
	private Integer transportadora;
	
	private Integer empresaMatriz;

	public InsertEmpresaDTO(Integer pessoa, Integer tipoEmpresa, Integer transportadora, Integer empresaMatriz) {
		super();
		this.pessoa = pessoa;
		this.tipoEmpresa = tipoEmpresa;
		this.transportadora = transportadora;
		this.empresaMatriz = empresaMatriz;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InsertEmpresaDTO [pessoa=");
		builder.append(pessoa);
		builder.append(", tipoEmpresa=");
		builder.append(tipoEmpresa);
		builder.append(", transportadora=");
		builder.append(transportadora);
		builder.append(", empresaMatriz=");
		builder.append(empresaMatriz);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
