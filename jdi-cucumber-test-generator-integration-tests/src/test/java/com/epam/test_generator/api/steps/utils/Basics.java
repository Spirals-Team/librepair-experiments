package com.epam.test_generator.api.steps.utils;

import static com.epam.http.requests.RequestData.requestBody;
import static com.epam.test_generator.api.ApiTokenInserter.requestBodyAndToken;
import static com.epam.test_generator.api.BddGeneratorApi.changeUserRoleUsingPUT;
import static com.epam.test_generator.api.BddGeneratorApi.loginUsingPOST;
import static com.epam.test_generator.api.BddGeneratorApi.registerUserAccountUsingPOST;

import com.epam.http.response.RestResponse;
import com.epam.test_generator.api.steps.StepBackground;
import com.epam.test_generator.controllers.admin.request.UserRoleUpdateDTO;
import com.epam.test_generator.controllers.user.request.LoginUserDTO;
import com.epam.test_generator.controllers.user.request.RegistrationUserDTO;
import com.epam.test_generator.controllers.user.response.UserDTO;
import com.epam.test_generator.dto.TokenDTO;

public class Basics extends StepBackground {

    public void registerUser() throws Throwable {

        RegistrationUserDTO registrationUserDTO = createRegistrationUserDTO();

        String userDTOAsString = mapper.writeValueAsString(registrationUserDTO);
        RestResponse response = registerUserAccountUsingPOST.call(requestBody(userDTOAsString));

        UserDTO userDTO = mapper
            .readValue(response.raResponse().print(), UserDTO.class);
        testContext.setTestDTO(userDTO);
    }

    public void loginUser(String email, String password) throws Throwable {
        TokenDTO tokenDTO = login(email, password);
        testContext.setToken(tokenDTO.getToken());
    }

    public void loginAdmin() throws Throwable {
        TokenDTO tokenDTO = login(testContext.ADMIN_EMAIL, testContext.ADMIN_PASSWORD);
        testContext.setAdminToken(tokenDTO.getToken());
    }

    private TokenDTO login(String email, String password) throws Throwable {
        LoginUserDTO loginUserDTO = new LoginUserDTO();
        loginUserDTO.setEmail(email);
        loginUserDTO.setPassword(password);

        String loginUserDTOAsString = mapper
            .writeValueAsString(loginUserDTO);
        RestResponse response = loginUsingPOST.call(requestBody(loginUserDTOAsString));

        return mapper.readValue(response.raResponse().print(), TokenDTO.class);
    }

    public RegistrationUserDTO createRegistrationUserDTO() {
        String email = "test" + testContext.getCounterAndIncrease() + "@mail.com";
        RegistrationUserDTO registrationUserDTO = new RegistrationUserDTO();
        registrationUserDTO.setEmail(email);
        registrationUserDTO.setPassword(testContext.USER_PASSWORD);
        registrationUserDTO.setName(testContext.USER_NAME);
        registrationUserDTO.setSurname(testContext.USER_SURNAME);
        return registrationUserDTO;
    }

    public void changeRole(String email, String role) throws Throwable {
        UserRoleUpdateDTO userRoleUpdateDTO = new UserRoleUpdateDTO();
        userRoleUpdateDTO.setEmail(email);
        userRoleUpdateDTO.setRole(role);

        String userRoleUpdateDTOAsString = mapper.writeValueAsString(userRoleUpdateDTO);

        RestResponse response = changeUserRoleUsingPUT
            .call(requestBodyAndToken(userRoleUpdateDTOAsString, testContext.getAdminToken()));
        testContext.setResponse(response);
    }
}
