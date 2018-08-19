package JoaoVFG.com.github.dto.request;

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
	
	public Integer idEmpresa;
	public ArrayList<EnderecoEntregaDTO> waypoints;
	
	public ListaEnderecoRotaDTO(Integer idEmpresa, ArrayList<EnderecoEntregaDTO> waypoints) {
		super();
		this.idEmpresa = idEmpresa;
		this.waypoints = waypoints;
	}
	
	
	
	
}
