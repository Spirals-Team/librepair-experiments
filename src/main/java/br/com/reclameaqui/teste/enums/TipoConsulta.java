package br.com.reclameaqui.teste.enums;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.query.Criteria.where;

import org.springframework.data.mongodb.core.aggregation.MatchOperation;

import br.com.reclameaqui.teste.dtos.ReclamantesRequestDTO;

public enum TipoConsulta implements TipoConsultaInterface {

	EMPRESA {
		@Override
		public MatchOperation matchOperation(ReclamantesRequestDTO request) {
			return match(where("empresa.nome").is(request.getEmpresa()));
		}
	},
	CIDADE {
		@Override
		public MatchOperation matchOperation(ReclamantesRequestDTO request) {
			return match(where("endereco.cidade").is(request.getCidade()));
		}
	},
	EMPRESAECIDADE {
		@Override
		public MatchOperation matchOperation(ReclamantesRequestDTO request) {
			return match(where("endereco.cidade").is(request.getCidade()).and("empresa.nome").is(request.getEmpresa()));
		}
	};

}
