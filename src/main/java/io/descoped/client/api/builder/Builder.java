package io.descoped.client.api.builder;

public interface Builder {

    Worker worker(String id);

    Builder execute();
}
