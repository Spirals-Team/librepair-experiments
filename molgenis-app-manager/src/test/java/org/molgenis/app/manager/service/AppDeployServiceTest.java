package org.molgenis.app.manager.service;

import org.mockito.Mock;
import org.molgenis.app.manager.exception.AppManagerException;
import org.molgenis.app.manager.meta.App;
import org.molgenis.app.manager.meta.AppMetadata;
import org.molgenis.app.manager.service.impl.AppDeployServiceImpl;
import org.molgenis.data.DataService;
import org.molgenis.data.Query;
import org.molgenis.data.support.QueryImpl;
import org.springframework.mock.web.MockHttpServletResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.testng.Assert.assertEquals;

public class AppDeployServiceTest
{
	private static final String APP_META_NAME = "sys_App";

	private AppDeployService appDeployService;

	@Mock
	private DataService dataService;

	private App app;

	@BeforeMethod
	public void beforeMethod()
	{
		initMocks(this);

		app = mock(App.class);
		when(app.getId()).thenReturn("id");
		when(app.getUri()).thenReturn("uri");
		when(app.getLabel()).thenReturn("label");
		when(app.getDescription()).thenReturn("description");
		when(app.isActive()).thenReturn(true);
		when(app.getAppVersion()).thenReturn("v1.0.0");
		when(app.includeMenuAndFooter()).thenReturn(true);
		when(app.getTemplateContent()).thenReturn("<h1>Test</h1>");
		when(app.getAppConfig()).thenReturn("{'config': 'test'}");

		ClassLoader classLoader = getClass().getClassLoader();
		when(app.getResourceFolder()).thenReturn(classLoader.getResource("test-resources").getFile());

		appDeployService = new AppDeployServiceImpl(dataService);
	}

	@Test
	public void testConfigureTemplateResourceReferencing()
	{
		String baseUrl = "/test/base/url/";
		String template = "<script src=js/test.js></script>\n<link href=css/test.css />\n<img src=img/test.png></img>";

		String actual = appDeployService.configureTemplateResourceReferencing(template, baseUrl);
		String expected =
				"<script src=/test/base/url/js/test.js></script>\n" + "<link href=/test/base/url/css/test.css />\n"
						+ "<img src=/test/base/url/img/test.png></img>";

		assertEquals(actual, expected);
	}

	@Test
	public void testLoadJavascriptResources() throws IOException
	{
		Query<App> query = QueryImpl.EQ(AppMetadata.URI, "test");
		when(dataService.findOne(APP_META_NAME, query, App.class)).thenReturn(app);
		MockHttpServletResponse response = new MockHttpServletResponse();
		appDeployService.loadJavascriptResources("test", "test.js", response);

		assertEquals(response.getContentAsString(), "var test = \"test file\"");
		assertEquals(response.getContentType(), "application/javascript; charset=UTF-8");
		assertEquals(response.getContentLength(), 22);
		assertEquals(response.getHeader("Content-Disposition"), "attachment; filename=test.js");
	}

	@Test
	public void testLoadCSSResources() throws IOException
	{
		Query<App> query = QueryImpl.EQ(AppMetadata.URI, "test");
		when(dataService.findOne(APP_META_NAME, query, App.class)).thenReturn(app);
		MockHttpServletResponse response = new MockHttpServletResponse();
		appDeployService.loadCSSResources("test", "test.css", response);

		assertEquals(response.getContentAsString(), ".test {\n    color: red\n}");
		assertEquals(response.getContentType(), "text/css; charset=utf-8");
		assertEquals(response.getContentLength(), 24);
		assertEquals(response.getHeader("Content-Disposition"), "attachment; filename=test.css");
	}

	@Test
	public void testLoadImageResources() throws IOException
	{
		Query<App> query = QueryImpl.EQ(AppMetadata.URI, "test");
		when(dataService.findOne(APP_META_NAME, query, App.class)).thenReturn(app);
		MockHttpServletResponse response = new MockHttpServletResponse();
		appDeployService.loadImageResources("test", "test.png", response);

		assertEquals(response.getContentType(), "image/png");
		assertEquals(response.getContentLength(), 3725);
		assertEquals(response.getHeader("Content-Disposition"), "attachment; filename=test.png");
	}

	@Test(expectedExceptions = AppManagerException.class, expectedExceptionsMessageRegExp = "App with uri \\[test\\] does not exist\\.")
	public void testAppDoesNotExist() throws IOException
	{
		Query<App> query = QueryImpl.EQ(AppMetadata.URI, "test");
		when(dataService.findOne(APP_META_NAME, query, App.class)).thenReturn(null);
		MockHttpServletResponse response = new MockHttpServletResponse();
		appDeployService.loadJavascriptResources("test", "test.js", response);
	}
}
