package ch.tbmelabs.tv.core.entryserver.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import ch.tbmelabs.tv.shared.constants.spring.SpringApplicationProfile;

@Component
@Profile(SpringApplicationProfile.DEV)
public class ZuulAccessFilter extends ZuulFilter {
  private static final Logger LOGGER = LogManager.getLogger(ZuulAccessFilter.class);

  @Override
  public String filterType() {
    return "post";
  }

  @Override
  public int filterOrder() {
    return 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() {
    HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
    HttpServletResponse response = RequestContext.getCurrentContext().getResponse();

    LOGGER.info("REQUEST  :: < " + request.getScheme() + " " + request.getLocalAddr() + ":" + request.getLocalPort());
    LOGGER.info("REQUEST  :: < " + request.getMethod() + " " + request.getRequestURI() + " " + request.getProtocol());
    LOGGER.info("RESPONSE :: > HTTP:" + response.getStatus());

    return null;
  }
}