package br.com.reclameaqui.teste.service;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;

import br.com.reclameaqui.teste.documents.Reclamacao;
import br.com.reclameaqui.teste.dtos.ReclamacaoDTO;
import br.com.reclameaqui.teste.exceptions.ReclamacaoNaoEncontradaException;
import br.com.reclameaqui.teste.repository.ReclamacaoRepository;

@Service
public class ReclamacaoServiceImpl implements ReclamacaoService {

	private final Logger logger = Logger.getLogger(ReclamacaoServiceImpl.class);
	
	private ReclamacaoRepository reclamacaoRepository;
	
	@Autowired
	public ReclamacaoServiceImpl(ReclamacaoRepository reclamacaoRepository) {
		this.reclamacaoRepository = reclamacaoRepository;
	}
	
	@Override
	public ReclamacaoDTO consultar(String id) throws ReclamacaoNaoEncontradaException {
		logger.info("Consultando reclamacao...");
		
		Reclamacao reclamacao = reclamacaoRepository.findById(id);
		validarReclamacao(reclamacao);
		
		ReclamacaoDTO reclamacaoDTO = modelMapper().map(reclamacao, ReclamacaoDTO.class);
		
		return reclamacaoDTO;
	}

	@Override
	public List<ReclamacaoDTO> buscarTodos() {
		logger.info("Consultando reclamacao...");

		List<Reclamacao> reclamacoes = (List<Reclamacao>) reclamacaoRepository.findAll();

		Type listType = new TypeToken<List<ReclamacaoDTO>>() {}.getType();
		List<ReclamacaoDTO> reclamacaosDTO = modelMapper().map(reclamacoes, listType);

		return reclamacaosDTO;
	}

	@Override
	public ReclamacaoDTO incluir(ReclamacaoDTO reclamacaoDTO) {
		logger.info("Incluindo reclamacao...");

		Reclamacao reclamacao = modelMapper().map(reclamacaoDTO, Reclamacao.class);
		
		reclamacaoRepository.save(reclamacao);
		
		return modelMapper().map(reclamacao, ReclamacaoDTO.class);
	}

	@Override
	public ReclamacaoDTO atualizar(String id, ReclamacaoDTO reclamacaoDTODetails) throws ReclamacaoNaoEncontradaException {
		logger.info("Atualizando reclamacao...");

		Reclamacao reclamacao = reclamacaoRepository.findById(id);
		validarReclamacao(reclamacao);
		
		Reclamacao reclamacaoDetails = modelMapper().map(reclamacaoDTODetails, Reclamacao.class);
		
		reclamacao = reclamacao.update(reclamacao, reclamacaoDetails);

		reclamacaoRepository.save(reclamacao);
		
		return modelMapper().map(reclamacao, ReclamacaoDTO.class);
	}
	
	@Override
	public ResponseEntity<ReclamacaoDTO> deletar(String id) throws ReclamacaoNaoEncontradaException {
		logger.info("Deletando reclamacao... id: " + id);
		
		Reclamacao reclamacao = reclamacaoRepository.findOne(id);
		validarReclamacao(reclamacao);
		
		reclamacaoRepository.delete(id);
		
		return ResponseEntity.noContent().build();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public void validarReclamacao(Reclamacao reclamacao) throws ReclamacaoNaoEncontradaException {
		if (reclamacao == null) {
			throw new ReclamacaoNaoEncontradaException("Reclamacao Nao encontrado");
		}
	}
	

}
