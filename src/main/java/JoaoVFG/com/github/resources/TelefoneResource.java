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

import JoaoVFG.com.github.dto.request.insert.InsertTelefoneDTO;
import JoaoVFG.com.github.entity.Telefone;
import JoaoVFG.com.github.service.TelefoneService;

@RestController
@RequestMapping(value = "/telefone")
public class TelefoneResource {
	
	@Autowired
	TelefoneService telefoneService;
	
	@PreAuthorize("hasRole('ROLE_BUSCAS_TELEFONES_AVANCADA') or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buscatelefone", method = RequestMethod.GET)
	public ResponseEntity<List<Telefone>> FindAll(){
		List<Telefone> telefones = telefoneService.findAll();
		return ResponseEntity.ok().body(telefones);
	}
	
	@PreAuthorize("hasRole('ROLE_BUSCA_TELEFONE') or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buscatelefone/{id}", method = RequestMethod.GET)
	public ResponseEntity<Telefone> findById(@PathVariable Integer id){
		Telefone telefone = telefoneService.findById(id);
		return ResponseEntity.ok().body(telefone);
	}
	
	@PreAuthorize("hasRole('ROLE_BUSCAS_TELEFONES_AVANCADA') or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buscatelefone/pessoa/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Telefone>> findByPessoa(@PathVariable Integer id){
		List<Telefone> telefones = telefoneService.findByPessoa(id);
		return ResponseEntity.ok().body(telefones);
	}
	
	@PreAuthorize("hasRole('ROLE_BUSCA_TELEFONE') or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buscatelefone/pessoatiponum/{id}/{tipo}", method = RequestMethod.GET)
	public ResponseEntity<List<Telefone>> findByPessoaETipo(@PathVariable Integer id, @PathVariable String tipo){
		List<Telefone> telefones = telefoneService.findByPessoaTipoNum(id, tipo);
		return ResponseEntity.ok().body(telefones);
	}
	
	@PreAuthorize("hasRole('ROLE_CREATE_TELEFONE') or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/insere", method = RequestMethod.POST)
	public ResponseEntity<Void> createTelefone(@RequestBody InsertTelefoneDTO dto){
		Telefone telefone = telefoneService.createFromDto(dto);
		URI uri = URI.create(telefone.getPessoa().getId().toString());
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasRole('ROLE_DELETE_TELEFONE') or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/deleta/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletaTelefone(@PathVariable Integer id){
		telefoneService.deletaTelefone(id);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasRole('ROLE_UPDATE_TELEFONE')  or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<Telefone> updateTelefone(@RequestBody Telefone updateTelefone){
		Telefone telefone = telefoneService.updateTelefone(updateTelefone);
		return ResponseEntity.ok(telefone);
	}
	
	 
	
}
