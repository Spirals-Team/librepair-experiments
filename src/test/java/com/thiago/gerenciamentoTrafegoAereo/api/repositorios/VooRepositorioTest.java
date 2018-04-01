package com.thiago.gerenciamentoTrafegoAereo.api.repositorios;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Aviao;
import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Cidade;
import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Piloto;
import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Voo;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class VooRepositorioTest
{
	private Long cidadeOrigemCodigo;
	private Long cidadeDestinoCodigo;
	private Long vooCodigo;
	private String dataPartida = "28/12/2018 12:00";
	private String dataChegada = "28/12/2018 13:00";
	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	@Autowired
	private PilotoRepositorio pilotoRepositorio;
	
	@Autowired
	private AviaoRepositorio aviaoRepositorio;
	
	@Autowired	
	private CidadeRepositorio cidadeRepositorio;
	
	@Autowired
	private VooRepositorio vooRepositorio;
	
	@Before
	public void inicializar() throws Exception
	{
		Piloto piloto = this.pilotoRepositorio.save(criarPiloto());
		
		Aviao aviao = this.aviaoRepositorio.save(criarAviao());
		
		Cidade cidadeOrigem = this.cidadeRepositorio.save(criarCidadeOrigem());
		this.cidadeOrigemCodigo = cidadeOrigem.getCodigo();
		
		Cidade cidadeDestino = this.cidadeRepositorio.save(criarCidadeDestino());
		this.cidadeDestinoCodigo = cidadeDestino.getCodigo();
		
		Voo voo = this.vooRepositorio.save(criarVoo(piloto, aviao, cidadeOrigem, cidadeDestino));
		this.vooCodigo = voo.getCodigo();
	}
	
	@Test
	public void testeBuscaPorId()
	{
		Voo voo = this.vooRepositorio.findByCodigo(vooCodigo);
		assertNotNull(voo);
	}
	
	@Test
	public void testeBuscarPorCidadeOrigemAndDestino()
	{
		List<Voo> alVoo = this.vooRepositorio.findByCidadeOrigemCodigoAndCidadeDestinoCodigo(cidadeOrigemCodigo, cidadeDestinoCodigo);
		assertTrue(alVoo.size() > 0);
	}
	
	@After
	public void finalizar()
	{
		vooRepositorio.deleteAll();
		cidadeRepositorio.deleteAll();
		aviaoRepositorio.deleteAll();
		pilotoRepositorio.deleteAll();
	}
	
	

	private Piloto criarPiloto()
	{
		Piloto piloto = new Piloto();
		piloto.setIdentificacao("1A2B3C");
		piloto.setNome("João");
		return piloto;
	}
	
	private Aviao criarAviao()
	{
		Aviao aviao = new Aviao();
		aviao.setNome("AIRBUS A320");
		return aviao;
	}
	
	private Cidade criarCidadeOrigem()
	{
		Cidade cidade = new Cidade();
		cidade.setNome("São  Paulo");
		return cidade;
	}
	
	private Cidade criarCidadeDestino()
	{
		Cidade cidade = new Cidade();
		cidade.setNome("Rio de Janeiro");
		return cidade;
	}
	
	private Voo criarVoo(Piloto piloto, Aviao aviao, Cidade cidadeOrigem, Cidade cidadeDestino) throws ParseException
	{
		Voo voo = new Voo();
		voo.setPiloto(piloto);
		voo.setAviao(aviao);
		voo.setCidadeOrigem(cidadeOrigem);
		voo.setCidadeDestino(cidadeDestino);
		
		java.sql.Date vooDataPartida = new java.sql.Date(format.parse(dataPartida).getTime());
		voo.setDataPartida(vooDataPartida);		
		
		java.sql.Date vooDataChegada = new java.sql.Date(format.parse(dataChegada).getTime());
		voo.setDataPartida(vooDataChegada);
		
		return voo;
	}
	
	
}
