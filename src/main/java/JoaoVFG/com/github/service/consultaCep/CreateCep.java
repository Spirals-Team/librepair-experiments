package JoaoVFG.com.github.service.consultaCep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JoaoVFG.com.github.entity.Cep;
import JoaoVFG.com.github.entity.Cidade;
import JoaoVFG.com.github.entity.Estado;
import JoaoVFG.com.github.service.CepService;
import JoaoVFG.com.github.service.CidadeService;
import JoaoVFG.com.github.service.EstadoService;
import JoaoVFG.com.github.services.exception.ObjectNotFoundException;

/**
 * Entidade baseada nos dados do WS do viacep.com Criada a baseado no código de
 * Ulisses Ricardo de Souza Silva https://github.com/uliss3s/ceputil
 */

@Service
public class CreateCep {

	@Autowired(required = true)
	CepService cepService;

	@Autowired(required = true)
	CidadeService cidadeService;

	@Autowired(required = true)
	EstadoService estadoService;

	public Cep generateCep(String cepConsulta) {
		ConsultaViaCep consultaViaCep = new ConsultaViaCep();

		EnderecoConsulta enderecoConsulta = consultaViaCep.consultaCep(cepConsulta.replace("-", ""));

		if (enderecoConsulta == null) {
			throw new ObjectNotFoundException("CEP INVALIDO E NÃO ENCONTRADO: " + cepConsulta);
		}

		Estado estado = estadoService.findBySiglaAux(enderecoConsulta.getUf());
		Cidade cidade = findOrCreateCidade(enderecoConsulta);

		Cep cep = saveCep(enderecoConsulta, estado, cidade);

		return cep;

	}

	public Cidade findOrCreateCidade(EnderecoConsulta enderecoConsulta) {

		Cidade cidade = new Cidade();

		cidade = cidadeService.findByNomeCidadeSiglaEstadoAux(enderecoConsulta.getUf(),
				enderecoConsulta.getLocalidade());

		if (cidade == null) {
			return cidadeService.createCidade(new Cidade(null, enderecoConsulta.getLocalidade(),
					estadoService.findBySiglaAux(enderecoConsulta.getUf())));
		}

		return cidade;
	}

	public Cep saveCep(EnderecoConsulta EnderecoConsulta, Estado estado, Cidade cidade) {

		Cep cep = new Cep(null, EnderecoConsulta.getCep(), EnderecoConsulta.getLogradouro(),
				EnderecoConsulta.getBairro(), cidade);

		cep = cepService.createCep(cep);

		return cep;
	}



}
