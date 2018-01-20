package ru.curriculum.domain.admin;

import boot.IntegrationWebBoot;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.curriculum.domain.admin.user.repository.UserRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ErrorControllerTest extends IntegrationWebBoot {
    // TODO: сделать так чтобы админ не удалялся при интеграционных тестах либо удалялся
    @Autowired
    private UserRepository userRepository;

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void tryToGetNonExistentUser_mustReturnErrorPage() throws Exception {
        mockMvc.perform(get("/admin/users/edit/19999"))
                .andExpect(view().name("error/errorPage"));
    }
}
