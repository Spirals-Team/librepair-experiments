package com.jairosousa.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jairosousa.cursomc.domain.Cidade;
import com.jairosousa.cursomc.domain.Estado;
import com.jairosousa.cursomc.dto.CidadeDTO;
import com.jairosousa.cursomc.dto.EstadoDTO;
import com.jairosousa.cursomc.services.CidadeService;
import com.jairosousa.cursomc.services.EstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstatoResource {

	@Autowired
	private EstadoService estadoService;

	@Autowired
	private CidadeService cidadeService;

	@GetMapping
	public ResponseEntity<List<EstadoDTO>> findAll() {
		List<Estado> list = estadoService.findAll();
		List<EstadoDTO> listDTO = list.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@GetMapping(value = "/{estadoId}/cidades")
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId) {
		List<Cidade> list = cidadeService.findByEstado(estadoId);
		List<CidadeDTO> listDTO = list.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
}
