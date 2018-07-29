package io.zero.epic;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.TestContext;
import io.zero.quiz.StoreBase;
import org.junit.Test;

public class Jackson1Tc extends StoreBase {

    @Test
    public void testvJson(final TestContext context) {
        final JsonObject data = IO.getJObject(this.getFile("jackson-testInt.json"));
        final JsonObject hitted = Jackson.visitJObject(data, "lang", "home", "email");
        context.assertNotNull(hitted);
    }
}
