package br.com.reclameaqui.teste.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.reclameaqui.teste.TesteApplication;
import br.com.reclameaqui.teste.dtos.ReclamantesRequestDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TesteApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestUtils {
	
	@Test
	public void testObjectToJson() {
		
		ReclamantesRequestDTO reclamantes = ReclamantesRequestDTO.builder().cidade("São Paulo").empresa("Samsung").build();
		
		String json = Utils.asJsonString(reclamantes);

		assertThat(json).isNotNull();
		assertEquals(json, "{\"empresa\":\"Samsung\",\"cidade\":\"São Paulo\"}");
	}
	
}
