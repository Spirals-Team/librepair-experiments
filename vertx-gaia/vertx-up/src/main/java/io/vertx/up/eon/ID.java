package io.vertx.up.eon;

public interface ID {

    String PARAMS_CONTENT = "$$PARAM_CONTENT$$";

    String PARAMS_LENGTH = "$$PARAM_LENGTH$$";

    String DIRECT = "$$DIRECT$$";

    String IGNORE = "$$IGNORE$$";

    interface Addr {

        String REGISTRY_START = "ZERO://MICRO/REGISTRY/START";

        String IPC_START = "ZERO://MICRO/IPC/START";
    }

    interface Header {

        String USER = "X-User";

        String ROLE = "X-Role";
    }
}
