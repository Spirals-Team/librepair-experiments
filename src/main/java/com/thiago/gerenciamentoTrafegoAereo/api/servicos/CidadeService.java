package com.thiago.gerenciamentoTrafegoAereo.api.servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiago.gerenciamentoTrafegoAereo.api.dao.interfaces.DaoCidadeInterface;
import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Cidade;
import com.thiago.gerenciamentoTrafegoAereo.api.response.Response;

@RestController
@RequestMapping("/api/cidade")
@CrossOrigin(origins = "*")
public class CidadeService
{
	@Autowired
	private DaoCidadeInterface daoCidadeInterface;
	
	//TODO Verificar a necessidade de criar um serviço on ira buscas a cidade por nome
	
	@GetMapping
	public ResponseEntity<Response<List<Cidade>>> listar()
	{
		Response<List<Cidade>> cidadeResponse = new Response<List<Cidade>>();
		List<Cidade> alCidade = daoCidadeInterface.listar();
		cidadeResponse.setData(alCidade);
		return ResponseEntity.ok(cidadeResponse);
	}
}
