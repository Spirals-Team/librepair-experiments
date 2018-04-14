package br.com.reclameaqui.teste.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;

import br.com.reclameaqui.teste.documents.Cliente;
import br.com.reclameaqui.teste.documents.Reclamacao;
import br.com.reclameaqui.teste.dtos.ClienteDTO;
import br.com.reclameaqui.teste.dtos.ReclamantesRequestDTO;
import br.com.reclameaqui.teste.dtos.ReclamantesResponseDTO;
import br.com.reclameaqui.teste.dtos.ReclamacaoDTO;
import br.com.reclameaqui.teste.exceptions.CampoNaoEncontradoException;
import br.com.reclameaqui.teste.exceptions.CampoObrigatorioException;
import br.com.reclameaqui.teste.repository.ClienteRepository;
import br.com.reclameaqui.teste.repository.ReclamacaoRepository;

@Service
public class ReclamacaoServiceImpl implements ReclamacaoService {

	private final Logger logger = Logger.getLogger(ReclamacaoServiceImpl.class);

	@Autowired
	ReclamacaoRepository reclamacaoRepository;
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Override
	public ReclamacaoDTO consultar(String id) {
		logger.info("Consultando reclamacao...");
		
		Reclamacao reclamacao = reclamacaoRepository.findById(id);
		
		if (reclamacao == null) {
			logger.error("Erro ao consultar Reclamacao!");
			//throw new CampoNaoEncontradoException("Reclamacao Nao encontrado!");
		}

		ReclamacaoDTO reclamacaoDTO = modelMapper().map(reclamacao, ReclamacaoDTO.class);
		
		return reclamacaoDTO;
	}

	@Override
	public List<ReclamacaoDTO> buscarTodos() {
		logger.info("Consultando reclamacao...");

		List<Reclamacao> reclamacaos = (List<Reclamacao>) reclamacaoRepository.findAll();

		Type listType = new TypeToken<List<ReclamacaoDTO>>() {}.getType();
		List<ReclamacaoDTO> reclamacaosDTO = modelMapper().map(reclamacaos, listType);

		return reclamacaosDTO;
	}

	@Override
	public ReclamacaoDTO incluir(ReclamacaoDTO reclamacaoDTO) {
		logger.info("Incluindo reclamacao...");

		Reclamacao reclamacao = modelMapper().map(reclamacaoDTO, Reclamacao.class);
		
		/*
		Cliente cliente = clienteRepository.findOne(idCliente);
		if (cliente != null) {
			cliente.setId(idCliente);
			cliente.getReclamacoes().add(reclamacao);
		}
		*/

		//reclamacaoRepository.save(reclamacao);
		reclamacaoRepository.save(reclamacao);
		
		return modelMapper().map(reclamacao, ReclamacaoDTO.class);
	}

	@Override
	public ReclamacaoDTO atualizar(String id, ReclamacaoDTO reclamacaoDTODetails) throws CampoNaoEncontradoException {
		logger.info("Atualizando reclamacao...");

		Reclamacao reclamacao = reclamacaoRepository.findById(id);
		Reclamacao reclamacaoDetails = modelMapper().map(reclamacaoDTODetails, Reclamacao.class);
		
		reclamacao = reclamacao.update(reclamacao, reclamacaoDetails);

		reclamacaoRepository.save(reclamacao);
		
		return modelMapper().map(reclamacao, ReclamacaoDTO.class);
	}
	
	@Override
	public void deletar(String id) throws CampoObrigatorioException {
		logger.info("Deletando reclamacao... id: " + id);
		Reclamacao reclamacao = reclamacaoRepository.findOne(id);
		if (reclamacao == null) {
			logger.error("Erro ao deletar Reclamacao!");
			throw new CampoObrigatorioException("Reclamacao Nao encontrado para ser excluido!");
		}
		reclamacaoRepository.delete(id);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


	

}
