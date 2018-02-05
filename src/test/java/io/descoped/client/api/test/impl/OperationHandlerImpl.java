package io.descoped.client.api.test.impl;

import io.descoped.client.api.builder.intf.OperationHandler;
import io.descoped.client.api.builder.intf.OutcomeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 23/11/2017
 */
abstract public class OperationHandlerImpl implements OperationHandler {

    private static final Logger log = LoggerFactory.getLogger(OperationHandlerImpl.class);

    private OutcomeHandler outcomeHandler;

    public OperationHandlerImpl(OutcomeHandler outcomeHandler) {
        this.outcomeHandler = outcomeHandler;
    }

    @Override
    abstract public boolean execute();

    @Override
    public OutcomeHandler getOutcomeHandler() {
        return outcomeHandler;
    }

}
