package me.phlask.api.router;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import me.phlask.api.PhlaskService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * The name space for this test is odd
 * It is much more an integration test
 * than a unit test. So maybe it is better up
 * one layer up. However, it mostly touches the at this router.
 */
@RunWith(VertxUnitRunner.class)
public class TapRouterTest {

    private static final String BASE_URI = "/v1/taps";

    private Vertx vertx;
    private static final int PORT = 8080;
    private static final String HOST = "localhost";

    @Before
    public void before(TestContext context) {

        DeploymentOptions options = new DeploymentOptions()
                .setConfig(new JsonObject().put("http.PORT", PORT));

        vertx = Vertx.vertx();
        vertx.deployVerticle(PhlaskService.class, options, context.asyncAssertSuccess());
    }

    @After
    public void after(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void happyPath(TestContext context) {
        final String expected = "{\"response\":null}";

        Async async = context.async();
        HttpClient client = vertx.createHttpClient();

        String uriWithParams = String.format("%s?latitude=4747477.89&longitude=486463.6", BASE_URI);

        client.getNow(PORT, HOST, uriWithParams, response -> {
            response.bodyHandler(body -> {
                context.assertEquals(expected, body.toString());
                client.close();
                async.complete();
            });
        });
    }

    @Test
    public void onMissingParams(TestContext context) {
        final String expected = "{\"response\":\"Missing/bad params on tap request\"}";

        Async async = context.async();
        HttpClient client = vertx.createHttpClient();

        client.getNow(PORT, HOST, BASE_URI, response -> {
            response.bodyHandler(body -> {
                context.assertEquals(expected, body.toString());
                client.close();
                async.complete();
            });
        });
    }

    @Test
    public void onBadParams(TestContext context) {
        final String expected = "{\"response\":\"Missing/bad params on tap request\"}";
        final String uriWithBadParams = String.format("%s?latitude=null&longitude=hey", BASE_URI);

        Async async = context.async();
        HttpClient client = vertx.createHttpClient();

        client.getNow(PORT, HOST, uriWithBadParams, response -> {
            response.bodyHandler(body -> {
                context.assertEquals(expected, body.toString());
                client.close();
                async.complete();
            });
        });
    }

}
