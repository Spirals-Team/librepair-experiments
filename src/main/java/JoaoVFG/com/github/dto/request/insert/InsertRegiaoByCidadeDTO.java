package JoaoVFG.com.github.dto.request.insert;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class InsertRegiaoByCidadeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String descricao;
	private Integer empresaId;
	private String estado;
	private String cidade;

	public InsertRegiaoByCidadeDTO(String descricao, Integer empresaId, String estado, String cidade) {
		super();
		this.descricao = descricao;
		this.empresaId = empresaId;
		this.estado = estado;
		this.cidade = cidade;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InsertRegiaoByCidadeDTO [descricao=");
		builder.append(descricao);
		builder.append(", empresaId=");
		builder.append(empresaId);
		builder.append(", estado=");
		builder.append(estado);
		builder.append(", cidade=");
		builder.append(cidade);
		builder.append("]");
		return builder.toString();
	}
	
	
}
