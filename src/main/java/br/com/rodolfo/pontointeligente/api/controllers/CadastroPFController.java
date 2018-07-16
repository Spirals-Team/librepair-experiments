package br.com.rodolfo.pontointeligente.api.controllers;

import java.math.BigDecimal;
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

import br.com.rodolfo.pontointeligente.api.dtos.CadastroPFDto;
import br.com.rodolfo.pontointeligente.api.entities.Empresa;
import br.com.rodolfo.pontointeligente.api.entities.Funcionario;
import br.com.rodolfo.pontointeligente.api.enums.PerfilEnum;
import br.com.rodolfo.pontointeligente.api.response.Response;
import br.com.rodolfo.pontointeligente.api.services.EmpresaService;
import br.com.rodolfo.pontointeligente.api.services.FuncionarioService;
import br.com.rodolfo.pontointeligente.api.utils.Passwordutils;

/**
 * CadastroPFController
 */
@RestController
@RequestMapping("api/cadastrar-pf")
@CrossOrigin(origins = "*")
public class CadastroPFController {

    private static final Logger log = LoggerFactory.getLogger(CadastroPFController.class);

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private EmpresaService empresaService;
    
    public CadastroPFController() {}

    /**
     * Cadastra um funcionário pessoa física no sistema
     * 
     * @param cadastroPFDto
     * @param result
     * @return ResponseEntity<Response<CadastroPFDto>>
     * @throws NoSuchAlgorithmException
     */
    @PostMapping
    public ResponseEntity<Response<CadastroPFDto>> cadastrar(@Valid @RequestBody CadastroPFDto cadastroPFDto,
                                                             BindingResult result) throws NoSuchAlgorithmException {

        log.info("Cadastrando PF: {}", cadastroPFDto);

        Response<CadastroPFDto> response = new Response<CadastroPFDto>();

        this.validarDadosExistentes(cadastroPFDto, result);
        Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPFDto, result);

        if(result.hasErrors()) {

            log.info("Erro validando dados de cadastro PF: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));

            return ResponseEntity.badRequest().body(response);
        }

        Optional<Empresa> empresa = this.empresaService.BuscarPorCnpj(cadastroPFDto.getCnpj());
        empresa.ifPresent(emp -> funcionario.setEmpresa(emp));

        this.funcionarioService.persistir(funcionario);

        response.setData(this.converterCadastroPFDto(funcionario));

        return ResponseEntity.ok(response);
    }

    /**
     * Popula o DTO de cadastro com os dados do funcionário e empresa
     * 
     * @param funcionario
     * @return CadastroPFDto
     */
    private CadastroPFDto converterCadastroPFDto(Funcionario funcionario) {
        
        CadastroPFDto cadastroPFDto = new CadastroPFDto();

        Empresa empresa = funcionario.getEmpresa();

        cadastroPFDto.setId(funcionario.getId());
        cadastroPFDto.setNome(funcionario.getNome());
        cadastroPFDto.setEmail(funcionario.getEmail());
        cadastroPFDto.setCpf(funcionario.getCpf());
        cadastroPFDto.setCnpj(empresa.getCnpj());
        funcionario.getQtdHorasTrabalhoDiaOpt().ifPresent(qtdHora -> 
                        cadastroPFDto.setQtdHoraTrabalhoDia(Optional.of(Float.toString(qtdHora))));
        funcionario.getQtdHorasAlmocoOpt().ifPresent(qtdHora -> 
                        cadastroPFDto.setQtdHoraAlmoco(Optional.of(Float.toString(qtdHora))));
        funcionario.getValorHoraOpt().ifPresent(valorHora -> 
                        cadastroPFDto.setValorHora(Optional.of(valorHora.toString())));
        
        return cadastroPFDto;
	}

	/**
     * Converte os dados DTO para funcionario
     * 
     * @param cadastroPFDto
     * @param result
     * @return Funcionario
     * @throws NoSuchAlgorithmException
     */
    private Funcionario converterDtoParaFuncionario(CadastroPFDto cadastroPFDto, 
                                                    BindingResult result) throws NoSuchAlgorithmException {
        
        Funcionario funcionario = new Funcionario();

        funcionario.setNome(cadastroPFDto.getNome());
        funcionario.setEmail(cadastroPFDto.getEmail());
        funcionario.setCpf(cadastroPFDto.getCpf());
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
        funcionario.setSenha(Passwordutils.gerarBCrypt(cadastroPFDto.getSenha()));
        cadastroPFDto.getQtdHoraAlmoco().ifPresent(qtdHora -> funcionario.setQtdHorasAlmoco(Float.parseFloat(qtdHora)));
        cadastroPFDto.getQtdHoraTrabalhoDia().ifPresent(qtdHora -> funcionario.setQtdHorasTrabalhoDia(Float.parseFloat(qtdHora)));
        cadastroPFDto.getValorHora().ifPresent(valor -> funcionario.setValorHora(new BigDecimal(valor)));

		return funcionario;
	}

	/**
     * Método que verifica se a empresa está cadastrada e se o funcionário não existe na base de dados
     * 
     * @param cadastroPFDto
     * @param result
     */
    private void validarDadosExistentes(CadastroPFDto cadastroPFDto, 
                                        BindingResult result) {

        Optional<Empresa> empresa = this.empresaService.BuscarPorCnpj(cadastroPFDto.getCnpj());

        if(!empresa.isPresent()) {

            result.addError(new ObjectError("empresa", "Empresa não cadastrada"));
        }

        this.funcionarioService.buscarPorCpf(cadastroPFDto.getCpf())
                .ifPresent(funcionario -> result.addError(new ObjectError("funcionario", "CPF já existente.")));
            
        this.funcionarioService.buscarPorEmail(cadastroPFDto.getEmail())
                .ifPresent(funcionario -> result.addError(new ObjectError("funcionario", "Email já existente.")));

	}


}