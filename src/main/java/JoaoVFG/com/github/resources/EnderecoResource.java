package JoaoVFG.com.github.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import JoaoVFG.com.github.dto.request.insert.InsertEnderecoDTO;
import JoaoVFG.com.github.entity.Endereco;
import JoaoVFG.com.github.service.EnderecoService;

@RestController
@RequestMapping(value = "/endereco")
public class EnderecoResource {

	@Autowired
	EnderecoService enderecoService;
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/buscaendereco/{id}", method = RequestMethod.GET)
	public ResponseEntity<Endereco> findById(@PathVariable Integer id) {
		Endereco endereco = enderecoService.findById(id);
		return ResponseEntity.ok(endereco);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buscaendereco", method = RequestMethod.GET)
	public ResponseEntity<List<Endereco>> findAll() {
		List<Endereco> enderecos = enderecoService.findAll();
		return ResponseEntity.ok(enderecos);
	}

	@PreAuthorize("hasRole('ROLE_BUSCA_ENDERECO_PESSOA') or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buscaendereco/pessoa/{id}", method = RequestMethod.GET)
	public ResponseEntity<Endereco> findByPessoa(@PathVariable Integer id) {
		Endereco endereco = enderecoService.findByPessoa(id);
		return ResponseEntity.ok(endereco);
	}

	@PreAuthorize("hasRole('ROLE_BUSCAR_ENDERECOS_POR_CEP') or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buscaendereco/cep/{cep}", method = RequestMethod.GET)
	public ResponseEntity<List<Endereco>> findByCep(@PathVariable String cep) {
		List<Endereco> enderecos = enderecoService.findByCep(cep);
		return ResponseEntity.ok(enderecos);
	}

	
	@RequestMapping(value = "/insere", method = RequestMethod.POST)
	public ResponseEntity<Void> createEndereco(@RequestBody InsertEnderecoDTO insertEnderecoDTO) {
		Endereco endereco = enderecoService.createFromDTO(insertEnderecoDTO);
		URI uri = URI.create("/endereco" + "buscaendereco/" + endereco.getId());
		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasRole('ROLE_DELETE_ENDERECO')or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/deleta/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletaEndereco(@PathVariable Integer id) {
		enderecoService.deletarEndereco(enderecoService.findById(id));
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasRole('ROLE_UPDATE_ENDERECO') or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Endereco> updateEndereco(@PathVariable Integer id, @RequestBody InsertEnderecoDTO enderecoUpdate) {
		Endereco endereco = enderecoService.updateEndereco(id, enderecoUpdate);
		return ResponseEntity.ok().body(endereco);
	}
}
