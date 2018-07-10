package br.com.syntax.controledeponto.api.controllers;


import br.com.syntax.controledeponto.api.dtos.CadastroEmpresaDto;
import br.com.syntax.controledeponto.api.response.Response;
import br.com.syntax.controledeponto.entities.Empresa;
import br.com.syntax.controledeponto.entities.Funcionario;
import br.com.syntax.controledeponto.enums.Perfil;
import br.com.syntax.controledeponto.services.EmpresaService;
import br.com.syntax.controledeponto.services.FuncionarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/empresa")
@CrossOrigin(origins = "*")
public class CadastroEmpresaController {

  private static Logger log = LoggerFactory.getLogger(CadastroEmpresaController.class);

  @Autowired private EmpresaService empresaService;
  @Autowired private FuncionarioService funcionarioService;


  /**
   * Método para gravar a empresa
   * @param parametroEmpresaDto
   * @param result
   * @return ResponseEntity< Response<CadastroEmpresaDto> >
   */
  @PostMapping
  public ResponseEntity< Response<CadastroEmpresaDto> > cadastrar(@Valid @RequestBody CadastroEmpresaDto parametroEmpresaDto, BindingResult result) {
    log.debug("Cadasttrando");
    Response<CadastroEmpresaDto> response = new Response<>();

    this.validarDados(parametroEmpresaDto, result);

    Empresa empresa = this.converterDtoParaEmpresa(parametroEmpresaDto);
    Funcionario funcionario = this.converterDtoParaFuncionario(parametroEmpresaDto);

    if (result.hasErrors()) {
      log.error("Existem erros na requisição: {}", result.getAllErrors());
      result.getAllErrors().forEach( (erro) -> response.getErros().add(erro.getDefaultMessage()) );
      return ResponseEntity.badRequest().body(response);
    }

    this.empresaService.salva(empresa);
    funcionario.setEmpresa(empresa);

    this.funcionarioService.salva(funcionario);
    response.setRetorno( this.converterParaEmpresaDto(funcionario) );
    return ResponseEntity.ok(response);
  }

  /**
   * Verifica se a empresa e o funcionário já existem na base de dados
   * @param cadastroEmpresaDto
   * @param result
   * @return  result com os erros
   */
  private void validarDados(CadastroEmpresaDto cadastroEmpresaDto, BindingResult result) {
    this.empresaService.buscaPorCnpj(cadastroEmpresaDto.getCnpj())
            .ifPresent( empresa -> result.addError(new ObjectError("Empresa", "A empresa já existe no banco de dados")) );

    this.funcionarioService.buscaPorCpf(cadastroEmpresaDto.getCpf())
            .ifPresent(funcionario -> result.addError(new ObjectError("Funcionário", "Esse CPF já foi cadastrado")) );

    this.funcionarioService.buscaPorEmail(cadastroEmpresaDto.getEmail())
            .ifPresent(funcionario -> result.addError(new ObjectError("Funcionário", "Esse email já foi cadastrado")) );
  }

  private Empresa converterDtoParaEmpresa(CadastroEmpresaDto cadastroEmpresaDto) {
    return new Empresa(cadastroEmpresaDto.getRazaoSocial(), cadastroEmpresaDto.getCnpj());
  }

  private Funcionario converterDtoParaFuncionario(CadastroEmpresaDto cadastroEmpresaDto) {
    Funcionario funcionario = new Funcionario();
    funcionario.setNome(cadastroEmpresaDto.getNome());
    funcionario.setCpf(cadastroEmpresaDto.getCpf());
    funcionario.setEmail(cadastroEmpresaDto.getEmail());
    funcionario.setSenha(cadastroEmpresaDto.getSenha());
    funcionario.setPerfil(Perfil.ADMIN);
    return funcionario;
  }

  private CadastroEmpresaDto converterParaEmpresaDto(Funcionario funcionario) {
    CadastroEmpresaDto cadastroEmpresaDto = new CadastroEmpresaDto();
    cadastroEmpresaDto.setId(funcionario.getEmpresa().getId());
    cadastroEmpresaDto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
    cadastroEmpresaDto.setCnpj(funcionario.getEmpresa().getCnpj());
    cadastroEmpresaDto.setNome(funcionario.getNome());
    cadastroEmpresaDto.setCpf(funcionario.getCpf());
    cadastroEmpresaDto.setEmail(funcionario.getEmail());
    return cadastroEmpresaDto;
  }


}
