package io.vertx.up.secure.provider;

interface Info {

    String MAP_INITED = "[ ZERO ] ( Auth ) The async shared map has been initialized: name = {0}";

    String MAP_HIT = "[ ZERO ] ( Auth ) The async shared map cache has been hitted by key = {0}, value = {1}";

    String MAP_MISSING = "[ ZERO ] ( Auth ) The async shared map cache has not been hitted by key = {0}";

    String MAP_PUT = "[ ZERO ] ( Auth ) The async shared map cache has been put with key = {0}, value = {1}";
}
