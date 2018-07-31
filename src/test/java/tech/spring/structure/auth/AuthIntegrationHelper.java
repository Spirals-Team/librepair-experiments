package tech.spring.structure.auth;

import static org.junit.Assert.assertTrue;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tech.spring.structure.auth.model.User;

public abstract class AuthIntegrationHelper {

    protected final static String TEXT_PLAIN_VALUE_UTF8 = TEXT_PLAIN_VALUE + ";charset=UTF-8";

    @Value("classpath:data/users.json")
    private Resource usersResource;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected List<User> users;

    @Before
    public void initializeMockUsers() throws JsonParseException, JsonMappingException, IOException {
        // @formatter:off
        users = objectMapper.readValue(usersResource.getFile(), new TypeReference<List<User>>() {});
        // @formatter:on
    }

    protected Cookie login(String username) throws Exception {
        User user = getMockUser(username);
        return login(user.getUsername(), user.getPassword());
    }

    protected Cookie login(User user) throws Exception {
        return login(user.getUsername(), user.getPassword());
    }

    protected User getMockUser(String username) {
        Optional<User> user = users.stream().filter(u -> username.equals(u.getUsername())).findFirst();
        assertTrue("Unable to find mock user with username " + username + "!", user.isPresent());
        return user.get();
    }

    private Cookie login(String username, String password) throws Exception {
        MvcResult result = mockMvc.perform(post("/login").param("username", username).param("password", password)).andReturn();
        return result.getResponse().getCookie("SESSION");
    }

}
