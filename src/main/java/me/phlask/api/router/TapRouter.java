package me.phlask.api.router;

import com.google.inject.Injector;
import io.vertx.core.MultiMap;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import me.phlask.api.dto.Result;
import me.phlask.api.dto.ResultImpl;
import me.phlask.api.dto.params.LocationParams;
import me.phlask.api.dto.response.TapResponse;
import me.phlask.api.service.TapService;

import javax.inject.Inject;

import java.util.Optional;

import static io.vertx.ext.sync.Sync.fiberHandler;

public class TapRouter implements RouteHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TapRouter.class);
    private static final String BAD_GET_REQUEST = "Missing/bad params on tap request";
    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";

    @Inject
    private Injector injector;

    private final Router router;
    private static final String BASE_URI = "/v1/taps";

    @Inject
    public TapRouter(Router router) {
        this.router = router;
    }


    @Override
    public void addRoutes() {
        router.get(BASE_URI).handler(fiberHandler(this::getHandler));;
    }

    private void getHandler(RoutingContext handler) {
        TapService service = getService();
        Optional<LocationParams> params = multiMapToLocation(handler.request().params());
        Result<TapResponse> response = Result.withFuture(handler.response());

        if (params.isPresent()) {
            response.addResponse(service.getRequest(params.get()));
        } else {
            LOGGER.error(BAD_GET_REQUEST, handler.request().params());
            TapResponse error = new TapResponse().setResponse(BAD_GET_REQUEST);
            response.addHttpCode(400).addResponse(error);
        }

        response.addResponseFuture(handler.response()).commit();
    }

    private TapService getService() {
        return injector.getInstance(TapService.class);
    }

    private static Optional<LocationParams> multiMapToLocation(MultiMap map) {
        if (map.contains(LONGITUDE) && map.contains(LATITUDE)) {
            String longitude = map.get(LONGITUDE);
            String latitude = map.get(LATITUDE);
            return safeLongLatConversion(longitude, latitude);
        } else {
            return Optional.empty();
        }
    }

    private static Optional<LocationParams> safeLongLatConversion(String latitude, String longitude) {
        try {
            return Optional.of(new LocationParams(longitude, latitude));
        } catch (NumberFormatException e) {
            LOGGER.error("Location params weren't doubles", e);
            return Optional.empty();
        }
    }

}
