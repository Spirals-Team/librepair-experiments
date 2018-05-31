package com.cmpl.web.front.ui.page;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.factory.DisplayFactory;

@Controller
public class AMPController {

  private static final Logger LOGGER = LoggerFactory.getLogger(AMPController.class);
  private final DisplayFactory displayFactory;

  public AMPController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  /**
   * Mapping pour les pages
   *
   * @param pageName
   * @param pageNumber
   * @return
   */
  @GetMapping(value = "/amp/{pageName}")
  public ModelAndView printPage(@PathVariable(value = "pageName") String pageName,
      @RequestParam(name = "p", required = false) Integer pageNumber, Locale locale) {

    LOGGER.info("Accès à la page amp" + pageName);
    return displayFactory.computeModelAndViewForAMP(pageName, locale, computePageNumberFromRequest(pageNumber));
  }

  int computePageNumberFromRequest(Integer pageNumber) {
    if (pageNumber == null) {
      return 0;
    }
    return pageNumber.intValue();

  }

}
