package io.descoped.client.api.builder.impl;

import io.descoped.client.api.builder.Builder;
import io.descoped.client.api.builder.ConsumerTask;
import io.descoped.client.api.builder.Worker;
import io.descoped.client.api.builder.intf.OperationHandler;
import io.descoped.client.api.builder.intf.OutcomeHandler;

import java.util.stream.Stream;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 23/11/2017
 */
public class WorkerImpl implements Worker {

    private Builder parent;
    private String id;
    private Class<? extends OperationHandler> operation;
    private Class<? extends OutcomeHandler> outcome;

    public WorkerImpl(Builder parent, String id) {
        this.parent = parent;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public Worker operation(Class<? extends OperationHandler> operation) {
        this.operation = operation;
        return this;
    }

    public Class<? extends OperationHandler> getOperation() {
        return operation;
    }

    @Override
    public Worker outcome(Class<? extends OutcomeHandler> outcome) {
        this.outcome = outcome;
        return this;
    }

    public Class<? extends OutcomeHandler> getOutcome() {
        return outcome;
    }

    @Override
    public Builder done() {
        return parent;
    }

    @Override
    public Worker consume(String url, Stream<Object> params) {
//        ConsumerOperation op = new ConsumerOperation(url, params);
        this.operation = ConsumerOperation.class;
        return this;
    }

    @Override
    public Worker consume(ConsumerTask<ConsumerJob> consumerTask) {
        return this;
    }

    @Override
    public Worker produce() {
        return this;
    }

    @Override
    public Worker header() {
        return this;
    }

    @Override
    public Worker options() {
        return this;
    }

    @Override
    public Worker delete() {
        return this;
    }
}
