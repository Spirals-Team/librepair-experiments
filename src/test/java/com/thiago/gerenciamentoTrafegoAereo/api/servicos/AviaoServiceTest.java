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

import com.thiago.gerenciamentoTrafegoAereo.api.dao.interfaces.DaoAviaoInterface;
import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Aviao;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AviaoServiceTest
{
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DaoAviaoInterface daoAviaoInterface;

	private Aviao aviao;

	private static final String URL_BASE = "/api/aviao/";

	private static final Long AVIAO_CODIGO = 1L;
	private static final String AVIAO_NOME = "Boeing 777";

	@Before
	public void criaAviao()
	{
		aviao = new Aviao();
		aviao.setCodigo(AVIAO_CODIGO);
		aviao.setNome(AVIAO_NOME);
	}

	@Test
	public void testGetAviaoPorId() throws Exception
	{
		BDDMockito.given(this.daoAviaoInterface.buscarPorId(Mockito.anyLong())).willReturn(Optional.of(aviao));

		mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE + AVIAO_CODIGO).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.data.codigo").value(AVIAO_CODIGO))
				.andExpect(jsonPath("$.data.nome").value(AVIAO_NOME)).andExpect(jsonPath("$.erros").isEmpty());
	}

	@Test
	public void testGetAllAvioes() throws Exception
	{
		List<Aviao> alAvioes = new ArrayList<Aviao>();
		alAvioes.add(aviao);
		BDDMockito.given(daoAviaoInterface.listar()).willReturn(alAvioes);
		mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.erros").isEmpty()).andExpect(jsonPath("$.data").isNotEmpty());
	}
}
