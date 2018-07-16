package br.com.rodolfo.pontointeligente.api.controllers;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodolfo.pontointeligente.api.dtos.FuncionarioDto;
import br.com.rodolfo.pontointeligente.api.entities.Funcionario;
import br.com.rodolfo.pontointeligente.api.response.Response;
import br.com.rodolfo.pontointeligente.api.services.FuncionarioService;
import br.com.rodolfo.pontointeligente.api.utils.Passwordutils;

/**
 * FuncionarioController
 */
@RestController
@RequestMapping("api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {

    private static final Logger log = LoggerFactory.getLogger(FuncionarioController.class);
    
    @Autowired
    private FuncionarioService funcionarioService;

    public FuncionarioController() {}

    /**
     * Atualiza os dados do funcionário
     * 
     * @param id
     * @param funcionarioDto
     * @param result
     * @throws NoSuchAlgorithmException
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<FuncionarioDto>> atualizar(@PathVariable("id") Long id,
                          @Valid @RequestBody FuncionarioDto funcionarioDto,
                          BindingResult result) throws NoSuchAlgorithmException {

        log.info("Atualizando funcionário: {}", funcionarioDto);

        Response<FuncionarioDto> response = new Response<FuncionarioDto>();

        Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(id);

        if(!funcionario.isPresent()) {

            result.addError(new ObjectError("funcionario", "Funcionário não encontrado"));
        } else {

            this.atualizarDadosDoFuncionario(funcionario.get(), funcionarioDto, result);
        }

        if(result.hasErrors()) {

            log.info("Erro validando funcionário: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.funcionarioService.persistir(funcionario.get());
        response.setData(this.converterFuncionarioDto(funcionario.get()));

        return ResponseEntity.ok(response);
    }

    /**
     * Retorna um DTO com os dados de um funcionário
     * 
     * @param funcionario
     * @return FuncionarioDto
     */
    private FuncionarioDto converterFuncionarioDto(Funcionario funcionario) {
        
        FuncionarioDto funcionarioDto = new FuncionarioDto();
        
        funcionarioDto.setId(funcionario.getId());
        funcionarioDto.setNome(funcionario.getNome());
        funcionarioDto.setEmail(funcionario.getEmail());
        funcionario.getValorHoraOpt().ifPresent(valorHora -> funcionarioDto.setValorHora(Optional.of(valorHora.toString())));
        funcionario.getQtdHorasAlmocoOpt().ifPresent(qtdHora -> funcionarioDto.setQtdHorasAlmoco(Optional.of(Float.toString(qtdHora))));
        funcionario.getQtdHorasTrabalhoDiaOpt().ifPresent(qtdHora -> funcionarioDto.setQtdHorasTrabalhoDia(Optional.of(Float.toString(qtdHora))));

        return funcionarioDto;
	}

	/**
     * Atualizar os dados do funcionáro baseado nos dados do DTO
     * 
     * @param funcionario
     * @param funcionarioDto
     * @param result
     * @throws NoSuchAlgorithmException
     */
	private void atualizarDadosDoFuncionario(Funcionario funcionario, FuncionarioDto funcionarioDto,
			BindingResult result) throws NoSuchAlgorithmException {

        funcionarioDto.getSenha().ifPresent(senha -> funcionario.setSenha(Passwordutils.gerarBCrypt(senha)));
        funcionario.setNome(funcionarioDto.getNome());

        if(!funcionario.getEmail().equals(funcionarioDto.getEmail())) {

            this.funcionarioService.buscarPorEmail(funcionarioDto.getEmail())
                    .ifPresent(func -> result.addError(new ObjectError("email", "Email já existe")));

            funcionario.setEmail(funcionarioDto.getEmail());
        }
        
        funcionario.setValorHora(null);
        funcionarioDto.getValorHora().ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));;
    
        funcionario.setQtdHorasAlmoco(null);
        funcionarioDto.getQtdHorasAlmoco().ifPresent(qtdHora -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHora)));

        funcionario.setQtdHorasTrabalhoDia(null);
        funcionarioDto.getQtdHorasTrabalhoDia().ifPresent(qtdHora -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHora)));
	}

    
}