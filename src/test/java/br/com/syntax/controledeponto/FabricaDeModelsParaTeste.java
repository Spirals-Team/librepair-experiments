package br.com.syntax.controledeponto;

import br.com.syntax.controledeponto.entities.Empresa;
import br.com.syntax.controledeponto.entities.Funcionario;
import br.com.syntax.controledeponto.entities.Lancamento;
import br.com.syntax.controledeponto.enums.Perfil;
import br.com.syntax.controledeponto.enums.TipoDeControle;
import br.com.syntax.controledeponto.utils.PasswordUtils;

import java.math.BigDecimal;
import java.util.Date;

public class FabricaDeModelsParaTeste {

  public static Empresa criarEmpresa(String cnpj) {
    Empresa empresa = new Empresa("Syntax Tech", cnpj);
    return empresa;
  }

  public static Funcionario criarFuncionario(String cpf, String email, Empresa empresa) {
    Funcionario funcionario = new Funcionario();
    funcionario.setNome("Marcos Paulo");
    funcionario.setCpf(cpf);
    funcionario.setEmail(email);
    funcionario.setSenha( PasswordUtils.geraBCrypt("123456") );
    funcionario.setPerfil(Perfil.ADMIN);
    funcionario.setValorHora( new BigDecimal(13.00));
    funcionario.setQuantidadeHorasTrabalhadasDia(8);
    funcionario.setQuantidadeHorasAlmoco(2);
    funcionario.setEmpresa(empresa);
    return funcionario;
  }

  public static Lancamento criarLancamento(Funcionario funcionario, TipoDeControle tipoDeControle) {
    Lancamento lancamento = new Lancamento(funcionario);
    lancamento.setData( new Date() );
    lancamento.setTipo(tipoDeControle);
    lancamento.setDescricao("Registro de ponto");
    lancamento.setLocalizacao("Interno");
    return lancamento;
  }

}
