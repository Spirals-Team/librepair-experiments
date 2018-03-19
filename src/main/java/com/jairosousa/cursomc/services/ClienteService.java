package com.jairosousa.cursomc.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jairosousa.cursomc.domain.Cidade;
import com.jairosousa.cursomc.domain.Cliente;
import com.jairosousa.cursomc.domain.Endereco;
import com.jairosousa.cursomc.domain.enums.Perfil;
import com.jairosousa.cursomc.domain.enums.TipoCliente;
import com.jairosousa.cursomc.dto.ClienteDTO;
import com.jairosousa.cursomc.dto.ClienteNewDTO;
import com.jairosousa.cursomc.repository.CidadeRepository;
import com.jairosousa.cursomc.repository.ClienteRepository;
import com.jairosousa.cursomc.repository.EnderecoRepository;
import com.jairosousa.cursomc.security.UserSS;
import com.jairosousa.cursomc.services.exceptios.AuthorizationException;
import com.jairosousa.cursomc.services.exceptios.DataIntegrityException;
import com.jairosousa.cursomc.services.exceptios.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private BCryptPasswordEncoder bcryipt;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private S3Service s3service;
	
	@Autowired
	private ImageService imageservice;
	
	@Value("${img.prefix.client.profile}")
	private String prefix;
	
	@Value("${img.profile.size}")
	private Integer size;

	public Cliente find(Integer id) {

		UserSS user = UserService.authenticated();

		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso Negado");
		}

		Cliente obj = clienteRepository.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", tipo: " + Cliente.class.getName());
		}
		return obj;
	}

	public Cliente insert(Cliente obj) {
		obj.setId(null);
		clienteRepository.save(obj);
		enderecoRepository.save(obj.getEnderecos());
		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return clienteRepository.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			clienteRepository.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir um cliente porque há pedidos relacionados");
		}
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
	
	public Cliente findByEmail(String email) {
		UserSS user = UserService.authenticated();
		
		if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso Negado");
		}

		Cliente obj = clienteRepository.findOne(user.getId());
		
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + user.getId() + ", tipo: " + Cliente.class.getName());
		}
		return obj;
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
	}

	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(),
				TipoCliente.toEnum(objDTO.getTipo()), bcryipt.encode(objDTO.getSenha()));

		Cidade cid = cidadeRepository.findOne(objDTO.getCidadeId());

		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(),
				objDTO.getBairro(), objDTO.getCep(), cli, cid);

		cli.getEnderecos().add(end);

		cli.getTelefones().add(objDTO.getTelefone1());

		if (objDTO.getTelefone2() != null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}

		if (objDTO.getTelefone3() != null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}

		return cli;
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	public URI uplodProfilePicture(MultipartFile multipartFile) {
		
		UserSS user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acesso Negado");
		}
		
		BufferedImage jpgImage = imageservice.getJpgImageFromFile(multipartFile);
		jpgImage = imageservice.cropSquare(jpgImage);
		jpgImage = imageservice.resize(jpgImage, size);
		
		String fileName = prefix + user.getId() + ".jpg";
		
		return s3service.uploadFile(imageservice.getInputStream(jpgImage, "jpg"), fileName, "image");
	}
}
