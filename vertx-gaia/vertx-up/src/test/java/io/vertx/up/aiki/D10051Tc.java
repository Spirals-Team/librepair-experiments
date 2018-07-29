package io.vertx.up.aiki;

import com.fasterxml.jackson.core.type.TypeReference;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.zero.epic.Ut;
import io.zero.quiz.TestBase;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class D10051Tc extends TestBase {

    @Test
    public void testToJarray() {
        final JsonArray data = this.getArray("d10051.json");
        final List<D10051Obj> obj = Ut.deserialize(data, new TypeReference<List<D10051Obj>>() {
        });
        // Convert
        final JsonArray ret = Ux.toArray(obj, "d10051");
        System.err.println(ret.encode());
        Assert.assertEquals(2, ret.size());
    }

    @Test
    public void testToUnique() {
        final JsonArray data = this.getArray("d10051.json");
        final List<D10051Obj> obj = Ut.deserialize(data, new TypeReference<List<D10051Obj>>() {
        });
        // Convert
        final JsonObject ret = Ux.toUnique(obj, "d10051");
        Assert.assertNotNull(ret);
        System.err.println(ret.encode());
        // Null returned
        final List<D10051Obj> nullfirst = new ArrayList<>();
        final JsonObject set = Ux.toUnique(nullfirst);
        Assert.assertNotNull(set);
        System.err.println(ret.encode());
    }
}
