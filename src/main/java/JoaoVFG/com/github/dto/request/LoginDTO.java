package JoaoVFG.com.github.dto.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String email;
	
	private String Senha;

	public LoginDTO(String email, String senha) {
		super();
		this.email = email;
		Senha = senha;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginDTO [email=");
		builder.append(email);
		builder.append(", Senha=");
		builder.append(Senha);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
