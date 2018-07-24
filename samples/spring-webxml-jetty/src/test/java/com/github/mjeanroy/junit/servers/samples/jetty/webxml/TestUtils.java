/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 <mickael.jeanroy@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.mjeanroy.junit.servers.samples.jetty.webxml;

import com.github.mjeanroy.junit.servers.client.HttpClient;
import com.github.mjeanroy.junit.servers.jetty.EmbeddedJettyConfiguration;
import com.github.mjeanroy.junit.servers.servers.EmbeddedServer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Static Test Utilities.
 */
class TestUtils {

	// Ensure non instantiation.
	private TestUtils() {
	}

	/**
	 * Create the Jetty Embedded configuration to use in unit tests.
	 *
	 * @return The Jetty Embedded Configuration.
	 * @throws AssertionError If an error occurred while creation the configuration object.
	 */
	static EmbeddedJettyConfiguration createJettyConfiguration() {
		try {

			String current = new File(".").getCanonicalPath();
			if (!current.endsWith("/")) {
				current += "/";
			}

			String subProjectPath = "samples/spring-webxml-jetty/";
			String path = current.endsWith(subProjectPath) ? current : current + subProjectPath;

			return EmbeddedJettyConfiguration.builder()
					.withWebapp(path + "src/main/webapp")
					.withClasspath(path + "target/classes")
					.build();

		} catch (Exception ex) {
			throw new AssertionError(ex);
		}
	}

	/**
	 * Ensure the index page is rendered with expected message.
	 *
	 * @param client The HTTP client to use.
	 * @param jetty The embedded jetty.
	 */
	static void ensureIndexIsOk(HttpClient client, EmbeddedServer jetty) {
		String message = client
				.prepareGet("/index")
				.execute()
				.body();

		assertThat(message).isNotEmpty().isEqualTo("Hello World");

		// Try to get servlet context
		ServletContext servletContext = jetty.getServletContext();
		assertThat(servletContext).isNotNull();

		// Try to retrieve spring webApplicationContext
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		assertThat(webApplicationContext).isNotNull();
	}
}
