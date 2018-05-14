package com.tiagotds_.pontointeligente.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

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

import com.tiagotds_.pontointeligente.api.dtos.CadastroPFDto;
import com.tiagotds_.pontointeligente.api.entities.Empresa;
import com.tiagotds_.pontointeligente.api.entities.Funcionario;
import com.tiagotds_.pontointeligente.api.response.Response;
import com.tiagotds_.pontointeligente.api.security.enums.PerfilEnum;
import com.tiagotds_.pontointeligente.api.services.EmpresaService;
import com.tiagotds_.pontointeligente.api.services.FuncionarioService;
import com.tiagotds_.pontointeligente.api.utils.PasswordUtils;

@RestController
@RequestMapping("/api/pf")
@CrossOrigin(origins = "*")
public class CadastroPFController {

	private static final Logger log = LoggerFactory.getLogger(CadastroPFController.class);

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private EmpresaService empresaService;

	public CadastroPFController() {

	}

	@PostMapping
	public ResponseEntity<Response<CadastroPFDto>> cadastrar(@Valid @RequestBody CadastroPFDto cadastro,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Cadastrando PF: {}", cadastro.toString());

		Response<CadastroPFDto> response = new Response<CadastroPFDto>();

		validarDadosExistentes(cadastro, result);
		Funcionario funcionario = this.converterDtoParaFuncionario(cadastro, result);

		if (result.hasErrors()) {
			log.error("Erro validando dados do cadastro de PF: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(cadastro.getCnpj());
		empresa.ifPresent(emp -> funcionario.setEmpresa(emp));
		this.funcionarioService.persistir(funcionario);

		response.setData(this.converterCadastroPFDto(funcionario));
		return ResponseEntity.ok(response);

	}

	private void validarDadosExistentes(CadastroPFDto cadastro, BindingResult result) {
		Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(cadastro.getCnpj());
		if (!empresa.isPresent()) {
			result.addError(new ObjectError("empresa", "Empresa não cadastrada"));
		}

		this.funcionarioService.buscarPorCpf(cadastro.getCpf())
				.ifPresent(func -> result.addError(new ObjectError("funcionario", "Funcionário já existe")));

		this.funcionarioService.buscarPorEmail(cadastro.getEmail())
				.ifPresent(func -> result.addError(new ObjectError("funcionario", "Email já existe")));

	}

	private Funcionario converterDtoParaFuncionario(CadastroPFDto cadastro, BindingResult result)
			throws NoSuchAlgorithmException {
		Funcionario funcionario = new Funcionario();

		funcionario.setNome(cadastro.getNome());
		funcionario.setCpf(cadastro.getCpf());
		funcionario.setEmail(cadastro.getEmail());
		funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastro.getSenha()));
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		cadastro.getQtdHorasAlmoco()
				.ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));
		cadastro.getQtdHorasTrabalhoDia().ifPresent(
				qtdHorasTrabalhoDia -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabalhoDia)));
		cadastro.getValorHora().ifPresent(valorHora -> funcionario.setValorHora(Double.valueOf(valorHora)));

		return funcionario;
	}

	private CadastroPFDto converterCadastroPFDto(Funcionario funcionario) {
		CadastroPFDto cadastro = new CadastroPFDto();

		cadastro.setId(funcionario.getId());
		cadastro.setNome(funcionario.getNome());
		cadastro.setCpf(funcionario.getCpf());
		cadastro.setEmail(funcionario.getEmail());
		cadastro.setCnpj(funcionario.getEmpresa().getCnpj());
		funcionario.getQtdHorasAlmocoOpt()
				.ifPresent(qtdHorasAlmoco -> cadastro.setQtdHorasAlmoco(Optional.of(Float.toString(qtdHorasAlmoco))));
		funcionario.getQtdHorasTrabalhoDiaOpt().ifPresent(qtdHorasTrabalhoDia -> cadastro
				.setQtdHorasTrabalhoDia(Optional.of(Float.toString(qtdHorasTrabalhoDia))));
		funcionario.getValorHoraOpt()
				.ifPresent(valorHora -> cadastro.setValorHora(Optional.of(Double.toString(valorHora))));

		return cadastro;
	}
}
