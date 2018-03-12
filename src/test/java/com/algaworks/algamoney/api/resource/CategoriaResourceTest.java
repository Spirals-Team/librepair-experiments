package com.algaworks.algamoney.api.resource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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

import com.algaworks.algamoney.api.model.Categoria;
import com.algaworks.algamoney.api.repository.CategoriaRepository;



@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CategoriaResourceTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private CategoriaRepository categoriaRepository;
	
	@Test
	public void testListar() throws Exception {
		
		List<Categoria> lista = popularListaDeCategorias();
		
		BDDMockito.given(this.categoriaRepository.findAll()).willReturn(new ArrayList());
		
		mvc.perform(MockMvcRequestBuilders.get("/categorias").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	private List<Categoria> popularListaDeCategorias() {
		List<Categoria> lista = new ArrayList<Categoria>();
		Categoria categoria = new Categoria();
		categoria.setCodigo(1L);
		categoria.setNome("Lazer");
		lista.add(categoria);
		
		categoria = new Categoria();
		categoria.setCodigo(2L);
		categoria.setNome("Alimentação");
		lista.add(categoria);
		
		categoria = new Categoria();
		categoria.setCodigo(3L);
		categoria.setNome("Supermercado");
		lista.add(categoria);
		
		categoria = new Categoria();
		categoria.setCodigo(4L);
		categoria.setNome("Farmácia");
		lista.add(categoria);
		
		categoria = new Categoria();
		categoria.setCodigo(5L);
		categoria.setNome("Outros");
		lista.add(categoria);
		
		return lista;
	}
	
	
}
