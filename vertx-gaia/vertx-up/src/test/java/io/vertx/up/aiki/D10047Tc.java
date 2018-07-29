package io.vertx.up.aiki;

import io.vertx.core.json.JsonObject;
import io.zero.epic.Ut;
import io.zero.quiz.TestBase;
import net.sf.cglib.beans.BeanCopier;
import org.junit.Assert;
import org.junit.Test;

public class D10047Tc extends TestBase {
    private <T> T copyEntity(final T target, final T updated) {
        final BeanCopier copier = BeanCopier.create(updated.getClass(), target.getClass(), false);
        copier.copy(updated, target, null);
        return target;
    }

    @Test
    public void testCopy() {
        final JsonObject data = this.getJson("d10047.json");
        final D10047Obj obj = Ut.deserialize(data, D10047Obj.class);
        final D10047Obj target = new D10047Obj();
        final D10047Obj result = this.copyEntity(target, obj);
        System.out.println(Ut.serialize(target));
    }

    @Test
    public void testToJson() {
        final JsonObject data = this.getJson("d10047.json");
        final D10047Obj obj = Ut.deserialize(data, D10047Obj.class);
        // Convert
        final JsonObject result = Ux.toJson(obj);
        System.out.println(result.encodePrettily());
        Assert.assertEquals(4, result.fieldNames().size());
    }

    @Test
    public void testToJsonMapping() {

        final JsonObject data = this.getJson("d10047.json");
        final D10047Obj obj = Ut.deserialize(data, D10047Obj.class);
        // Convert
        final JsonObject result = Ux.toJson(obj, "d10047");
        System.out.println(result.encodePrettily());
        Assert.assertEquals(4, result.fieldNames().size());
        Assert.assertEquals("Lang", result.getString("username"));
    }

    @Test
    public void testToJsonFun() {
        final JsonObject data = this.getJson("d10047.json");
        final D10047Obj obj = Ut.deserialize(data, D10047Obj.class);
        final JsonObject result = Ux.toJsonFun(obj,
                (from) -> from.put("username", from.getString("email")));
        Assert.assertEquals(result.getString("username"),
                result.getString("email"));
    }

    @Test
    public void testFromJson() {
        final JsonObject data = this.getJson("d10047.json");
        final D10047Obj obj = Ux.fromJson(data, D10047Obj.class);
        System.out.println(obj);
        Assert.assertNotNull(obj);
    }

    @Test
    public void testFromJsonMapping() {
        final JsonObject data = this.getJson("d10047-mapping.json");
        final D10047Obj obj = Ux.fromJson(data, D10047Obj.class, "d10047");
        System.out.println(obj);
        Assert.assertNotNull(obj);
    }
}
