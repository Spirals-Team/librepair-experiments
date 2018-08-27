package JoaoVFG.com.github.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import JoaoVFG.com.github.dto.request.insert.InsertRegiaoByBairroDTO;
import JoaoVFG.com.github.dto.request.insert.InsertRegiaoByCepsDTO;
import JoaoVFG.com.github.dto.request.insert.InsertRegiaoByCidadeDTO;
import JoaoVFG.com.github.entity.Regiao;
import JoaoVFG.com.github.service.RegiaoService;

@RestController
@RequestMapping(value = "/regiao")
public class RegiaoResource {

	@Autowired
	RegiaoService regiaoService;

	@PreAuthorize("hasRole('ROLE_BUSCA_REGIAO') or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buscaregiao/{id}", method = RequestMethod.GET)
	public ResponseEntity<Regiao> findById(@PathVariable("id") Integer id) {
		Regiao regiao = regiaoService.findById(id);
		return ResponseEntity.ok(regiao);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buscaregiao", method = RequestMethod.GET)
	public ResponseEntity<List<Regiao>> findAll() {
		List<Regiao> regiao = regiaoService.findAll();
		return ResponseEntity.ok(regiao);
	}

	@PreAuthorize("hasRole('ROLE_BUSCA_REGIAO') or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buscaregiao/empresa/{id}", method = RequestMethod.GET)
	public ResponseEntity<Regiao> findByEmpresa(@PathVariable("id") Integer idEmpresa) {
		Regiao regiao = regiaoService.findByEmpresa(idEmpresa);
		return ResponseEntity.ok(regiao);
	}

	@PreAuthorize("hasRole('ROLE_BUSCA_REGIAO') or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buscaregiao/empresa/{id}/descricao/{descricao}", method = RequestMethod.GET)
	public ResponseEntity<List<Regiao>> findByEmpresaAndDescricao(@PathVariable("id") Integer idEmpresa,
			@PathVariable("descricao") String descricaoRota) {
		List<Regiao> regioes = regiaoService.findByEmpresaAndDescricao(idEmpresa, descricaoRota);
		return ResponseEntity.ok(regioes);
	}
	
	@PreAuthorize("hasRole('ROLE_BUSCA_REGIAO') or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/buscaregiao/empresamatriz/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Regiao>> findByEmpresaMatriz(@PathVariable("id") Integer idEmpresaMatriz) {
		List<Regiao> regioes = regiaoService.findByEmpresaMatriz(idEmpresaMatriz);
		return ResponseEntity.ok(regioes);
	}
	
	@PreAuthorize("hasRole('ROLE_INSERE_REGIAO') or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/createmanual", method = RequestMethod.POST)
	public ResponseEntity<Regiao> createRegiaoManual(@RequestBody Regiao regiaoInsert) {
		Regiao regiao = regiaoService.createRegiao(regiaoInsert);
		return ResponseEntity.created(null).body(regiao);
	}
	
	@PreAuthorize("hasRole('ROLE_INSERE_REGIAO') or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/create/byceps", method = RequestMethod.POST)
	public ResponseEntity<Regiao> createRegiaoFromCeps(@RequestBody InsertRegiaoByCepsDTO insertRegiaoByCepsDTO) {
		Regiao regiao = regiaoService.createRegiaoByListaCeps(insertRegiaoByCepsDTO);
		return ResponseEntity.created(null).body(regiao);
	}
	
	@PreAuthorize("hasRole('ROLE_INSERE_REGIAO') or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/create/bycidade", method = RequestMethod.POST)
	public ResponseEntity<Regiao> createRegiaoFromCidade(@RequestBody InsertRegiaoByCidadeDTO insertRegiaoByCidadeDTO) {
		Regiao regiao = regiaoService.createRegiaoByCidade(insertRegiaoByCidadeDTO);
		return ResponseEntity.created(null).body(regiao);
	}
	
	@PreAuthorize("hasRole('ROLE_INSERE_REGIAO') or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/create/bybairro", method = RequestMethod.POST)
	public ResponseEntity<Regiao> createRegiaoFromBairo(@RequestBody InsertRegiaoByBairroDTO insertRegiaoByBairroDTO) {
		Regiao regiao = regiaoService.createRegiaoByBairro(insertRegiaoByBairroDTO);
		return ResponseEntity.created(null).body(regiao);
	}
	
	@PreAuthorize("hasRole('ROLE_ALTERA_REGIAO') or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/updateregiao", method = RequestMethod.PUT)
	public ResponseEntity<Regiao> updateRegiao(@RequestBody Regiao updateRegiao) {
		Regiao regiao = regiaoService.updateRegiao(updateRegiao);
		return ResponseEntity.ok(regiao);
	}

	@PreAuthorize("hasRole('ROLE_DELETA_REGIAO') or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/deleta/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletaRegiao(@PathVariable("id") Integer id) {
		regiaoService.deletaRegiao(id);
		return ResponseEntity.noContent().build();
	}
	

}
