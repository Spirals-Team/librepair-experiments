package com.Gabriel.Biblioteca.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Gabriel.Biblioteca.api.dtos.MaterialDTO;

@RestController
@RequestMapping("/api/material")
public class MaterialController {
	
	@GetMapping(value= "/{codigo}")
	public String consulta(@PathVariable("codigo") String codigo) {
		return "Código apresentado: " + codigo;
	}
	@PostMapping
	public ResponseEntity<MaterialDTO> cadastrar(@RequestBody MaterialDTO materialDTO){
		
		return ResponseEntity.ok(materialDTO);
	}
}
