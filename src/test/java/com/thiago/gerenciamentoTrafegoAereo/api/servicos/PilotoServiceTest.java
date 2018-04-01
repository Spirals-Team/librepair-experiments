package com.thiago.gerenciamentoTrafegoAereo.api.servicos;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.thiago.gerenciamentoTrafegoAereo.api.dao.interfaces.DaoPilotoInterface;
import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Piloto;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PilotoServiceTest
{
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DaoPilotoInterface daoPilotoInterface;
	private Piloto piloto;
	private static final String URL_BASE = "/api/piloto/";
	private static final Long PILOTO_CODIGO = 1L;
	private static final String PILOTO_NOME = "João";
	private static final String PILOTO_IDENTIFICACAO = "1A2B3C";

	@Before
	public void criaPiloto()
	{
		piloto = new Piloto();
		piloto.setCodigo(PILOTO_CODIGO);
		piloto.setNome(PILOTO_NOME);
		piloto.setIdentificacao(PILOTO_IDENTIFICACAO);
	}

	@Test
	public void testGetPilotoPorId() throws Exception
	{
		BDDMockito.given(this.daoPilotoInterface.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(piloto));

		mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE + PILOTO_CODIGO).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.data.codigo").value(PILOTO_CODIGO))
				.andExpect(jsonPath("$.data.nome").value(PILOTO_NOME))
				.andExpect(jsonPath("$.data.identificacao").value(PILOTO_IDENTIFICACAO)).andExpect(jsonPath("$.erros").isEmpty());
	}

	@Test
	public void testGetAllPiloto() throws Exception
	{
		List<Piloto> alPiloto = new ArrayList<Piloto>();
		alPiloto.add(piloto);
		BDDMockito.given(daoPilotoInterface.listar()).willReturn(alPiloto);
		mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.erros").isEmpty()).andExpect(jsonPath("$.data").isNotEmpty());
	}

}
