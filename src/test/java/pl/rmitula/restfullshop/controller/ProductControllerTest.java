package pl.rmitula.restfullshop.controller;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(this.wac).build();
    }

    @Test
    public void GetAllProductsTest() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$.[0].category", Matchers.is(1)))
                .andExpect(jsonPath("$.[0].name", Matchers.is("Nike Air Max 1")))
                .andExpect(jsonPath("$.[0].quanityInStock", Matchers.is(10)))
                .andExpect(jsonPath("$.[0].price", Matchers.is(450.99)))
                .andExpect(jsonPath("$.*", Matchers.hasSize(5)));
    }

    @Test
    public void GetProductByIdTest() throws Exception {
        mockMvc.perform(get("/api/products/{id}", 3))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", Matchers.is(3)))
                .andExpect(jsonPath("$.category", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("Converse All Star")))
                .andExpect(jsonPath("$.quanityInStock", Matchers.is(10)))
                .andExpect(jsonPath("$.price", Matchers.is(222.22)));
    }

    @Test
    public void GetProductByIdwithUnknownIdTest() throws Exception {
        mockMvc.perform(get("/api/products/{id}", 100))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.code", Matchers.is(404)))
                .andExpect(jsonPath("$.description", Matchers.is("Not found product with id: 100")));
    }

    @Test
    public void GetProductsByCategoryIdTest() throws Exception {
        mockMvc.perform(get("/api/category/{categoryId}/products", 2))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[0].id", Matchers.is(5)))
                .andExpect(jsonPath("$.[0].category", Matchers.is(2)))
                .andExpect(jsonPath("$.[0].name", Matchers.is("Supereme Bogo Navy")))
                .andExpect(jsonPath("$.[0].quanityInStock", Matchers.is(2)))
                .andExpect(jsonPath("$.[0].price", Matchers.is(500.1)));
    }

    @Test
    public void GetProductsByCategoryIdWithUnknownCategoryIdTest() throws Exception {
        mockMvc.perform(get("/api/category/{categoryId}/products", 100))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.code", Matchers.is(404)))
                .andExpect(jsonPath("$.description", Matchers.is("Not found category with id: 100")));
    }

    @Test
    public void CreateProductTest() throws Exception {
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createProductInJson("2", "New product", "13", "140")))
                .andExpect(status().isCreated());
    }

    @Test
    public void CreateProductWithUnknownCategoryTest() throws Exception {
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createProductInJson("100", "New product", "13", "140")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", Matchers.is(404)))
                .andExpect(jsonPath("$.description", Matchers.is("Not found category with id: 100")));
    }

    @Test
    public void CreateProductWithExistingNameTest() throws Exception {
        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createProductInJson("2", "Adidas Superstar", "13", "140")))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code", Matchers.is(409)))
                .andExpect(jsonPath("$.description", Matchers.is("This name is already associated with a different product.")));
    }

    @Test
    public void UpdateProductTest() throws Exception {
        mockMvc.perform(put("/api/products/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createProductInJson("1", "New", "13", "140")))
                .andExpect(status().isOk());
    }

    @Test
    public void UpdateProductWithUnknownIdTest() throws Exception {
        mockMvc.perform(put("/api/products/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createProductInJson("100", "New", "13", "140")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", Matchers.is(404)))
                .andExpect(jsonPath("$.description", Matchers.is("Not found category with id: 100")));
    }

    @Test
    public void DeleteProductTest() throws Exception {
        mockMvc.perform(delete("/api/products/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private static String createProductInJson (String category, String name, String quanityInStock, String price) {
        return "{ \"category\": \"" + category + "\", " +
                "\"name\":\"" + name + "\"," +
                "\"quanityInStock\":\"" + quanityInStock + "\"," +
                "\"price\":\"" + price + "\"}";
    }

}