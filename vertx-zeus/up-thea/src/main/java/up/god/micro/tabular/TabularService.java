package up.god.micro.tabular;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.up.aiki.Ux;
import up.god.domain.tables.daos.SysTabularDao;
import up.god.domain.tables.pojos.SysTabular;

import java.time.LocalDateTime;

public class TabularService implements TabularStub {

    @Override
    public Future<JsonObject> fetchOne(final Long id) {
        return Ux.Jooq.on(SysTabularDao.class)
                .<SysTabular>findByIdAsync(id)
                .compose(item -> Ux.thenJsonOne(item, "tabular"));
    }

    @Override
    public Future<JsonObject> create(final JsonObject data) {
        final SysTabular tabular = Ux.fromJson(data, SysTabular.class, "tabular");
        tabular.setZCreateTime(LocalDateTime.now());
        tabular.setZUpdateTime(LocalDateTime.now());
        return Ux.Jooq.on(SysTabularDao.class)
                .insertReturningPrimaryAsync(tabular, tabular::setPkId)
                .compose(item -> Ux.thenJsonOne(item, "tabular"));
    }

    @Override
    public Future<JsonObject> update(final Long id, final JsonObject data) {
        final SysTabular updated = Ux.fromJson(data, SysTabular.class, "tabular");
        return Ux.Jooq.on(SysTabularDao.class).saveAsync(id, updated)
                .compose(item -> Ux.thenJsonOne(item, "tabular"));
    }

    @Override
    public Future<JsonObject> delete(final Long id) {
        return Ux.Jooq.on(SysTabularDao.class).deleteByIdAsync(id)
                .compose(result -> (result) ?
                        Future.succeededFuture(new JsonObject().put("result", Boolean.TRUE)) :
                        Future.succeededFuture(new JsonObject().put("result", Boolean.FALSE))
                );
    }
}
