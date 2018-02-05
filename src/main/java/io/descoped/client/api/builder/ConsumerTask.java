package io.descoped.client.api.builder;

import io.descoped.client.api.builder.impl.ConsumerJob;

import java.util.Map;

@FunctionalInterface
public interface ConsumerTask<V> {

     V consume(ConsumerJob job, Map<String,Object> params) throws Exception;

}
