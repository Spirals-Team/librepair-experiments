package ru.curriculum.domain.admin;

import boot.IntegrationWebBoot;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.curriculum.domain.admin.user.entity.User;
import ru.curriculum.domain.admin.user.repository.UserRepository;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthControllerTest extends IntegrationWebBoot {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private FilterChainProxy filter;
    protected MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    User user;

    @Override
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilter(filter)
                .build();

        user = new User("testAdmin", "123", "test", "test", "test");
        userRepository.save(user);
    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @WithAnonymousUser
    public void getLoginFormTest() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(view().name("login"))
                .andExpect(model().errorCount(0));
    }

    @Test
    public void loginTest() throws Exception {
        mockMvc.perform(formLogin("/login")
                .user("testAdmin")
                .password("123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andDo(print());
    }

    @Test
    public void loginWithWrongLoginAndPassword_mustBeLoginError() throws Exception {
        mockMvc.perform(formLogin("/login")
                .user("none")
                .password("empty"))
                .andExpect(redirectedUrl("/login?error"))
                .andDo(print());
    }

    @Test
    public void tryToGetResourceWithoutAuth_mustBeAccessDenied() throws Exception {
        mockMvc.perform(post("/admin"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/accessDenied"))
                .andDo(print());
    }
}
