package com.github.kristofa.brave.jersey2;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.http.ITServletContainer;
import com.github.kristofa.brave.http.SpanNameProvider;
import com.github.kristofa.brave.jaxrs2.BraveTracingFeature;
import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.AssumptionViolatedException;
import org.junit.Test;

public class ITBraveTracingFeature_Server extends ITServletContainer {

  @Override
  @Test
  public void reportsSpanOnTransportException() throws Exception {
    // TODO: it seems the only way to process exceptions in a standard way is ExceptionMapper.
    // However, we probably shouldn't do that as it can interfere with user defined ones. We
    // should decide whether to use non-standard means (ex jersey classes), or some other way.
    throw new AssumptionViolatedException("jaxrs-2 filters cannot process exceptions");
  }

  @Path("")
  public static class TestResource {

    @GET
    @Path("foo")
    public Response get() {
      return Response.status(200).build();
    }

    @GET
    @Path("disconnect")
    public Response disconnect() throws IOException {
      throw new IOException();
    }
  }

  @Override protected void init(ServletContextHandler handler, Brave brave,
      SpanNameProvider spanNameProvider) {

    ResourceConfig config = new ResourceConfig()
        .register(TestResource.class)
        .register(BraveTracingFeature.builder(brave)
            .spanNameProvider(spanNameProvider)
            .build()
        );
    handler.addServlet(new ServletHolder(new ServletContainer(config)), "/*");
  }
}
