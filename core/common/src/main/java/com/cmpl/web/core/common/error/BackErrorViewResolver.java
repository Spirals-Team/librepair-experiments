package com.cmpl.web.core.common.error;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus.Series;
import org.springframework.web.servlet.ModelAndView;

public class BackErrorViewResolver implements ErrorViewResolver, Ordered {

  private int order = Ordered.LOWEST_PRECEDENCE;
  private final Map<Series, String> seriesView;

  public BackErrorViewResolver() {
    Map<Series, String> views = new HashMap<>();
    views.put(Series.CLIENT_ERROR, "4xx");
    views.put(Series.SERVER_ERROR, "5xx");
    this.seriesView = Collections.unmodifiableMap(views);
  }

  @Override
  public int getOrder() {
    return this.order;
  }

  @Override
  public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
    ModelAndView modelAndView = resolve(String.valueOf(status), model);
    if (modelAndView == null && seriesView.containsKey(status.series())) {
      modelAndView = resolve(seriesView.get(status.series()), model);
    }
    return modelAndView;
  }

  private ModelAndView resolve(String viewName, Map<String, Object> model) {
    return new ModelAndView(computeErrorViewName(viewName, model), model);
  }

  private String computeErrorViewName(String viewName, Map<String, Object> model) {
    String path = ((String) model.getOrDefault("path", ""));
    if (isBackPath(path)) {
      return computeBackErrorViewName(viewName);
    }
    if (isFrontPath(path)) {
      return computeFrontErrorViewName(viewName);
    }
    return computeBackErrorViewName(viewName);
  }

  private boolean isBackPath(String path) {
    return path.startsWith("/manager");
  }

  private boolean isFrontPath(String path) {
    return path.startsWith("/pages") || "/".equals(path);
  }

  private String computeFrontErrorViewName(String viewName) {
    return viewName;

  }

  private String computeBackErrorViewName(String viewName) {
    return "back/error/" + viewName;

  }

}
