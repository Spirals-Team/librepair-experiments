package br.com.reclameaqui.teste.enums;

import org.springframework.data.mongodb.core.aggregation.MatchOperation;

import br.com.reclameaqui.teste.dtos.ReclamantesRequestDTO;

public interface TipoConsultaInterface {
	
	 public MatchOperation matchOperation(ReclamantesRequestDTO request);

}
