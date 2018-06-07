package io.apiman.gateway.engine;

import io.apiman.gateway.engine.async.IAsyncHandler;
import io.apiman.gateway.engine.impl.ApiRequestExecutorImpl;
import io.apiman.gateway.engine.io.ISignalWriteStream;

/**
 * IPolicyRequestExecutor interface.
 * 
 * @author Marc Savy <msavy@redhat.com>
 *
 * @see ApiRequestExecutorImpl
 */
public interface IApiRequestExecutor {

    /**
     * Execute the policy chain and request.
     */
    void execute();

    /**
     * Policy request-response sequence has completed.
     * 
     * @return true if finished, else false.
     */
    boolean isFinished();

    /**
     * Indicates when the back-end connector is ready to handle streamed data.
     *
     * @param handler
     */
    void streamHandler(IAsyncHandler<ISignalWriteStream> handler);

}
