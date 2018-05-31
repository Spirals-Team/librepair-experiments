package com.cmpl.web.front.ui.robot;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmpl.web.core.common.context.ContextHolder;

/**
 * Controller pour le robot d'indexation google et autres
 * 
 * @author Louis
 *
 */
@Controller
public class RobotsController {

  private static final Logger LOGGER = LoggerFactory.getLogger(RobotsController.class);
  private final ContextHolder contextHolder;

  public RobotsController(ContextHolder contextHolder) {
    this.contextHolder = contextHolder;
  }

  /**
   * Mapping pour le robot d'indexation google et autres
   * 
   * @param response
   */
  @GetMapping(value = {"/robots", "/robot", "/robot.txt", "/robots.txt"})
  @ResponseBody
  public String printRobot(HttpServletResponse response) {

    LOGGER.info("Accès à la page des robots");
    return printRobotString();

  }

  public String printRobotString() {

    String robots = "User-agent: *\n" + "Disallow: /manager/\n" + "\n" + "Sitemap: " + contextHolder.getWebsiteUrl()
        + "/sitemap.xml";
    return robots;

  }

}
