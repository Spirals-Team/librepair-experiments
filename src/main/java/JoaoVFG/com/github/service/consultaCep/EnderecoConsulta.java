package JoaoVFG.com.github.service.consultaCep;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidade baseada nos dados do WS do viacep.com
 * Criada a baseado no código de Ulisses Ricardo de Souza Silva
 * https://github.com/uliss3s/ceputil
 */

@Getter @Setter
@NoArgsConstructor
public class EnderecoConsulta {
	
	private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String unidade;
    private String ibge;
    private String gia;
}
