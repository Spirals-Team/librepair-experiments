package org.jboss.resteasy.test.tracing;

import org.jboss.arquillian.container.test.api.OperateOnDeployment;
import org.jboss.resteasy.tracing.api.RESTEasyTracing;
import org.jboss.resteasy.util.HttpResponseCodes;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class DiverseTracingFormatTest extends BasicTracingTest {

        @Test
        @OperateOnDeployment(WAR_BASIC_TRACING_FILE)
        public void testJsonTracing() {
            String url = generateURL("/logger", WAR_BASIC_TRACING_FILE);
            WebTarget base = client.target(url);
            try {
                Response response = base.request().header(RESTEasyTracing.HEADER_ACCEPT_FORMAT, "JSON").get();
                System.out.println(response);

                Assert.assertEquals(HttpResponseCodes.SC_OK, response.getStatus());
                boolean hasTracing = false;
                for (Map.Entry entry : response.getStringHeaders().entrySet()) {
//                System.out.println("<K, V> ->" + entry);
                    if (entry.getKey().toString().startsWith(RESTEasyTracing.HEADER_TRACING_PREFIX)) {
                        hasTracing = true;
                        break;
                    }
                }
                assertTrue(hasTracing);
                response.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
}
