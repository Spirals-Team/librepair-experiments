package tech.spring.structure.menu.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static tech.spring.structure.menu.MenuIntegrationHelper.assertMenu;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tech.spring.structure.auth.evaluator.StructureSecurityExpressionEvaluator;
import tech.spring.structure.menu.config.MenuConfig;
import tech.spring.structure.menu.model.MenuItem;

@RunWith(SpringRunner.class)
public class MenuServiceTest {

    @Value("classpath:mock/menu-config.json")
    private Resource menuConfigResource;

    @Value("classpath:mock/menu.json")
    private Resource menuResource;

    @Spy
    private ObjectMapper objectMapper;

    @Mock
    private StructureSecurityExpressionEvaluator securityExpressionEvaluator;

    @InjectMocks
    private MenuService menuService;

    @Before
    public void init() throws JsonParseException, JsonMappingException, IOException {
        MenuConfig menuConfig = objectMapper.readValue(menuConfigResource.getFile(), MenuConfig.class);
        setField(menuService, "menuConfig", menuConfig);
        when(securityExpressionEvaluator.evaluate(any(String.class), any(HttpServletRequest.class), any(HttpServletResponse.class))).thenReturn(true);
    }

    @Test
    public void testGet() throws JsonParseException, JsonMappingException, IOException {
        List<MenuItem> menu = menuService.get(new MockHttpServletRequest(), new MockHttpServletResponse());
        // @formatter:off
        List<MenuItem> mockMenu = menu = objectMapper.readValue(menuResource.getFile(), new TypeReference<List<MenuItem>>() {});
        // @formatter:on

        assertMenu(mockMenu, menu);
    }

}
