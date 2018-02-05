package io.descoped.client.api.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import io.descoped.client.api.builder.intf.OperationHandler;
import io.descoped.client.api.builder.intf.OutcomeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 23/11/2017
 */
public class OperationCommand extends HystrixCommand<OutcomeHandler> {

    private static Logger log = LoggerFactory.getLogger(OperationCommand.class);
    private final OperationHandler operationHandler;
    private String error;
//    private final OutcomeHandler outcomeHandler;

    public OperationCommand(OperationHandler operationHandler) {
        super(HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("OperationCommand"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(300000))
                .andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.Setter()
                                .withMaxQueueSize(100)
                                .withQueueSizeRejectionThreshold(100)
                                .withCoreSize(4)));

        this.operationHandler = operationHandler;

        HystrixRequestContext.initializeContext();
    }

    @Override
    protected OutcomeHandler run() throws Exception {
        try {
            boolean ok = operationHandler.execute();
            return operationHandler.getOutcomeHandler();
        } catch (Exception e) {
            error = e.getMessage();
            throw e;
        }
    }

    @Override
    protected OutcomeHandler getFallback() {
        log.error(error);;
        return null;
    }
}
