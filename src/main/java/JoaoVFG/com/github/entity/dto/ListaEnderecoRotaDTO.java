package JoaoVFG.com.github.entity.dto;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ListaEnderecoRotaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public String filial;
	public ArrayList<EnderecoClienteDTO> waypoints;
	
	public ListaEnderecoRotaDTO(String filial, ArrayList<EnderecoClienteDTO> enderecosClienteDTO) {
		super();
		this.filial = filial;
		this.waypoints = enderecosClienteDTO;
	}
	
	
}
