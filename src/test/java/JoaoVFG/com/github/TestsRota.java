package JoaoVFG.com.github;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import JoaoVFG.com.github.entity.dto.EnderecoClienteDTO;
import JoaoVFG.com.github.service.route.CalculaDistancia;
import JoaoVFG.com.github.service.route.Distancia;
import JoaoVFG.com.github.service.route.GeraRota;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestsRota {
	@Autowired
	CalculaDistancia calculaDistancia;

	@Autowired
	GeraRota geraRota;

	@Test
	public void testCalcDistance() {
		Distancia dis = calculaDistancia.calcDistancia("12288560", "12285020");
		System.out.println(dis.getCepOrigem());
		System.out.println(dis.getCepDestino());
		System.out.println(dis.getTimeInSeconds());
		System.out.println(dis.getDistanciaInMeters());
		System.out.println(dis.toString());
	}

	@Test
	public void testAchouMenor() {
		String filial = "12288560";

		List<EnderecoClienteDTO> enderecoClienteDTOs = new ArrayList<EnderecoClienteDTO>();
		enderecoClienteDTOs.add(new EnderecoClienteDTO("12281350", "435"));
		enderecoClienteDTOs.add(new EnderecoClienteDTO("12285020", "1225"));
		enderecoClienteDTOs.add(new EnderecoClienteDTO("12295370", "132"));
		enderecoClienteDTOs.add(new EnderecoClienteDTO("12281460", "100"));
		enderecoClienteDTOs.add(new EnderecoClienteDTO("12288460", "100"));
		enderecoClienteDTOs.add(new EnderecoClienteDTO("12281420", "s/n"));

		geraRota.geraRota(filial, 0, enderecoClienteDTOs);
	}

}
