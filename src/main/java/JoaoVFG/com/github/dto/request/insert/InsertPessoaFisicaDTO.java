package JoaoVFG.com.github.dto.request.insert;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InsertPessoaFisicaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String nome;
	
	private Integer tipo;
	
	private String cpf;
	
	private String dataNascimento;
	
	private String sexo;

	public InsertPessoaFisicaDTO(String nome, Integer tipo, String cpf, String dataNascimento, String sexo) {
		super();
		this.nome = nome;
		this.tipo = tipo;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InsertPessoaFisicaDTO [nome=");
		builder.append(nome);
		builder.append(", tipo=");
		builder.append(tipo);
		builder.append(", cpf=");
		builder.append(cpf);
		builder.append(", dataNascimento=");
		builder.append(dataNascimento);
		builder.append(", sexo=");
		builder.append(sexo);
		builder.append("]");
		return builder.toString();
	}
	
	
}
