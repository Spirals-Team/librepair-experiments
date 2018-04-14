package br.com.reclameaqui.teste.service;

import java.lang.reflect.Type;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;

import br.com.reclameaqui.teste.documents.Cliente;
import br.com.reclameaqui.teste.dtos.ClienteDTO;
import br.com.reclameaqui.teste.dtos.ReclamantesRequestDTO;
import br.com.reclameaqui.teste.dtos.ReclamantesResponseDTO;
import br.com.reclameaqui.teste.enums.TipoConsulta;
import br.com.reclameaqui.teste.exceptions.ClienteNaoEncontradoException;
import br.com.reclameaqui.teste.repository.ClienteRepository;
import br.com.reclameaqui.teste.repository.ReclamacaoRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

	private final Logger logger = Logger.getLogger(ClienteServiceImpl.class);

	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	ReclamacaoRepository reclamacaoRepository;
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Override
	public ClienteDTO consultar(String id) throws ClienteNaoEncontradoException {
		logger.info("Consultando cliente...");
		Cliente cliente = clienteRepository.findById(id);
		validarCliente(cliente);

		ClienteDTO clienteDTO = modelMapper().map(cliente, ClienteDTO.class);
		
		return clienteDTO;
	}

	@Override
	public List<ClienteDTO> buscarTodos() {
		logger.info("Consultando cliente...");

		List<Cliente> clientes = (List<Cliente>) clienteRepository.findAll();

		Type listType = new TypeToken<List<ClienteDTO>>() {}.getType();
		List<ClienteDTO> clientesDTO = modelMapper().map(clientes, listType);

		return clientesDTO;
	}

	@Override
	public ClienteDTO incluir(ClienteDTO clienteDTO) {
		logger.info("Incluindo cliente...");
		
		Cliente cliente = modelMapper().map(clienteDTO, Cliente.class);

		clienteRepository.save(cliente);
		
		return modelMapper().map(cliente, ClienteDTO.class);
	}

	@Override
	public ClienteDTO atualizar(String id, ClienteDTO clienteDTODetails) throws ClienteNaoEncontradoException {
		logger.info("Atualizando cliente...");

		Cliente cliente = clienteRepository.findOne(id);
		
		validarCliente(cliente);
		
		Cliente clienteDetails = modelMapper().map(clienteDTODetails, Cliente.class);
		
		cliente = cliente.update(cliente, clienteDetails);

		clienteRepository.save(cliente);
		
		return modelMapper().map(cliente, ClienteDTO.class);
	}
	
	@Override
	public ResponseEntity<ClienteDTO> deletar(String id) throws ClienteNaoEncontradoException {
		logger.info("Deletando cliente... id: " + id);
		
		Cliente cliente = clienteRepository.findOne(id);
		validarCliente(cliente);
		
		clienteRepository.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@Override
	public ReclamantesResponseDTO buscarClientesQueReclamam(ReclamantesRequestDTO consulta) {
		
		TipoConsulta tipocConsulta = verificaParametros(consulta);
		
		List<Cliente> clientes = reclamacaoRepository.buscarClientesQueReclamam(tipocConsulta,consulta);
		
		Type listType = new TypeToken<List<ClienteDTO>>() {}.getType();
		List<ClienteDTO> clientesDTO = modelMapper().map(clientes, listType);
		
		return new ReclamantesResponseDTO(clientes.size(),clientesDTO);
	}

	private TipoConsulta verificaParametros(ReclamantesRequestDTO consulta) {
		if(StringUtils.isNotBlank(consulta.getEmpresa()) && StringUtils.isNotBlank(consulta.getCidade())) {
			return TipoConsulta.EMPRESAECIDADE;
		}
		
		if(StringUtils.isNotBlank(consulta.getCidade())) {
			return TipoConsulta.CIDADE;
		}
		
		if(StringUtils.isNotBlank(consulta.getEmpresa())) {
			return TipoConsulta.EMPRESA;
		}
		return null;
	}

	@Override
	public ClienteDTO consultarPorCpf(String cpf) throws ClienteNaoEncontradoException {
		logger.info("Consultando cliente...");
		Cliente cliente = clienteRepository.findByCpf(cpf);
		validarCliente(cliente);

		ClienteDTO clienteDTO = modelMapper().map(cliente, ClienteDTO.class);
		
		return clienteDTO;
	}

	@Override
	public ResponseEntity<ClienteDTO> deletarPorCpf(String cpf) throws ClienteNaoEncontradoException {
		logger.info("Deletando cliente... cpf: " + cpf);
		
		Cliente cliente = clienteRepository.findByCpf(cpf);
		validarCliente(cliente);
		
		clienteRepository.delete(cliente.getId());
		
		return ResponseEntity.noContent().build();
	}

	@Override
	public ClienteDTO atualizarPorCpf(String cpf, ClienteDTO clienteDTODetails) throws ClienteNaoEncontradoException {
		logger.info("Atualizando cliente...");

		Cliente cliente = clienteRepository.findByCpf(cpf);
		validarCliente(cliente);
		Cliente clienteDetails = modelMapper().map(clienteDTODetails, Cliente.class);
		
		cliente = cliente.update(cliente, clienteDetails);

		clienteRepository.save(cliente);
		
		return modelMapper().map(cliente, ClienteDTO.class);
	}

	private void validarCliente(Cliente cliente) throws ClienteNaoEncontradoException {
		if (cliente == null) {
			throw new ClienteNaoEncontradoException("Cliente Nao encontrado");
		}
	}
	

}
