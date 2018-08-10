package JoaoVFG.com.github.service.route;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JoaoVFG.com.github.entity.dto.ListaEnderecoRotaDTO;

@Service
public class RotaService {

	@Autowired
	GeraRota geraRota;

	public URL gerarUriRota(ListaEnderecoRotaDTO listaEnderecoRotaDTO) {
		String filial = listaEnderecoRotaDTO.filial;
		String urlRota = geraRota.geraRota(filial, 0, listaEnderecoRotaDTO.getWaypoints());
		URL url;
		
		try {
			url = new URL(urlRota);
		} catch (MalformedURLException e) {
			return null;
		}
		return url;	
	}
}
