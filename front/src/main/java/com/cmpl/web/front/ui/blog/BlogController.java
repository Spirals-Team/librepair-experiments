package com.cmpl.web.front.ui.blog;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cmpl.web.core.factory.DisplayFactory;
import com.cmpl.web.front.ui.page.PageController;

@Controller
@RequestMapping(value = "/blog")
public class BlogController {

  private static final Logger LOGGER = LoggerFactory.getLogger(PageController.class);
  private final DisplayFactory displayFactory;

  public BlogController(DisplayFactory displayFactory) {
    this.displayFactory = displayFactory;
  }

  @GetMapping(value = "/entries/{newsEntryId}")
  public ModelAndView printBlogEntry(@PathVariable(value = "newsEntryId") String newsEntryId,
      @RequestParam(name = "widgetId", required = false) String widgetId, Locale locale) {

    LOGGER.info("Recuperation de l'entree de blog d'id " + newsEntryId);
    return displayFactory.computeModelAndViewForBlogEntry(newsEntryId, widgetId, locale);
  }

}
