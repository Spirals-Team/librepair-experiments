package org.jboss.resteasy.test.warning.resource;

import org.jboss.resteasy.test.core.interceptors.resource.TestSubResource;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by rsearls on 9/5/17.
 */
@Path("test")
public class TestResource2 {

   @Path("x")
   @Produces("text/plain")
   public TestSubResource locator() {
      return new TestSubResource();
   }
}
