package io.descoped.client.api.builder;

import io.descoped.client.api.builder.impl.ConsumerJob;
import io.descoped.client.api.builder.intf.OperationHandler;
import io.descoped.client.api.builder.intf.OutcomeHandler;

import java.util.stream.Stream;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 07/11/2017
 */
public interface Worker {

    Worker operation(Class<? extends OperationHandler> operation);

    Worker outcome(Class<? extends OutcomeHandler> outcome);

    Builder done();

    Worker consume(String url, Stream<Object> params);

    Worker consume(ConsumerTask<ConsumerJob> consumerTask);

    Worker produce();

    Worker header();

    Worker options();

    Worker delete();
}
