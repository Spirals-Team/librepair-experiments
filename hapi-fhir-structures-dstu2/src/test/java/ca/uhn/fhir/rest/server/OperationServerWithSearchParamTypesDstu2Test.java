package ca.uhn.fhir.rest.server;

import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.dstu2.resource.Conformance;
import ca.uhn.fhir.model.dstu2.resource.OperationDefinition;
import ca.uhn.fhir.model.dstu2.resource.Parameters;
import ca.uhn.fhir.model.dstu2.resource.Patient;
import ca.uhn.fhir.model.primitive.IdDt;
import ca.uhn.fhir.model.primitive.StringDt;
import ca.uhn.fhir.rest.annotation.Operation;
import ca.uhn.fhir.rest.annotation.OperationParam;
import ca.uhn.fhir.rest.param.StringAndListParam;
import ca.uhn.fhir.rest.param.StringOrListParam;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.param.TokenAndListParam;
import ca.uhn.fhir.rest.param.TokenOrListParam;
import ca.uhn.fhir.rest.param.TokenParam;
import ca.uhn.fhir.rest.param.TokenParamModifier;
import ca.uhn.fhir.rest.server.provider.dstu2.ServerConformanceProvider;
import ca.uhn.fhir.util.PortUtil;
import ca.uhn.fhir.util.TestUtil;
import ca.uhn.fhir.util.UrlUtil;

public class OperationServerWithSearchParamTypesDstu2Test {
	private static CloseableHttpClient ourClient;
	private static FhirContext ourCtx;

	private static String ourLastMethod;
	private static List<StringOrListParam> ourLastParamValStr;
	private static List<TokenOrListParam> ourLastParamValTok;
	private static final org.slf4j.Logger ourLog = org.slf4j.LoggerFactory.getLogger(OperationServerWithSearchParamTypesDstu2Test.class);
	private static int ourPort;
	private static Server ourServer;

	@Before
	public void before() {
		ourLastMethod = "";
		ourLastParamValStr = null;
		ourLastParamValTok = null;
	}


	private HttpServletRequest createHttpServletRequest() {
		HttpServletRequest req = mock(HttpServletRequest.class);
		when(req.getRequestURI()).thenReturn("/FhirStorm/fhir/Patient/_search");
		when(req.getServletPath()).thenReturn("/fhir");
		when(req.getRequestURL()).thenReturn(new StringBuffer().append("http://fhirstorm.dyndns.org:8080/FhirStorm/fhir/Patient/_search"));
		when(req.getContextPath()).thenReturn("/FhirStorm");
		return req;
	}

	private ServletConfig createServletConfig() {
		ServletConfig sc = mock(ServletConfig.class);
		when(sc.getServletContext()).thenReturn(null);
		return sc;
	}

	@Test
	public void testAndListWithParameters() throws Exception {
		Parameters p = new Parameters();
		p.addParameter().setName("valstr").setValue(new StringDt("VALSTR1A,VALSTR1B"));
		p.addParameter().setName("valstr").setValue(new StringDt("VALSTR2A,VALSTR2B"));
		p.addParameter().setName("valtok").setValue(new StringDt("VALTOK1A|VALTOK1B"));
		p.addParameter().setName("valtok").setValue(new StringDt("VALTOK2A|VALTOK2B"));
		String inParamsStr = ourCtx.newXmlParser().encodeResourceToString(p);

		HttpPost httpPost = new HttpPost("http://localhost:" + ourPort + "/Patient/$andlist");
		httpPost.setEntity(new StringEntity(inParamsStr, ContentType.create(Constants.CT_FHIR_XML, "UTF-8")));
		HttpResponse status = ourClient.execute(httpPost);

		assertEquals(200, status.getStatusLine().getStatusCode());
		String response = IOUtils.toString(status.getEntity().getContent());
		ourLog.info(response);
		IOUtils.closeQuietly(status.getEntity().getContent());

		assertEquals(2, ourLastParamValStr.size());
		assertEquals(2, ourLastParamValStr.get(0).getValuesAsQueryTokens().size());
		assertEquals("VALSTR1A", ourLastParamValStr.get(0).getValuesAsQueryTokens().get(0).getValue());
		assertEquals("VALSTR1B", ourLastParamValStr.get(0).getValuesAsQueryTokens().get(1).getValue());
		assertEquals("VALSTR2A", ourLastParamValStr.get(1).getValuesAsQueryTokens().get(0).getValue());
		assertEquals("VALSTR2B", ourLastParamValStr.get(1).getValuesAsQueryTokens().get(1).getValue());
		assertEquals(2, ourLastParamValTok.size());
		assertEquals(1, ourLastParamValTok.get(0).getValuesAsQueryTokens().size());
		assertEquals("VALTOK1A", ourLastParamValTok.get(0).getValuesAsQueryTokens().get(0).getSystem());
		assertEquals("VALTOK1B", ourLastParamValTok.get(0).getValuesAsQueryTokens().get(0).getValue());
		assertEquals("VALTOK2A", ourLastParamValTok.get(1).getValuesAsQueryTokens().get(0).getSystem());
		assertEquals("VALTOK2B", ourLastParamValTok.get(1).getValuesAsQueryTokens().get(0).getValue());
		assertEquals("type $orlist", ourLastMethod);
	}

