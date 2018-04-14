package br.com.reclameaqui.teste;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import br.com.reclameaqui.teste.documents.Cliente;
import br.com.reclameaqui.teste.documents.Endereco;
import br.com.reclameaqui.teste.dtos.ReclamantesRequestDTO;
import br.com.reclameaqui.teste.util.Utils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TesteApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestClienteIntegracao {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

    @Test
	 public void testQuantidadeDeClientes() throws Exception {

		this.mockMvc.perform(get("/clientes")
	        		.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", Matchers.hasSize(9)));
	 }
	
	@Test
	 public void testQuantidadeDeReclamacoesEmpresaECidade() throws Exception {
		ReclamantesRequestDTO consulta = new ReclamantesRequestDTO("Samsung","São Paulo");

		this.mockMvc.perform(post("/clientes/consultar/reclamantes")
	        		.contentType(MediaType.APPLICATION_JSON)
	        		.content(Utils.asJsonString(consulta))
	        	            .accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.clientesQueReclamaram", Matchers.hasToString("5")));
	 }
	
	@Test
	 public void testQuantidadeDeReclamacoesEmpresa() throws Exception {
		ReclamantesRequestDTO consulta = new ReclamantesRequestDTO();
		consulta.setEmpresa("Samsung");

		this.mockMvc.perform(post("/clientes/consultar/reclamantes")
	        		.contentType(MediaType.APPLICATION_JSON)
	        		.content(Utils.asJsonString(consulta))
	        	            .accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.clientesQueReclamaram", Matchers.hasToString("8")));
	 }
	
	@Test
	 public void testQuantidadeDeReclamacoesCidade() throws Exception {
		ReclamantesRequestDTO consulta = new ReclamantesRequestDTO();
		consulta.setCidade("São Paulo");

		this.mockMvc.perform(post("/clientes/consultar/reclamantes")
	        		.contentType(MediaType.APPLICATION_JSON)
	        		.content(Utils.asJsonString(consulta))
	        	            .accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.clientesQueReclamaram", Matchers.hasToString("6")));
	 }
	
	@Test
	 public void testQuantidadeReclamantes() throws Exception {

		this.mockMvc.perform(get("/reclamacoes")
	        		.contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", Matchers.hasSize(14)));
	 }
	
/*	
	@Test
	 public void testCRUDCliente() throws Exception {
		Endereco endereco = new Endereco("07346444", "Rua Liberdade", "1243", "Jardim Independente", "Campinas");
		Cliente cliente = new Cliente("Jessica", "1945784854", "25042859730", endereco);

		this.mockMvc.perform(post("/cliente")
	        		.contentType(MediaType.APPLICATION_JSON)
	        		.content(Utils.asJsonString(cliente)));
		
		this.mockMvc.perform(get("/cliente/cpf/" + cliente.getCpf())
       		.contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.nome", Matchers.hasToString("Jessica")));
		
		cliente.setNome("Patricia");
		
		this.mockMvc.perform(put("/cliente/cpf/" + cliente.getCpf())
       		.contentType(MediaType.APPLICATION_JSON)
       		.content(Utils.asJsonString(cliente)));
		
		this.mockMvc.perform(get("/cliente/cpf/" + cliente.getCpf())
       		.contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.nome", Matchers.hasToString("Patricia")));
		
		this.mockMvc.perform(delete("/cliente/cpf/" + cliente.getCpf())
       		.contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isNoContent());
		
	 }
	
	*/

    
}