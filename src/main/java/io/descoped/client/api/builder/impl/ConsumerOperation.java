package io.descoped.client.api.builder.impl;

import io.descoped.client.api.builder.intf.OperationHandler;
import io.descoped.client.api.builder.intf.OutcomeHandler;

import java.util.stream.Stream;

public class ConsumerOperation implements OperationHandler {

    public ConsumerOperation() {
    }

    public ConsumerOperation(String url, Stream<Object> params) {
    }

    @Override
    public boolean execute() {
        return false;
    }

    @Override
    public OutcomeHandler getOutcomeHandler() {
        return null;
    }
}
