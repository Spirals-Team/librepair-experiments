package com.cmpl.web.manager.ui.core.login;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.factory.login.LoginDisplayFactory;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.core.user.ChangePasswordForm;
import com.cmpl.web.core.user.ChangePasswordResponse;
import com.cmpl.web.core.user.RequestPasswordLinkResponse;
import com.cmpl.web.core.user.UserDispatcher;

/**
 * Controller pour afficher la page de login
 * 
 * @author Louis
 *
 */
@Controller
public class LoginController {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

  private final LoginDisplayFactory displayFactory;
  private final UserDispatcher userDispatcher;

  /**
   * Constructeur en autowired
   * 
   * @param displayFactory
   */
  public LoginController(LoginDisplayFactory displayFactory, UserDispatcher userDispatcher) {
    this.displayFactory = displayFactory;
    this.userDispatcher = userDispatcher;
  }

  /**
   * Mapping pour la page de login
   * 
   * @return
   */
  @GetMapping(value = "/login")
  public ModelAndView printLogin() {

    LOGGER.info("Accès à la page " + BACK_PAGE.LOGIN.name());
    return displayFactory.computeModelAndViewForBackPage(BACK_PAGE.LOGIN, Locale.FRANCE);
  }

  @GetMapping(value = "/forgotten_password")
  public ModelAndView printForgottenPassword() {
    LOGGER.info("Accès à la page " + BACK_PAGE.FORGOTTEN_PASSWORD.name());
    return displayFactory.computeModelAndViewForBackPage(BACK_PAGE.FORGOTTEN_PASSWORD, Locale.FRANCE);
  }

  @PostMapping(value = "/forgotten_password")
  @ResponseBody
  public ResponseEntity<RequestPasswordLinkResponse> handleForgottenPassword(@RequestBody String email, Locale locale) {
    RequestPasswordLinkResponse response = userDispatcher.sendChangePasswordLink(email, locale);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping(value = "/change_password")
  public ModelAndView printChangePassword(@RequestParam("token") String token, Locale locale) {
    LOGGER.info("Accès à la page " + BACK_PAGE.FORGOTTEN_PASSWORD.name());

    ModelAndView changePasswordModel = displayFactory.computeModelAndViewForBackPage(BACK_PAGE.CHANGE_PASSWORD,
        Locale.FRANCE);
    changePasswordModel.addObject("token", token);
    return changePasswordModel;
  }

  @PostMapping(value = "/change_password")
  @ResponseBody
  public ResponseEntity<ChangePasswordResponse> handleChangePassword(@RequestBody ChangePasswordForm form, Locale locale) {
    ChangePasswordResponse response = userDispatcher.changePassword(form, locale);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
