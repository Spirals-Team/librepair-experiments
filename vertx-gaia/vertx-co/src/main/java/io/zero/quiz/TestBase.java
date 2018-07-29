package io.zero.quiz;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.zero.log.Log;
import io.zero.epic.Ut;

public class TestBase {

    protected String getFile(final String filename) {
        final Class<?> clazz = this.getClass();
        final String file = "test/" + clazz.getPackage().getName() + "/" + filename;
        Log.info(this.getLogger(), "[TEST] Test input file: {0}", file);
        return file;
    }

    protected JsonObject getJson(final String filename) {
        return Ut.ioJObject(this.getFile(filename));
    }

    protected JsonArray getArray(final String filename) {
        return Ut.ioJArray(this.getFile(filename));
    }

    protected Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}
