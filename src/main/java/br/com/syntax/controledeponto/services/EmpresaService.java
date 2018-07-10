package br.com.syntax.controledeponto.services;

import br.com.syntax.controledeponto.entities.Empresa;

import java.util.Optional;

public interface EmpresaService {

  /**
  * Retorna a empresa que possui o cnpj fornecido
  *
  * @param cnpj
  * @return empresa
  */
  Optional<Empresa> buscaPorCnpj(String cnpj);

  /**
  * Salva a empresa fornecida no banco de dados e retorna os dados da empresa que foi salva
  *
  * @param empresa
  * @return empresa
  */
  Optional<Empresa> salva(Empresa empresa);
}
