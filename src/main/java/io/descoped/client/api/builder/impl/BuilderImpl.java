package io.descoped.client.api.builder.impl;

import io.descoped.client.api.builder.Builder;
import io.descoped.client.api.builder.Worker;
import io.descoped.client.api.builder.intf.OperationHandler;
import io.descoped.client.api.builder.intf.OutcomeHandler;
import io.descoped.client.api.command.OperationCommand;
import io.descoped.client.exception.APIClientException;
import io.descoped.reflection.proxy.ClassProxy;
import io.descoped.reflection.proxy.ConstructorProxy;
import io.descoped.reflection.proxy.ObjectProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class BuilderImpl implements Builder {

    private static final Logger log = LoggerFactory.getLogger(Builder.class);

    private List<Worker> workers = new ArrayList<>();

    public BuilderImpl() {
    }

    @Override
    public Worker worker(String id) {
        Worker worker = new WorkerImpl(this, id);
        workers.add(worker);
        return worker;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    private <T> T construct(ClassProxy<T> proxy, Object instance) {
        List<? extends ConstructorProxy<?>> constructors = proxy.constructors();
        for (ConstructorProxy<?> constructorProxy : constructors) {
            if (constructorProxy.getParameterCount() == 1) {
                Class<?> paramType = constructorProxy.getParameterTypes().get(0);
                // paramType class isAssignable from instance
                if (paramType.isAssignableFrom(instance.getClass())) {
                    return (T) constructorProxy.construct(instance);
                }
                // paramType interfaces' isAssignable from instance
                Class<?>[] interfaces = paramType.getInterfaces();
                if (interfaces != null && interfaces.length > 0) {
                    for (Class<?> intf : interfaces) {
                        if (intf.isAssignableFrom(instance.getClass())) {
                            return (T) constructorProxy.construct(instance);
                        }
                    }
                }
            }
        }
        return proxy.constructor().construct(instance);
    }

    @Override
    public Builder execute() {
        for (Worker worker : getWorkers()) {
            String id = ((WorkerImpl) worker).getId();

            Class<? extends OutcomeHandler> outcomeHandlerClass = ((WorkerImpl) worker).getOutcome();
            ClassProxy<OutcomeHandler> outcomeHandlerClassProxy = new ClassProxy<>((Class<OutcomeHandler>) outcomeHandlerClass);
            ObjectProxy<OutcomeHandler> outcomeHandlerObjectProxy = outcomeHandlerClassProxy.construct();
            OutcomeHandler outcomeHandler = outcomeHandlerObjectProxy.getInstance();

            Class<? extends OperationHandler> operationHandlerClass = ((WorkerImpl) worker).getOperation();
            ClassProxy<OperationHandler> operationHandlerClassProxy = new ClassProxy<>((Class<OperationHandler>) operationHandlerClass);
            OperationHandler operationHandler = construct(operationHandlerClassProxy, outcomeHandler);

            // todo: this should be sent to an executor service with delayed schedule for failing tasks
            // todo: handling of outcome needs to be investigated

            OperationCommand operationCommand = new OperationCommand(operationHandler);
            log.trace("Execute operation: {}", id);
            try {
                OutcomeHandler result = operationCommand.execute();
                log.trace("Outcome for: {}\n{}", id, result);
            } catch (Exception e) {
                throw new APIClientException(e);
            }
        }
        return this;
    }

}
