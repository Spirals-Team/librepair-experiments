package JoaoVFG.com.github.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import JoaoVFG.com.github.dto.request.ListaEnderecoRotaDTO;
import JoaoVFG.com.github.dto.response.RotaResponseDTO;
import JoaoVFG.com.github.service.route.RotaService;

@RestController
@RequestMapping(value = "/rota")
public class RotaResource {

	@Autowired
	private RotaService rotaService;
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(method = RequestMethod.POST, value = "/criarotajson")
	public ResponseEntity<RotaResponseDTO> gerarRota(@RequestBody ListaEnderecoRotaDTO listaEnderecoRotaDTO){
		RotaResponseDTO rotaResponseDTO = rotaService.geraRotaRespose(listaEnderecoRotaDTO);
		return ResponseEntity.ok().body(rotaResponseDTO);
	}
}
