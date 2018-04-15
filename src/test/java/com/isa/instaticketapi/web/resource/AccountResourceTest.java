package com.isa.instaticketapi.web.resource;

import com.isa.instaticketapi.AbstractResourceTest;
import com.isa.instaticketapi.repository.UserRepository;
import com.isa.instaticketapi.service.AccountService;
import com.isa.instaticketapi.service.dto.account.ChangePasswordDTO;
import com.isa.instaticketapi.service.dto.account.LoginDTO;
import com.isa.instaticketapi.service.dto.account.UserDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

public class AccountResourceTest extends AbstractResourceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    @Transactional
    public void testSuccesfulLogin() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("sansajn");
        loginDTO.setPassword("sansajn");
        loginDTO.setRememberMe(false);
        String input = mapToJson(loginDTO);
        String uri = "/api/auth/authenticate";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(input)).andReturn();
        int status = result.getResponse().getStatus();
        Assert.assertEquals(200,status);
    }

    @Test
    @Transactional
    public void testRegisterNullPassword() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("tester");
        userDTO.setFirstName("tester");
        userDTO.setLastName("testirko");
        userDTO.setAddress("jovana ducica");
        userDTO.setCity("novi sad");
        userDTO.setEmail("test@test.com");
        userDTO.setNumber("0142/2144124");
        String input = mapToJson(userDTO);
        String uri = "/api/auth/sign-up";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(input)).andReturn();
        int status = result.getResponse().getStatus();
        Assert.assertEquals(400, status);
    }

    @Test
    @Transactional
    public void testSuccesfullRegistration() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("tester");
        userDTO.setPassword("testiranje");
        userDTO.setFirstName("tester");
        userDTO.setLastName("testirko");
        userDTO.setAddress("jovana ducica");
        userDTO.setCity("novi sad");
        userDTO.setEmail("test@test.com");
        userDTO.setNumber("0142/2144124");
        String input = mapToJson(userDTO);
        String uri = "/api/auth/sign-up";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(input)).andReturn();
        int status = result.getResponse().getStatus();
        Assert.assertEquals(201, status);
    }

    @Test
    @Transactional
    public void testUnsuccesfullAccountDelete() throws Exception {
        ChangePasswordDTO passwords = new ChangePasswordDTO("losasifra","losasifraa");
        String input = mapToJson(passwords);
        String uri = "/api/auth/delete-account";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(input)).andReturn();
        Assert.assertEquals(400, result.getResponse().getStatus());
    }

}
