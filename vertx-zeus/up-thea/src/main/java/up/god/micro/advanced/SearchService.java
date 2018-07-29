package up.god.micro.advanced;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.up.aiki.Ux;
import io.vertx.up.atom.query.Inquiry;
import up.god.domain.tables.daos.SysTabularDao;

public class SearchService implements SearchStub {
    @Override
    public Future<JsonObject> search(final Inquiry inquiry) {
        return Ux.Jooq.on(SysTabularDao.class)
                .searchAndAsync(inquiry, "tabular");
    }
}
