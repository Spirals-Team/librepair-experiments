package io.vertx.up.plugin.shared;

interface Info {

    String INFO_SYNC = "[ ZERO ] ( Sync ) You are using sync mode to create LocalMap: {0}";

    String INFO_ASYNC_START = "[ ZERO ] ( Async ) You are using async mode to create AsyncMap, start to initialize.";

    String INFO_ASYNC_END = "[ ZERO ] ( Async ) Your AsyncMap {0} has been created successfully, you can use it now.";

    String INFO_TIMER_PUT = "[ ZERO ] ( Timer ) You called timer put method, the key \"{0}\" will be expired in {1} seconds";

    String INFO_TIMER_EXPIRE = "[ ZERO ] ( Timer ) The key \"{0}\" refered data has been removed.";
}
