package org.jboss.resteasy.links.test.el;

import static org.jboss.resteasy.test.TestPortProvider.generateBaseUrl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.InternalServerErrorException;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient43Engine;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.links.test.BookStoreService;
import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.jboss.resteasy.test.TestPortProvider;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class TestLinksInvalidEL
{

   private static NettyJaxrsServer server;
   private static Dispatcher dispatcher;

   @BeforeClass
   public static void beforeClass() throws Exception
   {
      server = new NettyJaxrsServer();
      server.setPort(TestPortProvider.getPort());
      server.setRootResourcePath("/");
      server.start();
      dispatcher = server.getDeployment().getDispatcher();
   }

   @AfterClass
   public static void afterClass() throws Exception
   {
      server.stop();
      server = null;
      dispatcher = null;
   }

	@Parameters
	public static List<Class<?>[]> getParameters(){
		List<Class<?>[]> classes = new ArrayList<Class<?>[]>();
		classes.add(new Class<?>[]{BookStoreInvalidEL.class});
		return classes;
	}

	private Class<?> resourceType;
	private String url;
	private BookStoreService client;
	private HttpClient httpClient;
	
	public TestLinksInvalidEL(Class<?> resourceType){
		this.resourceType = resourceType;
	}
	
	@Before
	public void before(){
		POJOResourceFactory noDefaults = new POJOResourceFactory(resourceType);
		dispatcher.getRegistry().addResourceFactory(noDefaults);
		httpClient = HttpClientBuilder.create().build();
		ApacheHttpClient43Engine engine = new ApacheHttpClient43Engine(httpClient);
		url = generateBaseUrl();
		ResteasyWebTarget target = new ResteasyClientBuilder().httpEngine(engine).build().target(url);
		client = target.proxy(BookStoreService.class);
	}

	@SuppressWarnings("deprecation")
    @After
	public void after(){
		// TJWS does not support chunk encodings well so I need to kill kept
		// alive connections
		httpClient.getConnectionManager().closeIdleConnections(0, TimeUnit.MILLISECONDS);
		dispatcher.getRegistry().removeRegistrations(resourceType);
	}
	
	@Test
	public void testELWorksWithoutPackageXML() throws Exception
	{
		try{
			client.getBookXML("foo");
			Assert.fail("This should have caused a 500");
		}catch(InternalServerErrorException x){
			System.err.println("Failure is "+x.getResponse().readEntity(String.class));
			Assert.assertEquals(500, x.getResponse().getStatus());
	    }catch(Exception x){
	         Assert.fail("Expected InternalServerErrorException");
	    }
	}
	@Test
	public void testELWorksWithoutPackageJSON() throws Exception
	{
		try{
			client.getBookJSON("foo");
			Assert.fail("This should have caused a 500");
		}catch(InternalServerErrorException x){
			System.err.println("Failure is "+x.getResponse().readEntity(String.class));
			Assert.assertEquals(500, x.getResponse().getStatus());
		}catch(Exception x){
		   Assert.fail("Expected InternalServerErrorException");
		}
	}
}