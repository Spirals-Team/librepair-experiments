package br.com.rodolfo.pontointeligente.api.controllers;

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

import br.com.rodolfo.pontointeligente.api.dtos.CadastroPJDto;
import br.com.rodolfo.pontointeligente.api.entities.Empresa;
import br.com.rodolfo.pontointeligente.api.entities.Funcionario;
import br.com.rodolfo.pontointeligente.api.enums.PerfilEnum;
import br.com.rodolfo.pontointeligente.api.response.Response;
import br.com.rodolfo.pontointeligente.api.services.EmpresaService;
import br.com.rodolfo.pontointeligente.api.services.FuncionarioService;
import br.com.rodolfo.pontointeligente.api.utils.Passwordutils;

/**
 * CadastroPJController
 */
@RestController
@RequestMapping("api/cadastrar-pj")
@CrossOrigin(origins = "*")
public class CadastroPJController {

    private static final Logger log = LoggerFactory.getLogger(CadastroPJController.class);
    
    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private EmpresaService empresaService;

    public CadastroPJController() {}

    /**
     * Método que persiste os dados no banco via POST
     * 
     * @param cadastroPJDto
     * @param resul
     * @return ResponseEntity<Response<CadastroPJDto>>
     * @throws NoSuchAlgorithmException
     */
    @PostMapping
    public ResponseEntity<Response<CadastroPJDto>> cadastrar(@Valid @RequestBody CadastroPJDto cadastroPJDto, 
                                                             BindingResult result) throws NoSuchAlgorithmException {
                                                        
        log.info("Cadastrando PJ: {}", cadastroPJDto);

        Response<CadastroPJDto> response = new Response<CadastroPJDto>();
        
        this.validarDadosExistentes(cadastroPJDto, result);
        Empresa empresa = this.converterDtoParaEmpresa(cadastroPJDto);
        Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPJDto, result);

        if(result.hasErrors()) {

            log.info("Erro validando dados de cadastro PJ: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.empresaService.persistir(empresa);
        funcionario.setEmpresa(empresa);
        this.funcionarioService.persistir(funcionario);

        response.setData(this.converterCadastroPJDto(funcionario));

        return ResponseEntity.ok(response);
    }

    /**
     * Popula o objeto DTO com os dados da empresa e do funcionario
     * 
     * @param funcionario
     * @return cadastroPJDto
     */
    private CadastroPJDto converterCadastroPJDto(Funcionario funcionario) {
        
        CadastroPJDto cadastroPJDto = new CadastroPJDto();
        Empresa empresa = funcionario.getEmpresa();

        cadastroPJDto.setId(funcionario.getId());
        cadastroPJDto.setNome(funcionario.getNome());
        cadastroPJDto.setEmail(funcionario.getEmail());
        cadastroPJDto.setCpf(funcionario.getCpf());
        cadastroPJDto.setCnpj(empresa.getCnpj());
        cadastroPJDto.setRazaoSocial(empresa.getRazaoSocial());
        
        return cadastroPJDto;
	}

	/**
     * Converte os dados DTO para Funcionario.
     * 
     * @param cadastroPJDto
     * @return Funcionario
     * @throws NoSuchAlgorithmException
     */
    private Funcionario converterDtoParaFuncionario(CadastroPJDto cadastroPJDto, BindingResult result) {
        
        Funcionario funcionario = new Funcionario();
        
        funcionario.setNome(cadastroPJDto.getNome());
        funcionario.setEmail(cadastroPJDto.getEmail());
        funcionario.setCpf(cadastroPJDto.getCpf());
        funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
        funcionario.setSenha(Passwordutils.gerarBCrypt(cadastroPJDto.getSenha()));
        
        return funcionario;
	}

	/**
     * Converte os dados DTO para Empresa.
     * 
     * @param cadastroPJDto
     */
    private Empresa converterDtoParaEmpresa(CadastroPJDto cadastroPJDto) {
        
        Empresa empresa = new Empresa();
        
        empresa.setCnpj(cadastroPJDto.getCnpj());
        empresa.setRazaoSocial(cadastroPJDto.getRazaoSocial());

        return empresa;
	}

	/**
     * Método que verifica se uma empresa ou um funcionário já existem no banco de dados. (Método validador para evitar duplicidade)
     * 
     * @param cadastroPJDto
     * @param result
     */
    private void validarDadosExistentes(CadastroPJDto cadastroPJDto, 
                                        BindingResult result) {
        
        this.empresaService.BuscarPorCnpj(cadastroPJDto.getCnpj())
                .ifPresent(empresa -> result.addError(new ObjectError("empresa", "Empresa já existnte")));
        
        this.funcionarioService.buscarPorCpf(cadastroPJDto.getCpf())
                .ifPresent(funcionario -> result.addError(new ObjectError("funcionario", "CPF já existente")));
        
        this.funcionarioService.buscarPorEmail(cadastroPJDto.getEmail())
                .ifPresent(funcionario -> result.addError(new ObjectError("funcionario", "Email já existente")));
	}

}