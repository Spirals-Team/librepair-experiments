package JoaoVFG.com.github.resources;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import JoaoVFG.com.github.entity.dto.ListaEnderecoRotaDTO;
import JoaoVFG.com.github.service.route.RotaService;

@RestController
@RequestMapping(value = "/rota")
public class RotaResource {

	@Autowired
	private RotaService rotaService;
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/criaRotaJson")
	public ResponseEntity<URL> gerarRota(@RequestBody ListaEnderecoRotaDTO listaEnderecoRotaDTO){

		URL rota = rotaService.gerarUriRota(listaEnderecoRotaDTO);
		
		return ResponseEntity.ok().body(rota);
	}
}
