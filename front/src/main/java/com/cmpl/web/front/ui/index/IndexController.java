package com.cmpl.web.front.ui.index;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.factory.DisplayFactory;

/**
 * Controller pour la page d'accueil
 * 
 * @author Louis
 *
 */
@Controller
public class IndexController {

  private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
  private final DisplayFactory displayFactory;
  
  public IndexController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  /**
   * Mapping pour la page d'accueil
   * 
   * @return
   */
  @GetMapping(value = "/")
  public ModelAndView printIndex(Locale locale) {

    LOGGER.info("Accès à la page d'index");
    return displayFactory.computeModelAndViewForPage("accueil", locale, 0);
  }

}
