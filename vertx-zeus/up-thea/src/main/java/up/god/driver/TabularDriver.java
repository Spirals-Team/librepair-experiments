package up.god.driver;

import io.vertx.core.json.JsonObject;
import io.vertx.up.aiki.Ux;
import up.god.domain.tables.daos.SysTabularDao;

import java.util.Date;

public class TabularDriver {

    public static void main(final String[] args) {
        Ux.Jooq.on(SysTabularDao.class)
                .fetchAndAsync(new JsonObject().put("Z_CREATE_TIME,=,day", new Date().toInstant())
                        .put("Z_SIGMA", "Simg"));
    }
}
