package com.cmpl.web.manager.ui.core.index;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.factory.BackDisplayFactory;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.manager.ui.core.stereotype.ManagerController;

@ManagerController
@RequestMapping(value = "/manager")
public class IndexManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(IndexManagerController.class);
  private final BackDisplayFactory displayFactory;

  public IndexManagerController(BackDisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  @GetMapping
  public ModelAndView printIndex() {
    LOGGER.info("Accès à la page " + BACK_PAGE.INDEX.name());
    return displayFactory.computeModelAndViewForBackPage(BACK_PAGE.INDEX, Locale.FRANCE);
  }

  @GetMapping(value = "/")
  public ModelAndView printIndexGlobal() {
    LOGGER.info("Accès à la page " + BACK_PAGE.INDEX.name());
    return displayFactory.computeModelAndViewForBackPage(BACK_PAGE.INDEX, Locale.FRANCE);
  }

}