	@Test
	public void testAndListWithUrl() throws Exception {
		HttpGet httpGet = new HttpGet("http://localhost:" + ourPort + "/Patient/$andlist?valstr=VALSTR1A,VALSTR1B&valstr=VALSTR2A,VALSTR2B&valtok=" + UrlUtil.escape("VALTOK1A|VALTOK1B") + "&valtok="
				+ UrlUtil.escape("VALTOK2A|VALTOK2B"));
		HttpResponse status = ourClient.execute(httpGet);

		assertEquals(200, status.getStatusLine().getStatusCode());
		String response = IOUtils.toString(status.getEntity().getContent());
		ourLog.info(response);
		IOUtils.closeQuietly(status.getEntity().getContent());

		assertEquals(2, ourLastParamValStr.size());
		assertEquals(2, ourLastParamValStr.get(0).getValuesAsQueryTokens().size());
		assertEquals("VALSTR1A", ourLastParamValStr.get(0).getValuesAsQueryTokens().get(0).getValue());
		assertEquals("VALSTR1B", ourLastParamValStr.get(0).getValuesAsQueryTokens().get(1).getValue());
		assertEquals("VALSTR2A", ourLastParamValStr.get(1).getValuesAsQueryTokens().get(0).getValue());
		assertEquals("VALSTR2B", ourLastParamValStr.get(1).getValuesAsQueryTokens().get(1).getValue());
		assertEquals(2, ourLastParamValTok.size());
		assertEquals(1, ourLastParamValTok.get(0).getValuesAsQueryTokens().size());
		assertEquals("VALTOK1A", ourLastParamValTok.get(0).getValuesAsQueryTokens().get(0).getSystem());
		assertEquals("VALTOK1B", ourLastParamValTok.get(0).getValuesAsQueryTokens().get(0).getValue());
		assertEquals("VALTOK2A", ourLastParamValTok.get(1).getValuesAsQueryTokens().get(0).getSystem());
		assertEquals("VALTOK2B", ourLastParamValTok.get(1).getValuesAsQueryTokens().get(0).getValue());
		assertEquals("type $orlist", ourLastMethod);
	}

	@Test
	public void testGenerateConformance() throws Exception {
		RestfulServer rs = new RestfulServer(ourCtx);
		rs.setProviders(new PatientProvider());

		ServerConformanceProvider sc = new ServerConformanceProvider(rs);
		rs.setServerConformanceProvider(sc);

		rs.init(createServletConfig());

		Conformance conformance = sc.getServerConformance(createHttpServletRequest());

		String conf = ourCtx.newXmlParser().setPrettyPrint(true).encodeResourceToString(conformance);
		ourLog.info(conf);
		//@formatter:off
		assertThat(conf, stringContainsInOrder(
			"<type value=\"Patient\"/>",
			"<operation>", 
			"<name value=\"andlist\"/>"
		));
		assertThat(conf, stringContainsInOrder(
				"<type value=\"Patient\"/>",
				"<operation>", 
				"<name value=\"nonrepeating\"/>"
			));
		assertThat(conf, stringContainsInOrder(
				"<type value=\"Patient\"/>",
				"<operation>", 
				"<name value=\"orlist\"/>"
			));
		//@formatter:on

		/*
		 * Check the operation definitions themselves
		 */
		OperationDefinition andListDef = sc.readOperationDefinition(new IdDt("OperationDefinition/Patient--andlist"));
		String def = ourCtx.newXmlParser().setPrettyPrint(true).encodeResourceToString(andListDef);
		ourLog.info(def);
		//@formatter:off
		assertThat(def, stringContainsInOrder(
			"<parameter>", 
			"<name value=\"valtok\"/>", 
			"<use value=\"in\"/>", 
			"<min value=\"0\"/>", 
			"<max value=\"10\"/>", 
			"<type value=\"string\"/>", 
			"</parameter>"
		));
		//@formatter:on

	}

