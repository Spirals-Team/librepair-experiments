package com.thiago.gerenciamentoTrafegoAereo.api.servicos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiago.gerenciamentoTrafegoAereo.api.dao.interfaces.DaoPilotoInterface;
import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Piloto;
import com.thiago.gerenciamentoTrafegoAereo.api.response.Response;

@RestController
@RequestMapping("/api/piloto")
@CrossOrigin(origins = "*")
public class PilotoService
{
	@Autowired
	private DaoPilotoInterface daoPilotoInterface;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<Piloto>> getPorId(@PathVariable("id") Long id)
	{
		Response<Piloto> pilotoResponse = new Response<Piloto>();
		Optional<Piloto> piloto = this.daoPilotoInterface.buscarPorId(id);
		if (!piloto.isPresent())
		{
			pilotoResponse.getErros().add("Piloto não encontrado");			
			return ResponseEntity.badRequest().body(pilotoResponse);		
		}
		pilotoResponse.setData(piloto.get());
		return ResponseEntity.ok(pilotoResponse);
	}	
	
	@GetMapping
	public ResponseEntity<Response<List<Piloto>>> listar()
	{
		Response<List<Piloto>> aviaoResponse = new Response<List<Piloto>>();
		List<Piloto> alPilotos = daoPilotoInterface.listar();
		aviaoResponse.setData(alPilotos);
		return ResponseEntity.ok(aviaoResponse);
	}
}
