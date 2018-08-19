package JoaoVFG.com.github.dto.request.insert;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InsertPessoaJuridicaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer tipo;
	
	private String razaoSocial;

	private String cnpj;

	public InsertPessoaJuridicaDTO(Integer tipo, String razaoSocial, String cnpj) {
		super();
		this.tipo = tipo;
		this.razaoSocial = razaoSocial;
		this.cnpj = cnpj;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InsertPessoaJuridicaDTO [tipo=");
		builder.append(tipo);
		builder.append(", razaoSocial=");
		builder.append(razaoSocial);
		builder.append(", cnpj=");
		builder.append(cnpj);
		builder.append("]");
		return builder.toString();
	}

	
	
}