	@Test
	public void testNonRepeatingWithParams() throws Exception {
		Parameters p = new Parameters();
		p.addParameter().setName("valstr").setValue(new StringDt("VALSTR"));
		p.addParameter().setName("valtok").setValue(new StringDt("VALTOKA|VALTOKB"));
		String inParamsStr = ourCtx.newXmlParser().encodeResourceToString(p);

		HttpPost httpPost = new HttpPost("http://localhost:" + ourPort + "/Patient/$nonrepeating");
		httpPost.setEntity(new StringEntity(inParamsStr, ContentType.create(Constants.CT_FHIR_XML, "UTF-8")));
		HttpResponse status = ourClient.execute(httpPost);

		assertEquals(200, status.getStatusLine().getStatusCode());
		String response = IOUtils.toString(status.getEntity().getContent());
		ourLog.info(response);
		IOUtils.closeQuietly(status.getEntity().getContent());

		assertEquals(1, ourLastParamValStr.size());
		assertEquals(1, ourLastParamValStr.get(0).getValuesAsQueryTokens().size());
		assertEquals("VALSTR", ourLastParamValStr.get(0).getValuesAsQueryTokens().get(0).getValue());
		assertEquals(1, ourLastParamValTok.size());
		assertEquals(1, ourLastParamValTok.get(0).getValuesAsQueryTokens().size());
		assertEquals("VALTOKA", ourLastParamValTok.get(0).getValuesAsQueryTokens().get(0).getSystem());
		assertEquals("VALTOKB", ourLastParamValTok.get(0).getValuesAsQueryTokens().get(0).getValue());
		assertEquals("type $nonrepeating", ourLastMethod);
	}

	@Test
	public void testNonRepeatingWithUrl() throws Exception {
		HttpGet httpGet = new HttpGet("http://localhost:" + ourPort + "/Patient/$nonrepeating?valstr=VALSTR&valtok=" + UrlUtil.escape("VALTOKA|VALTOKB"));
		HttpResponse status = ourClient.execute(httpGet);

		assertEquals(200, status.getStatusLine().getStatusCode());
		String response = IOUtils.toString(status.getEntity().getContent());
		ourLog.info(response);
		IOUtils.closeQuietly(status.getEntity().getContent());

		assertEquals(1, ourLastParamValStr.size());
		assertEquals(1, ourLastParamValStr.get(0).getValuesAsQueryTokens().size());
		assertEquals("VALSTR", ourLastParamValStr.get(0).getValuesAsQueryTokens().get(0).getValue());
		assertEquals(1, ourLastParamValTok.size());
		assertEquals(1, ourLastParamValTok.get(0).getValuesAsQueryTokens().size());
		assertEquals("VALTOKA", ourLastParamValTok.get(0).getValuesAsQueryTokens().get(0).getSystem());
		assertEquals("VALTOKB", ourLastParamValTok.get(0).getValuesAsQueryTokens().get(0).getValue());
		assertEquals("type $nonrepeating", ourLastMethod);
	}

