package JoaoVFG.com.github.dto.request.insert;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InsertRegiaoByBairroDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String descricao;
	private Integer empresaId;
	private String cidade;
	private String bairro;

	public InsertRegiaoByBairroDTO(String descricao, Integer empresaId, String cidade, String bairro) {
		super();
		this.descricao = descricao;
		this.empresaId = empresaId;
		this.cidade = cidade;
		this.bairro = bairro;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InsertRegiaoByBairroDTO [descricao=");
		builder.append(descricao);
		builder.append(", empresaId=");
		builder.append(empresaId);
		builder.append(", cidade=");
		builder.append(cidade);
		builder.append(", bairro=");
		builder.append(bairro);
		builder.append("]");
		return builder.toString();
	}
	
	
}
