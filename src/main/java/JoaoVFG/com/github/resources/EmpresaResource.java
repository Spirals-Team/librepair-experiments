package JoaoVFG.com.github.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import JoaoVFG.com.github.entity.Empresa;
import JoaoVFG.com.github.entity.dto.EmpresaDTO;
import JoaoVFG.com.github.service.EmpresaService;

@RestController
@RequestMapping(value = "/empresa")
public class EmpresaResource {

	@Autowired
	private EmpresaService empresaService;

	@RequestMapping(value = "/buscaempresa/{id}", method = RequestMethod.GET)
	public ResponseEntity<Empresa> findById(@PathVariable Integer id) {
		Empresa empresa = empresaService.findById(id);
		return ResponseEntity.ok(empresa);
	}

	@RequestMapping(value = "/buscaempresa", method = RequestMethod.GET)
	public ResponseEntity<List<Empresa>> findAll() {
		List<Empresa> empresas = empresaService.findAll();
		return ResponseEntity.ok(empresas);
	}

	@RequestMapping(value = "/buscaempresa/idpessoa/{id}", method = RequestMethod.GET)
	public ResponseEntity<Empresa> findByIdPessoa(@PathVariable Integer id) {
		Empresa empresa = empresaService.findByIdPessoa(id);
		return ResponseEntity.ok(empresa);
	}
	
	//erro
	@RequestMapping(value = "/buscaempresa/tipoempresa/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Empresa>> findByTipoEmpresa(@PathVariable Integer id) {
		List<Empresa> empresas = empresaService.findByTipoEmpresa(id);
		return ResponseEntity.ok(empresas);
	}

	@RequestMapping(value = "/buscaempresa/transportadoras", method = RequestMethod.GET)
	public ResponseEntity<List<Empresa>> findTransportadoras() {
		List<Empresa> empresas = empresaService.findTransportadoras();
		return ResponseEntity.ok(empresas);
	}

	@RequestMapping(value = "/buscaempresa/idMatriz/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Empresa>> findByMatriz(@PathVariable Integer id) {
		List<Empresa> empresas = empresaService.findByMatriz(id);
		return ResponseEntity.ok(empresas);
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public ResponseEntity<Void> createEmpresa(@RequestBody EmpresaDTO empresaDTO) {
		Empresa empresa = empresaService.createEmpresa(empresaDTO);
		URI uri = URI.create("/empresa" + "/buscaempresa/" + empresa.getId());
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/deleta/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletaEmpresa(@PathVariable Integer id) {
		empresaService.deletarEmpresa(empresaService.findById(id));
		return ResponseEntity.noContent().build();
	}

}
