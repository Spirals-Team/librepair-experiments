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

import JoaoVFG.com.github.dto.request.insert.InsertFuncionarioDTO;
import JoaoVFG.com.github.entity.Funcionario;
import JoaoVFG.com.github.service.FuncionarioService;

@RestController
@RequestMapping(value = "/funcionario")
public class FuncionarioResource {

	@Autowired
	private FuncionarioService funcionarioService;
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buscafuncionario", method = RequestMethod.GET)
	public ResponseEntity<List<Funcionario>> findAll() {
		List<Funcionario> funcionarios = funcionarioService.findAll();
		return ResponseEntity.ok(funcionarios);
	}
	
	@PreAuthorize("hasRole('ROLE_BUSCAS_AVANCADAS_FUNCIONARIO')  or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buscafuncionario/{id}", method = RequestMethod.GET)
	public ResponseEntity<Funcionario> findById(@PathVariable Integer id) {
		Funcionario funcionario = funcionarioService.findById(id);
		return ResponseEntity.ok(funcionario);
	}
	
	
	@PreAuthorize("hasRole('ROLE_BUSCA_FUNCIONARIO_ID')  or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buscafuncionario/pessoa/{id}", method = RequestMethod.GET)
	public ResponseEntity<Funcionario> findByPessoa(@PathVariable Integer id) {
		Funcionario funcionario = funcionarioService.findByPessoa(id);
		return ResponseEntity.ok(funcionario);
	}

	@PreAuthorize("hasRole('ROLE_BUSCAS_AVANCADAS_FUNCIONARIO')  or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buscafuncionario/empresa/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Funcionario>> findByEmpresa(@PathVariable Integer id) {
		List<Funcionario> funcionarios = funcionarioService.findByEmpresa(id);
		return ResponseEntity.ok(funcionarios);
	}
	
	@PreAuthorize("hasRole('ROLE_BUSCAS_AVANCADAS_FUNCIONARIO')  or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buscafuncionario/empresa/{idEmpresa}/cargo/{idCargo}", method = RequestMethod.GET)
	public ResponseEntity<List<Funcionario>> findByEmpresaAndCargo(@PathVariable Integer idEmpresa, @PathVariable Integer idCargo) {
		List<Funcionario> funcionarios = funcionarioService.findByEmpresaCargo(idEmpresa, idCargo);
		return ResponseEntity.ok(funcionarios);
	}
	
	
	@PreAuthorize("hasRole('ROLE_CREATE_FUNCIONARIO')  or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/insere", method = RequestMethod.POST)
	public ResponseEntity<Void> insertFuncionario(@RequestBody InsertFuncionarioDTO dto){
		Funcionario funcionario = funcionarioService.insertFuncionario(dto);
		URI uri = URI.create("/funcionario" + "/buscafuncionario/" + funcionario.getId());
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasRole('ROLE_DELETE_FUNCIONARIO')  or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/deleta/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletaFuncinario(@PathVariable Integer id){
		funcionarioService.deletaFuncionario(id);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasRole('ROLE_UPDATE_FUNCIONARIO')  or hasRole('ROLE_ADMIN')")
	@RequestMapping(value= "/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Funcionario> updateFuncionario(@PathVariable Integer id, @RequestBody InsertFuncionarioDTO insertFuncionarioDTO){
		Funcionario funcionario = funcionarioService.updateFuncionario(id,insertFuncionarioDTO);
		return ResponseEntity.ok(funcionario);
	}
}
