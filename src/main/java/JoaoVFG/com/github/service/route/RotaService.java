package JoaoVFG.com.github.service.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JoaoVFG.com.github.dto.request.ListaEnderecoRotaDTO;
import JoaoVFG.com.github.dto.response.RotaResponseDTO;
import JoaoVFG.com.github.entity.Empresa;
import JoaoVFG.com.github.service.EmpresaService;



@Service
public class RotaService {

	@Autowired
	GeraRota geraRota;
	
	@Autowired
	EmpresaService empresaService;

	public RotaResponseDTO geraRotaRespose(ListaEnderecoRotaDTO listaEnderecoRotaDTO) {
		Empresa empresa = empresaService.findById(listaEnderecoRotaDTO.getIdEmpresa());
		RotaResponseDTO rotaResponseDTO = geraRota.geraRota(empresa, listaEnderecoRotaDTO.getWaypoints());
		
		return rotaResponseDTO;	
	}
}
