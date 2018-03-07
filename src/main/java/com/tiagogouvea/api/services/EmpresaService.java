package com.tiagogouvea.api.services;

import com.tiagogouvea.api.entities.Empresa;

public interface EmpresaService {
	
	Empresa findByCnpj(String cnpj);

}
