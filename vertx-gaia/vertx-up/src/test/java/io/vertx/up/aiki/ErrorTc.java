package io.vertx.up.aiki;

import io.vertx.up.exception.WebException;
import io.vertx.up.exception._500InternalServerException;
import org.junit.Assert;
import org.junit.Test;

public class ErrorTc {

    @Test
    public void buildError() {
        final WebException error =
                new _500InternalServerException(this.getClass(), "Error Internal");
        System.out.println(error);
        Assert.assertNotNull(error);
    }
}
