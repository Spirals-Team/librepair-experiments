package com.leonardo.pontointeligente.api.services;

import java.util.Optional;

import com.leonardo.pontointeligente.api.entities.Empresa;

public interface EmpresaService {

	Optional<Empresa> buscarPorCnpj(String cnpj);

	Empresa persistir(Empresa empresa);

}
