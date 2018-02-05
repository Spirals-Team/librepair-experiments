package io.descoped.client.api.builder.intf;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 23/11/2017
 */
public interface OperationHandler {

    boolean execute();

    OutcomeHandler getOutcomeHandler();
}
