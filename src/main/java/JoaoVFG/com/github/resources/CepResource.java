package JoaoVFG.com.github.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import JoaoVFG.com.github.entity.Cep;
import JoaoVFG.com.github.entity.Cidade;
import JoaoVFG.com.github.service.CepService;
import JoaoVFG.com.github.service.CidadeService;

@RestController
@RequestMapping(value = "/ceps")
public class CepResource {

	@Autowired
	CepService cepService;

	@Autowired
	CidadeService cidadeService;

	@RequestMapping(value = "/buscacep/{cep}", method = RequestMethod.GET)
	public ResponseEntity<Cep> findByCep(@PathVariable String cep) {
		Cep ceps = cepService.findByCep(cep);
		return ResponseEntity.ok().body(ceps);
	}

	@RequestMapping(value = "/buscacep", method = RequestMethod.GET)
	public ResponseEntity<List<Cep>> findAll() {
		List<Cep> ceps = cepService.findAll();
		return ResponseEntity.ok().body(ceps);
	}

	@RequestMapping(value = "/buscacep/estado/{estado}/cidade/{cidade}", method = RequestMethod.GET)
	public ResponseEntity<List<Cep>> findByCidade(@PathVariable String cidade, @PathVariable String estado) {
		List<Cep> ceps = cepService.findByCidade(cidade, estado);
		return ResponseEntity.ok().body(ceps);
	}

	@RequestMapping(value = "/buscacep/rua/{nomeRua}", method = RequestMethod.GET)
	public ResponseEntity<List<Cep>> findByNomeRua(@PathVariable String nomeRua) {
		List<Cep> ceps = cepService.findByNomeRua(nomeRua);
		return ResponseEntity.ok().body(ceps);
	}

	@RequestMapping(value = "/buscacep/cidade/{cidade}/bairro/{bairro}", method = RequestMethod.GET)
	public ResponseEntity<List<Cep>> findByCidadeAndBairro(@PathVariable String cidade, @PathVariable String bairro) {
		List<Cep> ceps = cepService.findByBairroAndCidade(bairro, cidade);
		return ResponseEntity.ok(ceps);
	}

	@RequestMapping(value = "/buscacep/cidadeEstado/{estadoSigla}", method = RequestMethod.GET)
	public ResponseEntity<List<Cidade>> findByEstado(@PathVariable String estadoSigla) {
		List<Cidade> cidades = cidadeService.findByEstado(estadoSigla);
		return ResponseEntity.ok().body(cidades);
	}

}
