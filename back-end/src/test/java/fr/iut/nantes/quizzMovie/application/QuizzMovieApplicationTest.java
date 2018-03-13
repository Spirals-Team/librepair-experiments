package fr.iut.nantes.quizzMovie.application;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import fr.iut.nantes.quizzMovie.Utils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @version 1.0
 * @see QuizzMovieApplication
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = QuizzMovieApplication.class)
@ComponentScan(basePackages = "fr.iut.nantes.quizzMovie")
@AutoConfigureMockMvc
public class QuizzMovieApplicationTest {

    @Autowired
    private MockMvc mvc;

    private String token;

    /**
     * @throws Exception
     * @since 1.0
     */
    @Before
    public void setUp() throws Exception {
        //decom if data clear
        mvc.perform(MockMvcRequestBuilders.post("/register")
                .content("{\"login\":\"loginTest\", \"password\":\"pwdLoginTest\"}"));

        Utils.setupConfig();
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/login")
                .content("{\"login\":\"loginTest\", \"password\":\"pwdLoginTest\"}")).andReturn();
        JsonParser jsonParser = new JsonParser();
        JsonElement parse = jsonParser.parse(result.getResponse().getContentAsString());
        token = parse.getAsJsonObject().get("token").getAsString();
    }

    /**
     * @throws Exception
     * @see QuizzMovieApplication#generateQuestion(String)
     * @since 1.0
     */
    @Test
    public void generateQuestion() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/question")
                .param("token", token))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.question").isNotEmpty());
    }

    /**
     * @throws Exception
     * @see QuizzMovieApplication#sendResponse(HttpEntity, String)
     * @since 1.0
     */
    @Test
    public void sendResponseWhitNoQuestion() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/response"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    /**
     * @throws Exception
     * @see QuizzMovieApplication#sendResponse(HttpEntity, String)
     * @since 1.0
     */
    @Test
    public void sendResponse() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/question")
                .param("token", token))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.question").isNotEmpty());
        mvc.perform(MockMvcRequestBuilders.post("/response")
                .param("token", token)
                .param("response", "................"))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
    }

    /**
     * @throws Exception
     * @see QuizzMovieApplication#disconnect(String)
     * @since 1.0
     */
    @Test
    public void disconnect() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/login")
                .content("{\"login\":\"loginTest\", \"password\":\"pwdLoginTest\"}")).andReturn();
        JsonParser jsonParser = new JsonParser();
        JsonElement parse = jsonParser.parse(result.getResponse().getContentAsString());
        String tokenForDisconnect = parse.getAsJsonObject().get("token").getAsString();

        mvc.perform(MockMvcRequestBuilders.get("/disconnect")
                .param("token", tokenForDisconnect))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    /**
     * @throws Exception
     * @see QuizzMovieApplication#getGoodAnswers(String)
     * @since 1.0
     */
    @Test
    public void getGoodAnswers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/goodanswers")
                .param("token", token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.goodanswers").isNotEmpty());
    }

    /**
     * @throws Exception
     * @see QuizzMovieApplication#getanswers(String)
     * @since 1.0
     */
    @Test
    public void getAnswers() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/answers")
                .param("token", token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.answers").isNotEmpty());

    }

    /**
     * @throws Exception
     * @see QuizzMovieApplication#index()
     * @since 1.0
     */
    @Test
    public void index() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.server").value("OK"));
    }

    /**
     * @throws Exception
     * @see QuizzMovieApplication#register(HttpEntity)
     * @since 1.0
     */
    @Test
    public void register() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/register")
                .content("{\"login\":\"loginTest2\", \"password\":\"pwdLoginTest2\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("true"));
    }


}