	@Test
	public void testNonRepeatingWithUrlQualified() throws Exception {
		HttpGet httpGet = new HttpGet("http://localhost:" + ourPort + "/Patient/$nonrepeating?valstr:exact=VALSTR&valtok:not=" + UrlUtil.escape("VALTOKA|VALTOKB"));
		HttpResponse status = ourClient.execute(httpGet);

		assertEquals(200, status.getStatusLine().getStatusCode());
		String response = IOUtils.toString(status.getEntity().getContent());
		ourLog.info(response);
		IOUtils.closeQuietly(status.getEntity().getContent());

		assertEquals(1, ourLastParamValStr.size());
		assertEquals(1, ourLastParamValStr.get(0).getValuesAsQueryTokens().size());
		assertEquals("VALSTR", ourLastParamValStr.get(0).getValuesAsQueryTokens().get(0).getValue());
		assertTrue(ourLastParamValStr.get(0).getValuesAsQueryTokens().get(0).isExact());
		assertEquals(1, ourLastParamValTok.size());
		assertEquals(1, ourLastParamValTok.get(0).getValuesAsQueryTokens().size());
		assertEquals("VALTOKA", ourLastParamValTok.get(0).getValuesAsQueryTokens().get(0).getSystem());
		assertEquals("VALTOKB", ourLastParamValTok.get(0).getValuesAsQueryTokens().get(0).getValue());
		assertEquals(TokenParamModifier.NOT, ourLastParamValTok.get(0).getValuesAsQueryTokens().get(0).getModifier());
		assertEquals("type $nonrepeating", ourLastMethod);
	}

	@Test
	public void testOrListWithParameters() throws Exception {
		Parameters p = new Parameters();
		p.addParameter().setName("valstr").setValue(new StringDt("VALSTR1A,VALSTR1B"));
		p.addParameter().setName("valstr").setValue(new StringDt("VALSTR2A,VALSTR2B"));
		p.addParameter().setName("valtok").setValue(new StringDt("VALTOK1A|VALTOK1B"));
		p.addParameter().setName("valtok").setValue(new StringDt("VALTOK2A|VALTOK2B"));
		String inParamsStr = ourCtx.newXmlParser().encodeResourceToString(p);

		HttpPost httpPost = new HttpPost("http://localhost:" + ourPort + "/Patient/$orlist");
		httpPost.setEntity(new StringEntity(inParamsStr, ContentType.create(Constants.CT_FHIR_XML, "UTF-8")));
		HttpResponse status = ourClient.execute(httpPost);

		assertEquals(200, status.getStatusLine().getStatusCode());
		String response = IOUtils.toString(status.getEntity().getContent());
		ourLog.info(response);
		IOUtils.closeQuietly(status.getEntity().getContent());

		assertEquals(2, ourLastParamValStr.size());
		assertEquals(2, ourLastParamValStr.get(0).getValuesAsQueryTokens().size());
		assertEquals("VALSTR1A", ourLastParamValStr.get(0).getValuesAsQueryTokens().get(0).getValue());
		assertEquals("VALSTR1B", ourLastParamValStr.get(0).getValuesAsQueryTokens().get(1).getValue());
		assertEquals("VALSTR2A", ourLastParamValStr.get(1).getValuesAsQueryTokens().get(0).getValue());
		assertEquals("VALSTR2B", ourLastParamValStr.get(1).getValuesAsQueryTokens().get(1).getValue());
		assertEquals(2, ourLastParamValTok.size());
		assertEquals(1, ourLastParamValTok.get(0).getValuesAsQueryTokens().size());
		assertEquals("VALTOK1A", ourLastParamValTok.get(0).getValuesAsQueryTokens().get(0).getSystem());
		assertEquals("VALTOK1B", ourLastParamValTok.get(0).getValuesAsQueryTokens().get(0).getValue());
		assertEquals("VALTOK2A", ourLastParamValTok.get(1).getValuesAsQueryTokens().get(0).getSystem());
		assertEquals("VALTOK2B", ourLastParamValTok.get(1).getValuesAsQueryTokens().get(0).getValue());
		assertEquals("type $orlist", ourLastMethod);
	}

