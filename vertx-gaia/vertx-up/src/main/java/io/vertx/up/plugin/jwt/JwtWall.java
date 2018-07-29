package io.vertx.up.plugin.jwt;

import io.vertx.core.json.JsonObject;
import io.vertx.up.atom.secure.Cliff;
import io.vertx.up.eon.em.WallType;
import io.vertx.up.log.Annal;
import io.vertx.up.plugin.mongo.MongoWall;
import io.vertx.zero.atom.Ruler;
import io.vertx.zero.marshal.Transformer;
import io.zero.epic.fn.Fn;

public class JwtWall implements Transformer<Cliff> {

    private static final Annal LOGGER = Annal.get(MongoWall.class);

    @Override
    public Cliff transform(final JsonObject input) {
        Fn.outUp(() -> Ruler.verify("wall-jwt", input), LOGGER);
        final Cliff cliff = new Cliff();
        cliff.setType(WallType.JWT);
        cliff.setConfig(input.getJsonObject("config"));
        return cliff;
    }
}
