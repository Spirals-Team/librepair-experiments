package br.com.reclameaqui.teste;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import br.com.reclameaqui.teste.documents.Cliente;
import br.com.reclameaqui.teste.documents.Empresa;
import br.com.reclameaqui.teste.documents.Endereco;
import br.com.reclameaqui.teste.documents.Reclamacao;
import br.com.reclameaqui.teste.repository.ClienteRepository;
import br.com.reclameaqui.teste.repository.ReclamacaoRepository;
import br.com.reclameaqui.teste.utils.ClearRepositories;

@SpringBootApplication
@EnableCaching
public class TesteApplication implements CommandLineRunner{

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ReclamacaoRepository reclamacaoRepository;
	
	@Autowired
	private ClearRepositories clearRepositories;

	@Override
	public void run(String... arg0) throws Exception {
		clearRepositories.clear();

		Endereco ruaAlamedaSaoPaulo = new Endereco("06454050", "Alameda Grajau", "554", "Alphaville Industrial", "Barueri");
		Endereco ruaOliveiraSaoPaulo = new Endereco("09420350", "Rua Oliveira", "7", "Jardim Paulista", "São Paulo");
		Endereco ruaDomingoSaoPaulo = new Endereco("09420350", "Rua Domingo", "42", "Jardim Paulista", "São Paulo");
		Endereco ruaCorinthiansSaoPaulo = new Endereco("09420350", "Rua Corinthians", "12", "Jardim Primavera", "São Paulo");
		Endereco ruaPalmeirasSaoPaulo = new Endereco("09420350", "Rua Palmeiras", "2865", "Jardim Euclides", "São Paulo");
		Endereco ruaBotafogoRioDeJaneiro = new Endereco("09420350", "Rua Botafogo", "43", "Presidente", "Rio de Janeiro");
		Endereco ruaVascoRioDeJaneiro = new Endereco("09420350", "Rua Vasco", "22", "Amoreira", "Rio de Janeiro");
		Endereco ruaFlamengoRioDeJaneiro = new Endereco("09420350", "Rua Flamengo", "5", "Amoreira", "Rio de Janeiro");
		
		
		Cliente matheus = new Cliente("Matheus", "11981168076", "82345352698", ruaAlamedaSaoPaulo);
		Cliente pedro = new Cliente("Pedro", "1945760423", "38972734896", ruaOliveiraSaoPaulo);
		Cliente renata = new Cliente("Renata", "1123249823", "65288783829", ruaDomingoSaoPaulo);
		Cliente igor = new Cliente("Igor", "1198359734", "45163846639", ruaCorinthiansSaoPaulo);
		Cliente jose = new Cliente("Jose", "1943925493", "54510749252", ruaPalmeirasSaoPaulo);
		Cliente ronaldo = new Cliente("Ronaldo", "1123549890", "33686558840", ruaDomingoSaoPaulo);
		Cliente andre = new Cliente("Andre", "1193452454", "66642822838", ruaBotafogoRioDeJaneiro);
		Cliente cristiane = new Cliente("Cristiane", "1948392454", "98938031101", ruaVascoRioDeJaneiro);
		Cliente paula = new Cliente("Paula", "1123249823", "40248687727", ruaFlamengoRioDeJaneiro);
		
		List<Cliente> listClientes = Arrays.asList(matheus, pedro, renata, igor, jose, ronaldo, andre, cristiane, paula);
		
		this.clienteRepository.save(listClientes);
		
		Empresa netflix = new Empresa("06.145.892/0001-03", "Netflix");
		Empresa vivo = new Empresa("48.582.342/0001-93", "Vivo");
		Empresa samsung = new Empresa("80.276.244/0001-30", "Samsung");
		Empresa net = new Empresa("12.760.939/0001-33", "NET");
		
		//Netflix
		Reclamacao reclamacaoNetflixMatheus = new Reclamacao("O Netflix parou de funcionar", "Não consigo assistir minhas series", ruaAlamedaSaoPaulo, netflix, matheus);
		Reclamacao reclamacaoNetflixAndre = new Reclamacao("O meu plano nao está 4k", "Não consigo assistir filmes em 4K", ruaBotafogoRioDeJaneiro, netflix, andre);
		Reclamacao reclamacaoNetflixPaula = new Reclamacao("O Netflix cobrou a mais", "Cobrança indevida", ruaFlamengoRioDeJaneiro, netflix, paula);

		//Vivo
		Reclamacao reclamacaoVivoPedro = new Reclamacao("Minha internet cai muito", "Minha internet não para de cair e o sinal está muito ruim", ruaOliveiraSaoPaulo, vivo, pedro);
		Reclamacao reclamacaoVivoRonaldo = new Reclamacao("Minha internet nao funciona direito", "Meu plano acabou e os 50Mb do basico não funciona", ruaAlamedaSaoPaulo, vivo, ronaldo);

		//Samsung
		Reclamacao reclamacaoSamsungMatheus = new Reclamacao("Tv com imagem ruim", "Imagem muito ruim", ruaAlamedaSaoPaulo, samsung, matheus);
		Reclamacao reclamacaoSamsungIgor = new Reclamacao("A TV pifou", "Comprei uma tv e ela parou de funcionar no segundo dia", ruaCorinthiansSaoPaulo, samsung, igor);
		Reclamacao reclamacaoSamsungJose = new Reclamacao("TV com imagem ruim", "Não esperava por uma imagem tao ruim", ruaPalmeirasSaoPaulo, samsung, jose);
		Reclamacao reclamacaoSamsungCristiane = new Reclamacao("Tv com botoes fundos", "Os botoes da tv afundaram", ruaVascoRioDeJaneiro, samsung, cristiane);
		Reclamacao reclamacaoSamsungRonaldo = new Reclamacao("Sem controle", "Comprei a tv e não veio o controle", ruaAlamedaSaoPaulo, samsung, ronaldo);
		Reclamacao reclamacaoSamsungAndre = new Reclamacao("Entrega não recebida", "Minha televisao nao chega", ruaPalmeirasSaoPaulo, samsung, andre);
		Reclamacao reclamacaoSamsungPaula = new Reclamacao("Entrega quebrada", "Meu celular chegou quebrado", ruaCorinthiansSaoPaulo, samsung, paula);
		Reclamacao reclamacaoSamsungPedro = new Reclamacao("Reclamacao novamente nao respondida", "Samsung demora para responder reclamacao", ruaDomingoSaoPaulo, samsung, pedro);
		
		//NET
		Reclamacao reclamacaoNetRenata = new Reclamacao("Cobrando mais do que o esperado ", "O plano de futevol está cobrando mais do que o esperado", ruaDomingoSaoPaulo, net, renata);
		
		List<Reclamacao> listReclamacoes = Arrays.asList(
				reclamacaoNetflixMatheus, 
				reclamacaoVivoPedro, 
				reclamacaoNetRenata, 
				reclamacaoSamsungIgor, 
				reclamacaoSamsungJose, 
				reclamacaoVivoRonaldo, 
				reclamacaoNetflixAndre, 
				reclamacaoSamsungCristiane, 
				reclamacaoNetflixPaula,
				reclamacaoSamsungRonaldo,
				reclamacaoSamsungMatheus,
				reclamacaoSamsungAndre,
				reclamacaoSamsungPaula,
				reclamacaoSamsungPedro
		);

		this.reclamacaoRepository.save(listReclamacoes);
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TesteApplication.class, args);
	}
}
