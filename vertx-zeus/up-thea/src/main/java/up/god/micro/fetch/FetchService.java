package up.god.micro.fetch;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.up.aiki.Ux;
import up.god.domain.tables.daos.SysTabularDao;
import up.god.domain.tables.pojos.SysTabular;

public class FetchService implements FetchStub {
    @Override
    public Future<JsonObject> fetchByName(final String name) {
        return Ux.Jooq.on(SysTabularDao.class)
                .<SysTabular>fetchOneAsync("S_NAME", name)
                .compose(item -> Ux.thenJsonOne(item, "tabular"));
    }

    @Override
    public Future<JsonArray> fetchByTypes(final Object... types) {
        return Ux.Jooq.on(SysTabularDao.class)
                .<SysTabular>fetchInAsync("S_TYPE", types)
                .compose(item -> Ux.thenJsonMore(item, "tabular"));
    }

    @Override
    public Future<JsonArray> fetchByFilters(final JsonObject filters) {
        return Ux.Jooq.on(SysTabularDao.class)
                .<SysTabular>fetchAndAsync(filters)
                .compose(item -> Ux.thenJsonMore(item, "tabular"));
    }
}
