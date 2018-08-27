package JoaoVFG.com.github.service.route;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import JoaoVFG.com.github.dto.request.EnderecoEntregaDTO;
import JoaoVFG.com.github.dto.response.ResponsavelRegiaoDTO;
import JoaoVFG.com.github.dto.response.RotaResponseDTO;
import JoaoVFG.com.github.entity.Empresa;
import JoaoVFG.com.github.entity.Endereco;
import JoaoVFG.com.github.entity.Funcionario;
import JoaoVFG.com.github.entity.Pessoa;
import JoaoVFG.com.github.entity.Regiao;
import JoaoVFG.com.github.repositories.FuncionarioRepository;
import JoaoVFG.com.github.service.CepService;
import JoaoVFG.com.github.service.EmpresaService;
import JoaoVFG.com.github.service.EnderecoService;
import JoaoVFG.com.github.service.RegiaoService;

@Service
public class GeraRota {

	@Autowired
	private CalculaDistancia calculaDistancia;

	@Autowired
	private CepService cepService;

	@Autowired
	private EnderecoService enderecoService;

	@Autowired
	private RegiaoService regiaoService;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private EmpresaService empresaService;

	public RotaResponseDTO geraRota(Pessoa pessoa, List<EnderecoEntregaDTO> enderecoEntregaDTOs) {
		// objeto de retorno - INICIALZIAÇÃO
		RotaResponseDTO rotaResponseDTO = new RotaResponseDTO();
		
		// objeto de Funcionario - INICIALZIAÇÃO
		Funcionario funcionario = funcionarioRepository.buscaPorId(pessoa.getId());
		
		// objeto de Empresa - INICIALZIAÇÃO
		Empresa empresa = new Empresa();
		
		// objeto de Endereco - INICIALZIAÇÃO
		Endereco endereco = new Endereco();
		
		if(!(funcionario == null)) {
			empresa = empresaService.findById(funcionario.getEmpresa().getId());
		}
		

		if (!(empresa == null)) {

			// Lista para os Ceps que a empresa não atende - INICIALZIAÇÃO
			List<ResponsavelRegiaoDTO> listResponsavelRegiao = new ArrayList<>();

			// Regiao de atuação de empresa
			Regiao regiao = regiaoService.findByEmpresa(empresa.getId());

			// Regiões das empresas parceiras
			List<Regiao> regioesBusca = regiaoService.findByEmpresaMatriz(empresa.getEmpresaMatrizId());

			// verifica se a empresa tem região de atuação
			if (!regiao.equals(null)) {

				// Se tiver uma regiao de atuação, ira iterar pela lista dos endereços
				// pra verficar se ela faz parte da sua area de atuação
				for (int i = 0; i < enderecoEntregaDTOs.size(); i++) {// (EnderecoEntregaDTO e : iterator) {

					// verifica se o endereço de entrega esta na lista de atuação
					if (!regiao.getCeps().contains(cepService.findByCep(enderecoEntregaDTOs.get(i).getCep()))) {// caso
																												// não
																												// esteja

						// busca se tem uma empresa da mesma cadeia que entregue nesse cep

						for (Regiao r : regioesBusca) {

							if (r.getCeps().contains(cepService.findByCep(enderecoEntregaDTOs.get(i).getCep()))) {
								listResponsavelRegiao.add(new ResponsavelRegiaoDTO(enderecoEntregaDTOs.get(i).getCep(),
										r.getEmpresa().getPessoa().getRazaoSocial()));
								break;
							}
						}

						// remove endereço da lista a ser roteirizada
						enderecoEntregaDTOs.remove(i);

					}
				}
			}
			
			endereco = enderecoService.findByPessoa(empresa.getPessoa().getId());
			rotaResponseDTO.setResponsavel(listResponsavelRegiao);
		}else {
			endereco = enderecoService.findByPessoa(pessoa.getId());
		}
		
		
		// Lista não roteirizada -> ja exclusos os endereços que a empresa não entrega
		List<String> listaEnderecosStringNaoRoteirizado = enderecoClienteDtoToString(enderecoEntregaDTOs);

		String enderecoPartida = cepService.cepToStringEndereco(endereco.getCep().getCep(),
				endereco.getNumeroLogradouro().toString());
		String enderecoEmpresa = enderecoPartida;

		List<String> listaRoteirizada = new ArrayList<String>();

		while (!listaEnderecosStringNaoRoteirizado.isEmpty()) {
			System.out.println(enderecoPartida);
			// recebe o ponto mais proximo de enderecoString
			enderecoPartida = calculaDistancia.findMenorDistancia(enderecoPartida, listaEnderecosStringNaoRoteirizado);
			listaRoteirizada.add(enderecoPartida);
			listaEnderecosStringNaoRoteirizado.remove(enderecoPartida);
		}

		System.out.println(listaRoteirizada);

		// return geraUrlMaps(listaRoteirizada, enderecoEmpresa);
		rotaResponseDTO.setRota(geraUrlMaps(listaRoteirizada, enderecoEmpresa));
		return rotaResponseDTO;
	}

	public String geraUrlMaps(List<String> listaRota, String enderecoEmpresa) {

		StringBuilder builder = new StringBuilder();

		String sufixo = "https://www.google.com/maps/dir/?api=1";
		builder.append(sufixo);

		try {

			builder.append("&origin=");
			builder.append(URLEncoder.encode(enderecoEmpresa, "UTF-8"));
			builder.append("&destination=");
			builder.append(URLEncoder.encode(enderecoEmpresa, "UTF-8"));
			builder.append("&waypoints=");
			for (String e : listaRota) {
				builder.append(URLEncoder.encode(e, "UTF-8"));
				builder.append("|");
			}

			builder.append("&travelmode=driving");
		} catch (UnsupportedEncodingException e1) {
			return null;
		}

		System.out.println(builder.toString());
		return builder.toString();

	}

	public List<String> enderecoClienteDtoToString(List<EnderecoEntregaDTO> enderecos) {
		List<String> enderecosString = new ArrayList<String>();

		for (EnderecoEntregaDTO e : enderecos) {
			enderecosString.add(cepService.cepToStringEndereco(e.getCep(), e.getNumeroLogradouro()));
		}

		return enderecosString;
	}

}
