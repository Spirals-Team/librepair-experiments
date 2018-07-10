package br.com.syntax.controledeponto.api.controllers;

import br.com.syntax.controledeponto.api.dtos.CadastroEmpresaDto;
import br.com.syntax.controledeponto.api.dtos.PessoaFisicaDto;
import br.com.syntax.controledeponto.api.response.Response;
import br.com.syntax.controledeponto.entities.Empresa;
import br.com.syntax.controledeponto.entities.Funcionario;
import br.com.syntax.controledeponto.enums.Perfil;
import br.com.syntax.controledeponto.services.EmpresaService;
import br.com.syntax.controledeponto.services.FuncionarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/pessoa-fisica")
@CrossOrigin(origins = "*")
public class PessoaFisicaController {
  private static Logger log = LoggerFactory.getLogger(PessoaFisicaController.class);

  @Autowired private EmpresaService empresaService;
  @Autowired private FuncionarioService funcionarioService;

  /**
   * Método para gravar a empresa
   * @param pessoaFisicaDto
   * @param result
   * @return ResponseEntity< Response<CadastroEmpresaDto> >
   */
  @PostMapping
  public ResponseEntity< Response<PessoaFisicaDto> > cadastrar(@Valid @RequestBody PessoaFisicaDto pessoaFisicaDto, BindingResult result) {
    log.debug("Cadasttrando");
    Response<PessoaFisicaDto> response = new Response<>();

    this.validarDados(pessoaFisicaDto, result);

    Funcionario funcionario = this.converterDtoParaFuncionario(pessoaFisicaDto);

    if (result.hasErrors()) {
      log.error("Existem erros na requisição: {}", result.getAllErrors());
      result.getAllErrors().forEach( (erro) -> response.getErros().add(erro.getDefaultMessage()) );
      return ResponseEntity.badRequest().body(response);
    }

    Optional<Empresa> optionalEmpresa = this.empresaService.buscaPorCnpj(pessoaFisicaDto.getCnpj());
    optionalEmpresa.ifPresent( empresa -> funcionario.setEmpresa(empresa) );
    this.funcionarioService.salva(funcionario);


    response.setRetorno( this.converterParaPessoaFisicaDto(funcionario) );
    return ResponseEntity.ok(response);
  }

  /**
   * Verifica se a empresa e o funcionário já existem na base de dados
   * @param pessoaFisicaDto
   * @param result
   * @return  result com os erros se existirem
   */
  private void validarDados(PessoaFisicaDto pessoaFisicaDto, BindingResult result) {
    if (!this.empresaService.buscaPorCnpj(pessoaFisicaDto.getCnpj()).isPresent()) {
      result.addError(new ObjectError("Empresa", "A empresa não existe no banco de dados"));
    }

    this.funcionarioService.buscaPorCpf(pessoaFisicaDto.getCpf())
            .ifPresent(funcionario -> result.addError(new ObjectError("Funcionário", "Esse CPF já foi cadastrado")) );

    this.funcionarioService.buscaPorEmail(pessoaFisicaDto.getEmail())
            .ifPresent(funcionario -> result.addError(new ObjectError("Funcionário", "Esse email já foi cadastrado")) );
  }

  private Funcionario converterDtoParaFuncionario(PessoaFisicaDto pessoaFisicaDto) {
    Funcionario funcionario = new Funcionario();
    funcionario.setNome(pessoaFisicaDto.getNome());
    funcionario.setCpf(pessoaFisicaDto.getCpf());
    funcionario.setEmail(pessoaFisicaDto.getEmail());
    funcionario.setSenha(pessoaFisicaDto.getSenha());
    funcionario.setPerfil(Perfil.USUARIO_COMUM);
    funcionario.setValorHora(pessoaFisicaDto.getValorHora());
    funcionario.setQuantidadeHorasAlmoco(pessoaFisicaDto.getQuantidadeHorasAlmoco());
    funcionario.setQuantidadeHorasTrabalhadasDia(pessoaFisicaDto.getQuantidadeHorasTrabalhadasDia());
    return funcionario;
  }

  private PessoaFisicaDto converterParaPessoaFisicaDto(Funcionario funcionario) {
    PessoaFisicaDto pessoaFisicaDto = new PessoaFisicaDto();
    pessoaFisicaDto.setId(funcionario.getId());
    pessoaFisicaDto.setNome(funcionario.getNome());
    pessoaFisicaDto.setEmail(funcionario.getEmail());
    pessoaFisicaDto.setCpf(funcionario.getCpf());
    pessoaFisicaDto.setValorHora(funcionario.getValorHora());
    pessoaFisicaDto.setQuantidadeHorasAlmoco(funcionario.getQuantidadeHorasAlmoco());
    pessoaFisicaDto.setQuantidadeHorasTrabalhadasDia(funcionario.getQuantidadeHorasTrabalhadasDia());
    pessoaFisicaDto.setCnpj(funcionario.getEmpresa().getCnpj());
    return pessoaFisicaDto;
  }

}
