package com.acqua;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**
 * Servlet initializer to support traditional deployments (war file)
 * 
 * @author Christian Lusardi
 * @version 1.0
 *
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AcquaApplication.class);
	}

}
