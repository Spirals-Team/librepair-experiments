package ua.com.company.store.controller.impl.executions;

import org.apache.log4j.Logger;
import ua.com.company.store.constants.Redirection;
import ua.com.company.store.controller.command.CommandTypical;
import ua.com.company.store.utils.CookiesAction;
import ua.com.company.store.utils.RedirectionManager;
import ua.com.company.store.utils.ServletWrapper;
import ua.com.company.store.utils.SessionManager;
import ua.com.company.store.hashing.PasswordHashing;
import ua.com.company.store.model.dto.SignUpDto;
import ua.com.company.store.model.entity.User;
import ua.com.company.store.service.UserService;
import ua.com.company.store.validation.ValidatorAbstract;
import ua.com.company.store.validation.signup.LoginValidator;
import ua.com.company.store.validation.signup.PasswordValidator;
import ua.com.company.store.validation.signup.EmailValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Владислав on 29.11.2017.
 */
public class CommandSignUpFormExecution implements CommandTypical {
    private Logger logger = Logger.getRootLogger();
    private UserService userService;
    private PasswordHashing passwordHashing;

    public CommandSignUpFormExecution(UserService userService, PasswordHashing passwordHashing) {
        this.userService = userService;
        this.passwordHashing = passwordHashing;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getSession().getAttribute("user") != null) {
            return Redirection.ACCESS_ERROR_PAGE;
        }

        String login = req.getParameter("login");
        System.out.println(login);


        String pass = req.getParameter("password");
        String email = req.getParameter("email");

        String checkedIds = req.getParameter("checkMeOut");


        HttpSession session = req.getSession(true);
        SignUpDto signUpDto = getUserInput(login, email, pass);
        User user = null;
        if (doValidationInputs(signUpDto)) {
            logger.info("Successful validated inputs signUp");
            try {
                user = new User.UserBuilder().id(0).nickname(signUpDto.getLogin()).password(passwordHashing.hashingPassword(signUpDto.getPassword()))
                        .email(signUpDto.getEmail()).role(false).isDefaulter(false).build();
                System.out.println(user.toString());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            if (userService.validationUserOnBeforeExist(user)) {
                logger.info("Validation on existing user is fallen");
                req.setAttribute("userEmail", user.getEmail());
                return Redirection.EXIST_USER_ERROR;
            } else {
                if (SessionManager.getSessionManager().isUserLoggedIn(session)) {
                    RedirectionManager.getRediractionManger().redirect(new ServletWrapper(req, resp), "/main.jsp");
                    return RedirectionManager.REDIRECTION;
                }
                int userID = userService.addUser(user);
                User userFromDB = userService.getById(userID);
                session.setAttribute("user", userFromDB);
                req.setAttribute("user", userFromDB);

                if (checkedIds != null && checkedIds.equals("OK")) {
                    CookiesAction.setCookieUserInform(resp, user);
                    logger.info("Marked checkbox and added user in cookie successfully");
                } else {
                    logger.info("Continue execution without adding in cookie ");
                }
                return Redirection.MAIN_PAGE;
            }
        } else {
            logger.info("Validated inputs unsuccessful " + login + " -- " + pass);
            req.setAttribute("error", " NUll inputs!!!");
            return Redirection.ERRORS_WITH_INPUTS;
        }
    }

    private SignUpDto getUserInput(String login, String email, String password) {
        return new SignUpDto(login, email, password);
    }

    private boolean doValidationInputs(SignUpDto signUpDto) {
        ValidatorAbstract validatorAbstractLogin = new LoginValidator();
        ValidatorAbstract validatorAbstractPassword = new PasswordValidator();
        ValidatorAbstract validatorAbstractEmail = new EmailValidator();
        validatorAbstractLogin.setNextValidator(validatorAbstractPassword);
        validatorAbstractPassword.setNextValidator(validatorAbstractEmail);
        return validatorAbstractLogin.validate(signUpDto.getLogin(), signUpDto.getPassword(), signUpDto.getEmail());
    }
}