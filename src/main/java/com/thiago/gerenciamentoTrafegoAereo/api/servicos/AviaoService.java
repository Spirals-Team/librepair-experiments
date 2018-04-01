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

import com.thiago.gerenciamentoTrafegoAereo.api.dao.interfaces.DaoAviaoInterface;
import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Aviao;
import com.thiago.gerenciamentoTrafegoAereo.api.response.Response;

@RestController
@RequestMapping("/api/aviao")
@CrossOrigin(origins = "*")
public class AviaoService
{
	@Autowired
	private DaoAviaoInterface daoAviaoInterface;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<Aviao>> getPorId(@PathVariable("id") Long id)
	{
		Response<Aviao> aviaoResponse = new Response<Aviao>();
		Optional<Aviao> aviao = this.daoAviaoInterface.buscarPorId(id);
		if (!aviao.isPresent())
		{
			aviaoResponse.getErros().add("Avião não encontrado");			
			return ResponseEntity.badRequest().body(aviaoResponse);		
		}
		aviaoResponse.setData(aviao.get());
		return ResponseEntity.ok(aviaoResponse);
	}	
	
	@GetMapping
	public ResponseEntity<Response<List<Aviao>>> listar()
	{
		// TODO caso de tempo colocar paginação
		Response<List<Aviao>> aviaoResponse = new Response<List<Aviao>>();
		List<Aviao> alAvioes = daoAviaoInterface.listar();
		aviaoResponse.setData(alAvioes);
		return ResponseEntity.ok(aviaoResponse);
	}
}
