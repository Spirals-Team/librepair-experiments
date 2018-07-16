package br.com.rodolfo.pontointeligente.api.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.rodolfo.pontointeligente.api.entities.Empresa;
import br.com.rodolfo.pontointeligente.api.services.EmpresaService;

/**
 * EmpresaControllerTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
//Implementa um contexto WEB para a realização dos testes
@AutoConfigureMockMvc
public class EmpresaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private EmpresaService empresaService;

    private static final String BUSCAR_EMPRESA_CNPJ_URL = "/api/empresas/cnpj/";
    private static final Long ID = Long.valueOf(1);
    private static final String CNPJ = "11861136000104";
    private static final String RAZAO_SOCIAL = "Empresa XYZ";

    @Test
    @WithMockUser
    public void testBuscarEmpresaCnpjInvalido() throws Exception {
        
        BDDMockito.given(this.empresaService.BuscarPorCnpj(Mockito.anyString())).willReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get(BUSCAR_EMPRESA_CNPJ_URL + CNPJ).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").value("Empresa não encontrada para o CNPJ: " + CNPJ));
    }

    @Test
    @WithMockUser
    public void testBuscarEmpresaCnpjValido() throws Exception {
        
        BDDMockito.given(this.empresaService.BuscarPorCnpj(Mockito.anyString())).willReturn(Optional.of(this.getDadosEmpresa()));

        mockMvc.perform(MockMvcRequestBuilders.get(BUSCAR_EMPRESA_CNPJ_URL + CNPJ).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(ID))
                .andExpect(jsonPath("$.data.razaoSocial", equalTo(RAZAO_SOCIAL)))
                .andExpect(jsonPath("$.data.cnpj", equalTo(CNPJ)))
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    private Empresa getDadosEmpresa() {
        
        Empresa empresa = new Empresa();

        empresa.setId(ID);
        empresa.setCnpj(CNPJ);
        empresa.setRazaoSocial(RAZAO_SOCIAL);

        return empresa;
    }

}