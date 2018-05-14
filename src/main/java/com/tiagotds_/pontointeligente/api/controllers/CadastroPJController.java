package com.tiagotds_.pontointeligente.api.controllers;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiagotds_.pontointeligente.api.dtos.CadastroPJDto;
import com.tiagotds_.pontointeligente.api.entities.Empresa;
import com.tiagotds_.pontointeligente.api.entities.Funcionario;
import com.tiagotds_.pontointeligente.api.response.Response;
import com.tiagotds_.pontointeligente.api.security.enums.PerfilEnum;
import com.tiagotds_.pontointeligente.api.services.EmpresaService;
import com.tiagotds_.pontointeligente.api.services.FuncionarioService;
import com.tiagotds_.pontointeligente.api.utils.PasswordUtils;

@RestController
@RequestMapping("/api/pj")
@CrossOrigin(origins = "*")
public class CadastroPJController {

	private static final Logger log = LoggerFactory.getLogger(CadastroPJController.class);

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private EmpresaService empresaService;

	public CadastroPJController() {

	}

	@PostMapping
	public ResponseEntity<Response<CadastroPJDto>> cadastrar(@Valid @RequestBody CadastroPJDto cadastro,
			BindingResult result) throws NoSuchAlgorithmException {

		log.info("Cadastrando PJ: {}", cadastro.toString());
		Response<CadastroPJDto> response = new Response<CadastroPJDto>();

		validarDadosExistentes(cadastro, result);
		Empresa empresa = this.converterDtoParaEmpresa(cadastro);
		Funcionario funcionario = this.converterDtoParaFuncionario(cadastro, result);

		if (result.hasErrors()) {
			log.error("Erro validando dados de cadastro PJ: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		this.empresaService.persistir(empresa);
		funcionario.setEmpresa(empresa);
		this.funcionarioService.persistir(funcionario);

		response.setData(this.converterCadastroPJDto(funcionario));

		return ResponseEntity.ok(response);
	}

	private void validarDadosExistentes(CadastroPJDto cadastro, BindingResult result) {
		this.empresaService.buscarPorCnpj(cadastro.getCnpj())
				.ifPresent(emp -> result.addError(new ObjectError("empresa", "Empresa já existe")));
		this.funcionarioService.buscarPorCpf(cadastro.getCpf())
				.ifPresent(func -> result.addError(new ObjectError("funcionario", "Funcionário já existe")));
		this.funcionarioService.buscarPorEmail(cadastro.getEmail())
				.ifPresent(func -> result.addError(new ObjectError("funcionario", "E-mail já existe")));
	}

	private Empresa converterDtoParaEmpresa(CadastroPJDto cadastro) {
		Empresa empresa = new Empresa();
		empresa.setCnpj(cadastro.getCnpj());
		empresa.setRazaoSocial(cadastro.getRazaoSocial());
		return empresa;
	}

	private Funcionario converterDtoParaFuncionario(CadastroPJDto cadastro, BindingResult result)
			throws NoSuchAlgorithmException {
		Funcionario funcionario = new Funcionario();

		funcionario.setNome(cadastro.getNome());
		funcionario.setCpf(cadastro.getCpf());
		funcionario.setEmail(cadastro.getEmail());
		funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
		funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastro.getSenha()));

		return funcionario;
	}
	
	private CadastroPJDto converterCadastroPJDto(Funcionario funcionario) {
		CadastroPJDto cadastro = new CadastroPJDto();
		
		cadastro.setId(funcionario.getId());
		cadastro.setNome(funcionario.getNome());
		cadastro.setEmail(funcionario.getEmail());
		cadastro.setCpf(funcionario.getCpf());
		cadastro.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
		cadastro.setCnpj(funcionario.getEmpresa().getCnpj());
		
		return cadastro;
	}

}
