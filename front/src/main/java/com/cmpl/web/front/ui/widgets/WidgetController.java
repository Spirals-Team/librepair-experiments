package com.cmpl.web.front.ui.widgets;

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
public class WidgetController {

  private static final Logger LOGGER = LoggerFactory.getLogger(WidgetController.class);
  private final DisplayFactory displayFactory;

  public WidgetController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  @GetMapping(value = "/widgets/{widgetName}")
  public ModelAndView printPage(@PathVariable(value = "widgetName") String widgetName,
      @RequestParam(name = "p", required = false) Integer pageNumber,
      @RequestParam(name = "pageName", required = false) String pageName, Locale locale) {
    LOGGER.info("Récupération du widget " + widgetName);
    return displayFactory.computeModelAndViewForWidget(widgetName, locale, computePageNumberFromRequest(pageNumber),
        pageName);
  }

  int computePageNumberFromRequest(Integer pageNumber) {
    if (pageNumber == null) {
      return 0;
    }
    return pageNumber.intValue();

  }

}
