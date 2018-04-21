package br.com.jordilucas.pontointeligente.api.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jordilucas.pontointeligente.api.dtos.EmpresaDto;
import br.com.jordilucas.pontointeligente.api.entities.Empresa;
import br.com.jordilucas.pontointeligente.api.response.Response;
import br.com.jordilucas.pontointeligente.api.services.EmpresaService;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin("*")
public class EmpresaController {
	
	private static final Logger log = LoggerFactory.getLogger(EmpresaController.class);
	
	@Autowired
	private EmpresaService empresaService;
	
	public EmpresaController() {}
	
	/**
	 * Retorna uma empresa dado um cnpj
	 * 
	 * @param cnpj
	 * @return ResponseEntity<Response<EmpresaDto>>
	 * 
	 * **/
	@GetMapping("/cnpj/{cnpj}")
	public ResponseEntity<Response<EmpresaDto>> buscarPorCnpj(@PathVariable("cnpj") String cnpj) {
		
		log.info("Buscando empresa por cnpj: {}", cnpj);
		Response<EmpresaDto> response = new Response<EmpresaDto>();
		Optional<Empresa> empresa = empresaService.buscarPorCnpj(cnpj);
		
		if(!empresa.isPresent()) {
			log.info("Empresa nao encontrada para o CNPJ: {}", cnpj);
			response.getErrors().add("Empresa nao encontrada para o CNPJ "+ cnpj);
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(this.converterEmpresaDto(empresa.get()));
		return ResponseEntity.ok(response);
		
	}
	
	/**
	 * Popula dto com os dados da empresa
	 * 
	 * @param empresa
	 * @return empresaDto
	 * 
	 * **/
	private EmpresaDto converterEmpresaDto(Empresa empresa) {
		
		EmpresaDto empresaDto = new EmpresaDto();
		empresaDto.setId(empresa.getId());
		empresaDto.setCnpj(empresa.getCnpj());
		empresaDto.setRazaoSocial(empresa.getRazaoSocial());
		
		return empresaDto;
	}
	
	
}
