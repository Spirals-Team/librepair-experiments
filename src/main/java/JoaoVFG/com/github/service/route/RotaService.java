package JoaoVFG.com.github.service.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JoaoVFG.com.github.dto.request.ListaEnderecoRotaDTO;
import JoaoVFG.com.github.dto.response.RotaResponseDTO;
import JoaoVFG.com.github.entity.Pessoa;
import JoaoVFG.com.github.entity.security.User;
import JoaoVFG.com.github.service.PessoaService;
import JoaoVFG.com.github.service.security.UserService;



@Service
public class RotaService {

	@Autowired
	private GeraRota geraRota;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private UserService userService;
	
	
	public RotaResponseDTO geraRotaRespose(ListaEnderecoRotaDTO listaEnderecoRotaDTO) {
		User user = userService.findById(listaEnderecoRotaDTO.getIdUser());
		Pessoa pessoa = pessoaService.findById(user.getPessoa().getId());
		
		RotaResponseDTO rotaResponseDTO = geraRota.geraRota(pessoa, listaEnderecoRotaDTO.getWaypoints());
		
		return rotaResponseDTO;	
	}
}
