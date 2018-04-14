package br.com.reclameaqui.teste.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.reclameaqui.teste.documents.Reclamacao;
import br.com.reclameaqui.teste.dtos.ReclamantesResponseDTO;


@Repository
public interface ReclamacaoRepository extends MongoRepository<Reclamacao, String>, ReclamacaoRepositoryCustom{
	public Reclamacao findById(String id);
	
	 @Query(value = "{ 'empresa.nome' : ?0, 'cidade.nome' : ?1 }")
	public ReclamantesResponseDTO buscarQuantidadeClientesQueReclamam(String empresa, String cidade);
}
