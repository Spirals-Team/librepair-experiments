package com.thiago.gerenciamentoTrafegoAereo.api.servicos;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.thiago.gerenciamentoTrafegoAereo.api.dao.interfaces.DaoVooInterface;
import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Aviao;
import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Cidade;
import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Piloto;
import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Voo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class VooServiceTest
{
	private String dataPartida = "28/12/2018 12:00";
	private String dataChegada = "28/12/2018 13:00";
	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DaoVooInterface daoVooInterface;
	
	@Before
	public void inicializar() throws Exception
	{
		Piloto piloto = criarPiloto();
		
		Aviao aviao = criarAviao();
		
		Cidade cidadeOrigem = criarCidadeOrigem();		
		
		Cidade cidadeDestino = criarCidadeDestino();		
		
		Voo voo = criarVoo(piloto, aviao, cidadeOrigem, cidadeDestino);
		
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
