package br.com.reclameaqui.teste.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.reclameaqui.teste.documents.Cliente;


@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String>{
	public Cliente findById(String id);
	
	List<Cliente> findByIdIn(List<String> id);

	public Cliente findByCpf(String cpf);
	
	//public void save(Cliente pedro, Cliente renata);
	
	 //List<Cliente> findByTagsIn(List<String> id);

}
