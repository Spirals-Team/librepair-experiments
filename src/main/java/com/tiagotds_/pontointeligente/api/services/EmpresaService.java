package com.tiagotds_.pontointeligente.api.services;

import java.util.Optional;

import com.tiagotds_.pontointeligente.api.entities.Empresa;

public interface EmpresaService {

	Optional<Empresa> buscarPorCnpj(String cnpj);

	Empresa persistir(Empresa empresa);
}
