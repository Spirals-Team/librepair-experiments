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
	
	public Integer idUser;
	public ArrayList<EnderecoEntregaDTO> waypoints;
	
	public ListaEnderecoRotaDTO(Integer idUser, ArrayList<EnderecoEntregaDTO> waypoints) {
		super();
		this.idUser = idUser;
		this.waypoints = waypoints;
	}
	
	
	
	
}
