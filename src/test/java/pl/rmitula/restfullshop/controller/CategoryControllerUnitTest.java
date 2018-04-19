package pl.rmitula.restfullshop.controller;

        import org.hamcrest.Matchers;
        import org.junit.Before;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.mockito.InjectMocks;
        import org.mockito.Mock;
        import org.springframework.http.MediaType;
        import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
        import org.springframework.test.web.servlet.MockMvc;
        import org.springframework.test.web.servlet.setup.MockMvcBuilders;
        import pl.rmitula.restfullshop.model.User;
        import pl.rmitula.restfullshop.service.UserService;

        import java.util.Arrays;
        import java.util.List;

        import static org.mockito.Mockito.when;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CategoryControllerUnitTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
    }

    @Test
    public void GetAllUsersTest() throws Exception {
        List<User> users = Arrays.asList(
                new User("John", "Snow", "snow", "johnsnow@blackbastard.com", "password"),
                new User("Tyrion", "Lanister", "tyrion", "tyrion@lanister.pl", "password"));

        when(userService.getUsers()).thenReturn(users);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[0].firstName", Matchers.is("John")))
                .andExpect(jsonPath("$.[0].lastName", Matchers.is("Snow")))
                .andExpect(jsonPath("$.[0].username", Matchers.is("snow")))
                .andExpect(jsonPath("$.[0].email", Matchers.is("johnsnow@blackbastard.com")))
                .andExpect(jsonPath("$.[1].firstName", Matchers.is("Tyrion")))
                .andExpect(jsonPath("$.[1].lastName", Matchers.is("Lanister")))
                .andExpect(jsonPath("$.[1].username", Matchers.is("tyrion")))
                .andExpect(jsonPath("$.[1].email", Matchers.is("tyrion@lanister.pl")))
                .andExpect(jsonPath("$.*", Matchers.hasSize(2)));
    }

    @Test
    public void CreateUserTest() throws Exception {
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserInJson("Tyrion", "Lanister", "tyrion", "tyrion@lanister.pl", "password")))
                .andExpect(status().isCreated());
    }

    @Test
    public void UpdateUserDetailsTest() throws Exception {

        mockMvc.perform(put("/api/users/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserInJson("John", "Lanister", "tyrion", "tyrion@lanister.pl", "password")))
                .andExpect(status().isOk());
    }

    @Test
    public void DeleteUserTest() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private static String createUserInJson (String firstName, String lastName, String userName, String email, String password) {
        return "{ \"firstName\": \"" + firstName + "\", " +
                "\"lastName\":\"" + lastName + "\"," +
                "\"email\":\"" + email + "\"," +
                "\"username\":\"" + userName + "\"," +
                "\"password\":\"" + password + "\"}";
    }
}