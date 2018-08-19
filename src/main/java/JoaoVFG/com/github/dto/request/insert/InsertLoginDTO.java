package JoaoVFG.com.github.dto.request.insert;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InsertLoginDTO {
	
	private Integer idPessoa;
	private String email;
	private String senha;
	
	public InsertLoginDTO(Integer idPessoa, String email, String senha) {
		super();
		this.idPessoa = idPessoa;
		this.email = email;
		this.senha = senha;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InsertLoginDTO [idPessoa=");
		builder.append(idPessoa);
		builder.append(", email=");
		builder.append(email);
		builder.append(", senha=");
		builder.append(senha);
		builder.append("]");
		return builder.toString();
	}
	
	
}
