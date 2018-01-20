package hanfak.shopofhan.infrastructure.web.server;

import com.google.common.annotations.VisibleForTesting;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;

public class EndPoint {
    public final String method;
    public final String path;
    public final List<String> parameterNames;

    @VisibleForTesting
    public EndPoint(String method, String path, List<String> parameterNames) {
        this.method = method;
        this.path = path;
        this.parameterNames = parameterNames;
    }

    public static EndPoint get(String path, String... parameterNames) {
        return new EndPoint("GET", path, Arrays.asList(parameterNames));
    }

    // TODO test
    public static EndPoint post(String path) {
        return new EndPoint("POST", path, emptyList());
    }

    // TODO test
    public static EndPoint delete(String path) {
        return new EndPoint("DELETE", path, emptyList());
    }
}
