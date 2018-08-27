package JoaoVFG.com.github.dto.request.insert;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InsertRegiaoByCepsDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String descricao;
	private Integer empresaId;
	private List<String> ceps;
	
	
	public InsertRegiaoByCepsDTO(String descricao, Integer empresaId, List<String> ceps) {
		super();
		this.descricao = descricao;
		this.empresaId = empresaId;
		this.ceps = ceps;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InsertRegiaoByCepsDTO [descricao=");
		builder.append(descricao);
		builder.append(", empresaId=");
		builder.append(empresaId);
		builder.append(", ceps=");
		builder.append(ceps);
		builder.append("]");
		return builder.toString();
	}
	
	
}
