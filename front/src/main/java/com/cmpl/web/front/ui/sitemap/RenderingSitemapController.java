package com.cmpl.web.front.ui.sitemap;

import java.util.Locale;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmpl.web.core.common.exception.BaseException;
import com.cmpl.web.core.sitemap.rendering.RenderingSitemapService;

/**
 * Controller du sitemap
 * 
 * @author Louis
 *
 */
@Controller
public class RenderingSitemapController {

  private static final Logger LOGGER = LoggerFactory.getLogger(RenderingSitemapController.class);

  private final RenderingSitemapService renderingSitemapService;

  public RenderingSitemapController(RenderingSitemapService renderingSitemapService) {

    this.renderingSitemapService = Objects.requireNonNull(renderingSitemapService);

  }

  /**
   * Mapping pour le sitemap
   * 
   * @return
   */
  @GetMapping(value = {"/sites/{websiteName}/sitemap.xml"}, produces = "application/xml")
  @ResponseBody
  public String printSitemap(@PathVariable(value = "websiteName") String websiteName, Locale locale) {

    LOGGER.info("Accès au sitemap");

    try {
      return renderingSitemapService.createSiteMap(websiteName, locale);
    } catch (BaseException e1) {
      LOGGER.error("Impossible de générer le fichier des sitemap", e1);
    }
    return "";

  }

}
