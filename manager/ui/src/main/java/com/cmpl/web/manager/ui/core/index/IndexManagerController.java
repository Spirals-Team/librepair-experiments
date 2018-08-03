package com.cmpl.web.manager.ui.core.index;

import java.util.Locale;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.factory.index.IndexDisplayFactory;
import com.cmpl.web.core.page.BACK_PAGE;
import com.cmpl.web.manager.ui.core.common.stereotype.ManagerController;

@ManagerController
@RequestMapping(value = "/manager")
public class IndexManagerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(IndexManagerController.class);
  private final IndexDisplayFactory displayFactory;

  public IndexManagerController(IndexDisplayFactory displayFactory) {

    this.displayFactory = Objects.requireNonNull(displayFactory);
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
