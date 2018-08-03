package com.cmpl.web.front.ui.website;

import java.util.Locale;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.factory.DisplayFactory;

@Controller
public class RenderingWebsiteController {

  private static final Logger LOGGER = LoggerFactory.getLogger(RenderingWebsiteController.class);

  private final DisplayFactory displayFactory;

  public RenderingWebsiteController(DisplayFactory displayFactory) {
    this.displayFactory = Objects.requireNonNull(displayFactory);
  }

  @GetMapping(value = "/sites/{websiteName}/pages/{pageName}")
  public ModelAndView printWebsitePage(@PathVariable(value = "websiteName") String websiteName,
      @PathVariable(value = "pageName") String pageName, @RequestParam(name = "p", required = false) Integer pageNumber,
      Locale locale) {

    LOGGER.info("Accès à la page {0} du site {1}", pageName, websiteName);
    return displayFactory.computeModelAndViewForWebsitePage(websiteName, pageName, locale,
        computePageNumberFromRequest(pageNumber));

  }

  int computePageNumberFromRequest(Integer pageNumber) {
    if (pageNumber == null) {
      return 0;
    }
    return pageNumber.intValue();

  }

}
