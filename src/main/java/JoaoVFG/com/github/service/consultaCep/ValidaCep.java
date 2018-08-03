package JoaoVFG.com.github.service.consultaCep;

/**
 * Entidade baseada nos dados do WS do viacep.com
 * Criada a baseado no código de Ulisses Ricardo de Souza Silva
 * https://github.com/uliss3s/ceputil
 */

public class ValidaCep {

	 public static boolean validaCep(String cep) {
	        if (!cep.matches("\\d{8}")) {
	            return false;
	        }

	        return true;
	    }
	 
}
