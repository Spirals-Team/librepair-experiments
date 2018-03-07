package com.tiagogouvea.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiagogouvea.api.entities.Empresa;
import com.tiagogouvea.api.services.EmpresaService;

@RestController
@RequestMapping("/api/empresa")
public class EmpresaController {

	@Autowired
	EmpresaService empresaService;
	
	@GetMapping(value = "/{cnpj}")
	public Empresa buscarEmpresa (@PathVariable("cnpj") String cnpj) {
		
		System.out.println("CNPJ: " + cnpj);
		Empresa emp = empresaService.findByCnpj(cnpj);
		
		System.out.println(emp.toString());
		
		return emp;
	}
	
}
