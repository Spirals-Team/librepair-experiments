package com.cmpl.web.front.ui.robot;

import java.net.URI;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmpl.web.core.website.WebsiteDTO;
import com.cmpl.web.core.website.WebsiteService;

/**
 * Controller pour le robot d'indexation google et autres
 * 
 * @author Louis
 *
 */
@Controller
public class RobotsController {

  private static final Logger LOGGER = LoggerFactory.getLogger(RobotsController.class);
  private final WebsiteService websiteService;

  public RobotsController(WebsiteService websiteService) {
    this.websiteService = Objects.requireNonNull(websiteService);

  }

  /**
   * Mapping pour le robot d'indexation google et autres
   *
   */
  @GetMapping(value = {"/sites/{websiteName}/robots", "/sites/{websiteName}/robot", "/sites/{websiteName}/robot.txt",
      "/sites/{websiteName}/robots.txt"})
  @ResponseBody
  public String printRobot(@PathVariable(value = "websiteName") String websiteName) {

    WebsiteDTO websiteDTO = websiteService.getWebsiteByName(websiteName);
    if (websiteDTO == null) {
      return "";
    }
    LOGGER.info("Accès à la page des robots pour le site {}", websiteName);
    return printRobotString(websiteDTO);

  }

  public String printRobotString(WebsiteDTO websiteDTO) {

    String scheme = websiteDTO.isSecure() ? "https://" : "http://";
    String robots = "User-agent: *\n" + "Disallow: /manager/\n" + "\n" + "Sitemap: "
        + URI.create(scheme + websiteDTO.getName() + "/sitemap.xml");
    return robots;

  }

}