	@Test
	public void testOrListWithUrl() throws Exception {
		HttpGet httpGet = new HttpGet("http://localhost:" + ourPort + "/Patient/$orlist?valstr=VALSTR1A,VALSTR1B&valstr=VALSTR2A,VALSTR2B&valtok=" + UrlUtil.escape("VALTOK1A|VALTOK1B") + "&valtok="
				+ UrlUtil.escape("VALTOK2A|VALTOK2B"));
		HttpResponse status = ourClient.execute(httpGet);

		assertEquals(200, status.getStatusLine().getStatusCode());
		String response = IOUtils.toString(status.getEntity().getContent());
		ourLog.info(response);
		IOUtils.closeQuietly(status.getEntity().getContent());

		assertEquals(2, ourLastParamValStr.size());
		assertEquals(2, ourLastParamValStr.get(0).getValuesAsQueryTokens().size());
		assertEquals("VALSTR1A", ourLastParamValStr.get(0).getValuesAsQueryTokens().get(0).getValue());
		assertEquals("VALSTR1B", ourLastParamValStr.get(0).getValuesAsQueryTokens().get(1).getValue());
		assertEquals("VALSTR2A", ourLastParamValStr.get(1).getValuesAsQueryTokens().get(0).getValue());
		assertEquals("VALSTR2B", ourLastParamValStr.get(1).getValuesAsQueryTokens().get(1).getValue());
		assertEquals(2, ourLastParamValTok.size());
		assertEquals(1, ourLastParamValTok.get(0).getValuesAsQueryTokens().size());
		assertEquals("VALTOK1A", ourLastParamValTok.get(0).getValuesAsQueryTokens().get(0).getSystem());
		assertEquals("VALTOK1B", ourLastParamValTok.get(0).getValuesAsQueryTokens().get(0).getValue());
		assertEquals("VALTOK2A", ourLastParamValTok.get(1).getValuesAsQueryTokens().get(0).getSystem());
		assertEquals("VALTOK2B", ourLastParamValTok.get(1).getValuesAsQueryTokens().get(0).getValue());
		assertEquals("type $orlist", ourLastMethod);
	}

	@AfterClass
	public static void afterClassClearContext() throws Exception {
		ourServer.stop();
		TestUtil.clearAllStaticFieldsForUnitTest();
	}

	@BeforeClass
	public static void beforeClass() throws Exception {
		ourCtx = FhirContext.forDstu2();
		ourPort = PortUtil.findFreePort();
		ourServer = new Server(ourPort);

		ServletHandler proxyHandler = new ServletHandler();
		RestfulServer servlet = new RestfulServer(ourCtx);

		servlet.setPagingProvider(new FifoMemoryPagingProvider(10).setDefaultPageSize(2));

		servlet.setFhirContext(ourCtx);
		servlet.setResourceProviders(new PatientProvider());
		ServletHolder servletHolder = new ServletHolder(servlet);
		proxyHandler.addServletWithMapping(servletHolder, "/*");
		ourServer.setHandler(proxyHandler);
		ourServer.start();

		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(5000, TimeUnit.MILLISECONDS);
		HttpClientBuilder builder = HttpClientBuilder.create();
		builder.setConnectionManager(connectionManager);
		ourClient = builder.build();

	}

	public static class PatientProvider implements IResourceProvider {

		@Operation(name = "$andlist", idempotent = true)
		public Parameters andlist(
				//@formatter:off
				@OperationParam(name="valstr", max=10) StringAndListParam theValStr,
				@OperationParam(name="valtok", max=10) TokenAndListParam theValTok
				//@formatter:on
		) {
			ourLastMethod = "type $orlist";
			ourLastParamValStr = theValStr.getValuesAsQueryTokens();
			ourLastParamValTok = theValTok.getValuesAsQueryTokens();

			return createEmptyParams();
		}

		/**
		 * Just so we have something to return
		 */
		private Parameters createEmptyParams() {
			Parameters retVal = new Parameters();
			retVal.setId("100");
			return retVal;
		}

		@Override
		public Class<? extends IBaseResource> getResourceType() {
			return Patient.class;
		}

		@Operation(name = "$nonrepeating", idempotent = true)
		public Parameters nonrepeating(
				//@formatter:off
				@OperationParam(name="valstr") StringParam theValStr,
				@OperationParam(name="valtok") TokenParam theValTok
				//@formatter:on
		) {
			ourLastMethod = "type $nonrepeating";
			ourLastParamValStr = Collections.singletonList(new StringOrListParam().add(theValStr));
			ourLastParamValTok = Collections.singletonList(new TokenOrListParam().add(theValTok));

			return createEmptyParams();
		}

		@Operation(name = "$orlist", idempotent = true)
		public Parameters orlist(
				//@formatter:off
				@OperationParam(name="valstr", max=10) List<StringOrListParam> theValStr,
				@OperationParam(name="valtok", max=10) List<TokenOrListParam> theValTok
				//@formatter:on
		) {
			ourLastMethod = "type $orlist";
			ourLastParamValStr = theValStr;
			ourLastParamValTok = theValTok;

			return createEmptyParams();
		}

	}

}
