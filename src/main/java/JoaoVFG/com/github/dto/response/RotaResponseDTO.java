package JoaoVFG.com.github.dto.response;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RotaResponseDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String rota;
	private List<ResponsavelRegiaoDTO> responsavel;
	
	public RotaResponseDTO(String rota, List<ResponsavelRegiaoDTO> responsavel) {
		super();
		this.rota = rota;
		this.responsavel = responsavel;
	}
	
	
	
}
