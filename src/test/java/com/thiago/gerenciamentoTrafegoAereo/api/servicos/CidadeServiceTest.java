package com.thiago.gerenciamentoTrafegoAereo.api.servicos;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.thiago.gerenciamentoTrafegoAereo.api.dao.interfaces.DaoCidadeInterface;
import com.thiago.gerenciamentoTrafegoAereo.api.entidades.Cidade;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CidadeServiceTest
{
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DaoCidadeInterface daoCidadeInterface;	
	private Cidade cidade;
	private static final String URL_BASE = "/api/cidade/";
	private static final Long CIDADE_CODIGO = 1L;
	private static final String CIDADE_NOME = "São Paulo";
	
	@Before
	public void criarCidade()
	{
		cidade = new Cidade();
		cidade.setCodigo(CIDADE_CODIGO);
		cidade.setNome(CIDADE_NOME);
	}
	
	@Test
	public void testGetAllCidade() throws Exception
	{
		List<Cidade> alCidade = new ArrayList<Cidade>();
		alCidade.add(cidade);
		BDDMockito.given(daoCidadeInterface.listar()).willReturn(alCidade);
		mockMvc.perform(MockMvcRequestBuilders.get(URL_BASE).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.erros").isEmpty()).andExpect(jsonPath("$.data").isNotEmpty());
	}
	
}
