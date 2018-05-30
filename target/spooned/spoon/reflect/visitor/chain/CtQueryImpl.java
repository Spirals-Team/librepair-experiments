/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.reflect.visitor.chain;


/**
 * The facade of {@link CtQuery} which represents a query bound to the {@link CtElement},
 * which is the constant input of this query.
 * It is used by {@link CtElement} implementations of {@link CtQueryable}.
 */
public class CtQueryImpl implements spoon.reflect.visitor.chain.CtQuery {
    /**
     * All the constant inputs of this query.
     */
    private java.util.List<java.lang.Object> inputs;

    private spoon.reflect.visitor.chain.CtQueryImpl.OutputFunctionWrapper outputStep = new spoon.reflect.visitor.chain.CtQueryImpl.OutputFunctionWrapper();

    private spoon.reflect.visitor.chain.CtQueryImpl.AbstractStep lastStep = outputStep;

    private spoon.reflect.visitor.chain.CtQueryImpl.AbstractStep firstStep = lastStep;

    private boolean terminated = false;

    public CtQueryImpl(java.lang.Object... input) {
        setInput(input);
    }

    /**
     *
     *
     * @return list of elements which will be used as input of the query
     */
    public java.util.List<java.lang.Object> getInputs() {
        return (inputs) == null ? java.util.Collections.emptyList() : inputs;
    }

    @java.lang.SuppressWarnings("unchecked")
    @java.lang.Override
    public spoon.reflect.visitor.chain.CtQueryImpl setInput(java.lang.Object... input) {
        if ((inputs) != null) {
            inputs.clear();
        }
        return addInput(input);
    }

    /**
     * adds list of elements which will be used as input of the query too
     *
     * @param input
     * 		
     * @return this to support fluent API
     */
    public spoon.reflect.visitor.chain.CtQueryImpl addInput(java.lang.Object... input) {
        if ((this.inputs) == null) {
            this.inputs = new java.util.ArrayList<>();
        }
        if (input != null) {
            for (java.lang.Object in : input) {
                this.inputs.add(in);
            }
        }
        return this;
    }

    public spoon.reflect.visitor.chain.CtQueryImpl addInput(java.lang.Iterable<?> input) {
        if ((this.inputs) == null) {
            this.inputs = new java.util.ArrayList<>();
        }
        if (input != null) {
            for (java.lang.Object in : input) {
                this.inputs.add(in);
            }
        }
        return this;
    }

    @java.lang.Override
    public <R> void forEach(spoon.reflect.visitor.chain.CtConsumer<R> consumer) {
        outputStep.setNext(consumer);
        for (java.lang.Object input : inputs) {
            firstStep.accept(input);
        }
    }

    @java.lang.SuppressWarnings("unchecked")
    @java.lang.Override
    public <R extends java.lang.Object> java.util.List<R> list() {
        return ((java.util.List<R>) (list(java.lang.Object.class)));
    }

    @java.lang.Override
    public <R> java.util.List<R> list(final java.lang.Class<R> itemClass) {
        final java.util.List<R> list = new java.util.ArrayList<>();
        forEach(new spoon.reflect.visitor.chain.CtConsumer<R>() {
            @java.lang.Override
            public void accept(R out) {
                if ((out != null) && (itemClass.isAssignableFrom(out.getClass()))) {
                    list.add(out);
                }
            }
        });
        return list;
    }

    @java.lang.SuppressWarnings("unchecked")
    @java.lang.Override
    public <R> R first() {
        return ((R) (first(java.lang.Object.class)));
    }

    @java.lang.SuppressWarnings("unchecked")
    @java.lang.Override
    public <R> R first(final java.lang.Class<R> itemClass) {
        final java.lang.Object[] result = new java.lang.Object[1];
        outputStep.setNext(new spoon.reflect.visitor.chain.CtConsumer<R>() {
            @java.lang.Override
            public void accept(R out) {
                if ((out != null) && (itemClass.isAssignableFrom(out.getClass()))) {
                    result[0] = out;
                    terminate();
                }
            }
        });
        for (java.lang.Object input : inputs) {
            firstStep.accept(input);
            if (isTerminated()) {
                break;
            }
        }
        return ((R) (result[0]));
    }

    private boolean logging = false;

    private spoon.reflect.visitor.chain.QueryFailurePolicy failurePolicy = spoon.reflect.visitor.chain.QueryFailurePolicy.FAIL;

    @java.lang.Override
    public <I> spoon.reflect.visitor.chain.CtQueryImpl map(spoon.reflect.visitor.chain.CtConsumableFunction<I> code) {
        addStep(new spoon.reflect.visitor.chain.CtQueryImpl.LazyFunctionWrapper(code));
        return this;
    }

    @java.lang.Override
    public <I, R> spoon.reflect.visitor.chain.CtQueryImpl map(spoon.reflect.visitor.chain.CtFunction<I, R> function) {
        addStep(new spoon.reflect.visitor.chain.CtQueryImpl.FunctionWrapper(function));
        return this;
    }

    @java.lang.Override
    public <R extends spoon.reflect.declaration.CtElement> spoon.reflect.visitor.chain.CtQueryImpl filterChildren(spoon.reflect.visitor.Filter<R> filter) {
        map(new spoon.reflect.visitor.filter.CtScannerFunction());
        if (filter != null) {
            select(filter);
        }
        return this;
    }

    @java.lang.Override
    public <R extends spoon.reflect.declaration.CtElement> spoon.reflect.visitor.chain.CtQueryImpl select(final spoon.reflect.visitor.Filter<R> filter) {
        spoon.reflect.visitor.chain.CtFunction fnc = new spoon.reflect.visitor.chain.CtFunction<R, java.lang.Boolean>() {
            @java.lang.Override
            public java.lang.Boolean apply(R input) {
                return filter.matches(input);
            }
        };
        spoon.reflect.visitor.chain.CtQueryImpl.FunctionWrapper fw = new spoon.reflect.visitor.chain.CtQueryImpl.FunctionWrapper(fnc);
        // set the expected type by real filter and not by helper wrapper above
        fw.onCallbackSet(fnc.getClass().getName(), "apply", filter.getClass(), "matches", 1, 0);
        addStep(fw);
        stepFailurePolicy(spoon.reflect.visitor.chain.QueryFailurePolicy.IGNORE);
        return this;
    }

    @java.lang.Override
    public boolean isTerminated() {
        return terminated;
    }

    @java.lang.Override
    public void terminate() {
        terminated = true;
    }

    /**
     * Evaluates this query, ignoring bound input - if any
     *
     * @param input
     * 		represents the input element of the first mapping function of this query
     * @param outputConsumer
     * 		method accept of the outputConsumer is called for each element produced by last mapping function of this query
     */
    public <I, R> void evaluate(I input, spoon.reflect.visitor.chain.CtConsumer<R> outputConsumer) {
        outputStep.setNext(outputConsumer);
        firstStep.accept(input);
    }

    @java.lang.Override
    public spoon.reflect.visitor.chain.CtQueryImpl name(java.lang.String name) {
        lastStep.setName(name);
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.chain.CtQueryImpl failurePolicy(spoon.reflect.visitor.chain.QueryFailurePolicy policy) {
        failurePolicy = policy;
        return this;
    }

    public spoon.reflect.visitor.chain.CtQueryImpl stepFailurePolicy(spoon.reflect.visitor.chain.QueryFailurePolicy policy) {
        lastStep.setLocalFailurePolicy(policy);
        return this;
    }

    /**
     * Enable/disable logging for this query
     *
     * Note: it is not possible to enable logging of all queries globally by Launcher.LOGGER.isDebugEnabled()
     * because it causes StackOverflow.
     * Reason: Query chains are used internally during writing of log messages too. So it would write logs for ever...
     */
    public spoon.reflect.visitor.chain.CtQueryImpl logging(boolean logging) {
        this.logging = logging;
        return this;
    }

    protected void handleListenerSetQuery(java.lang.Object target) {
        if (target instanceof spoon.reflect.visitor.chain.CtQueryAware) {
            ((spoon.reflect.visitor.chain.CtQueryAware) (target)).setQuery(this);
        }
    }

    private void addStep(spoon.reflect.visitor.chain.CtQueryImpl.AbstractStep step) {
        step.nextStep = outputStep;
        lastStep.nextStep = step;
        lastStep = step;
        if ((firstStep) == (outputStep)) {
            firstStep = step;
        }
        step.setName(java.lang.String.valueOf(((getStepIndex(step)) + 1)));
    }

    private int getStepIndex(spoon.reflect.visitor.chain.CtQueryImpl.AbstractStep step) {
        int idx = 0;
        spoon.reflect.visitor.chain.CtQueryImpl.AbstractStep s = firstStep;
        while (s != (outputStep)) {
            if (s == step) {
                return idx;
            }
            s = ((spoon.reflect.visitor.chain.CtQueryImpl.AbstractStep) (s.nextStep));
            idx++;
        } 
        return -1;
    }

    private boolean isLogging() {
        return logging;
    }

    private void log(spoon.reflect.visitor.chain.CtQueryImpl.AbstractStep step, java.lang.String message, java.lang.Object... parameters) {
        if ((isLogging()) && (spoon.Launcher.LOGGER.isInfoEnabled())) {
            spoon.Launcher.LOGGER.info(getStepDescription(step, message, parameters));
        }
    }

    private java.lang.String getStepDescription(spoon.reflect.visitor.chain.CtQueryImpl.AbstractStep step, java.lang.String message, java.lang.Object... parameters) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("Step ");
        sb.append(step.getName()).append(") ");
        sb.append(message);
        for (int i = 0; i < (parameters.length); i++) {
            sb.append("\nParameter ").append((i + 1)).append(") ");
            if ((parameters[i]) != null) {
                sb.append(parameters[i].getClass().getSimpleName());
                sb.append(": ");
            }
            sb.append(parameters[i]);
        }
        return sb.toString();
    }

    /**
     * Holds optional name and local QueryFailurePolicy of each step
     */
    private abstract class AbstractStep implements spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> {
        java.lang.String name;

        spoon.reflect.visitor.chain.QueryFailurePolicy localFailurePolicy = null;

        spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> nextStep;

        java.lang.Class<?> expectedClass;

        java.lang.String cceStacktraceClass;

        java.lang.String cceStacktraceMethodName;

        @java.lang.Override
        public final void accept(java.lang.Object input) {
            if ((input == null) || (isTerminated())) {
                return;
            }
            if ((isAcceptableType(input)) == false) {
                return;
            }
            java.lang.Object result;
            try {
                result = _accept(input);
            } catch (java.lang.ClassCastException e) {
                onClassCastException(e, input);
                return;
            }
            if ((result == null) || (isTerminated())) {
                return;
            }
            handleResult(result, input);
        }

        protected abstract java.lang.Object _accept(java.lang.Object input);

        protected void handleResult(java.lang.Object result, java.lang.Object input) {
        }

        /**
         *
         *
         * @return name of this Step - for debugging purposes
         */
        private java.lang.String getName() {
            return name;
        }

        /**
         *
         *
         * @param name
         * 		of the step - for debugging purposes
         */
        private void setName(java.lang.String name) {
            this.name = name;
        }

        /**
         *
         *
         * @return true if this step should throw {@link ClassCastException} in case of
         * step input type incompatibility
         */
        private boolean isFailOnCCE() {
            if ((localFailurePolicy) != null) {
                return (localFailurePolicy) == (spoon.reflect.visitor.chain.QueryFailurePolicy.FAIL);
            }else {
                return (failurePolicy) == (spoon.reflect.visitor.chain.QueryFailurePolicy.FAIL);
            }
        }

        private void setLocalFailurePolicy(spoon.reflect.visitor.chain.QueryFailurePolicy localFailurePolicy) {
            this.localFailurePolicy = localFailurePolicy;
        }

        /**
         * check whether `input` can be used to call a function.
         *
         * @param input
         * 		the to be checked value
         * @return true if it can be used or if we do not know that yet
         */
        protected boolean isAcceptableType(java.lang.Object input) {
            if (isFailOnCCE()) {
                // do not check type if it has to fail on cce
                return true;
            }
            if (((expectedClass) != null) && ((expectedClass.isAssignableFrom(input.getClass())) == false)) {
                log(this, (((input.getClass().getName()) + " cannot be cast to ") + (expectedClass.getName())), input);
                return false;
            }
            return true;
        }

        /**
         * Sets up type checking following the type of input parameter of callback method
         *
         * @param stackClass
         * 		- name of class of method in the stacktrace, if ClassCastException is thrown on the input parameter of lambda expression
         * @param stackMethodName
         * 		- name of method in the stacktrace, if ClassCastException is thrown on the input parameter of lambda expression
         * @param callbackClass
         * 		- the class of callback method
         * @param callbackMethod
         * 		- the name of callback method
         * @param nrOfParams
         * 		- total number of input parameters of callback method
         * @param idxOfInputParam
         * 		- index of input parameter, whose type has to be checked
         */
        protected void onCallbackSet(java.lang.String stackClass, java.lang.String stackMethodName, java.lang.Class<?> callbackClass, java.lang.String callbackMethod, int nrOfParams, int idxOfInputParam) {
            this.cceStacktraceClass = stackClass;
            this.cceStacktraceMethodName = stackMethodName;
            if ((callbackClass.getName().indexOf("$$Lambda$")) >= 0) {
                // lambda expressions does not provide runtime information about type of input parameter
                // clear it now. We can detect input type from first ClassCastException
                this.expectedClass = null;
            }else {
                java.lang.reflect.Method method = spoon.support.util.RtHelper.getMethod(callbackClass, callbackMethod, nrOfParams);
                if (method == null) {
                    throw new spoon.SpoonException(((("The method " + callbackMethod) + " with one parameter was not found on the class ") + (callbackClass.getName())));
                }
                this.expectedClass = ((java.lang.Class<?>) (method.getParameterTypes()[idxOfInputParam]));
            }
        }

        /**
         * Is used to log that invocation was not processed
         *
         * @param e
         * 		- the CCE caught during last call of callback
         * @param input
         * 		- the value sent as input to last call of callback
         */
        protected void onClassCastException(java.lang.ClassCastException e, java.lang.Object input) {
            if ((isFailOnCCE()) || ((expectedClass) != null)) {
                // expected class is known so it was checked before the call, so the CCE must be thrown by something else. Report it directly as it is. It is bug in client's code
                throw e;
            }
            if ((spoon.reflect.visitor.chain.CtQueryImpl.indexOfCallerInStack) < 0) {
                // this is an exotic JVM, where we cannot detect type of parameter of Lambda expression
                // Silently ignore this CCE, which was may be expected or may be problem in client's code.
                // if (Launcher.LOGGER.isDebugEnabled()) {
                // Launcher.LOGGER.debug("ClassCastException thrown by client's code or Query engine ...", e);
                // }
                return;
            }
            // we can detect whether CCE was thrown in client's code (unexpected - must be rethrown) or Query engine (expected - has to be ignored)
            java.lang.StackTraceElement[] stackEles = e.getStackTrace();
            if ((stackEles.length) == 0) {
                /* The java runtime detected that this ClassCastException is thrown often and recompiled code to use faster pre-alocated exception,
                which doesn't provide stacktrace.
                So exceptions, which doesn't provide stacktrace can be ignored too, because they were already ignored before many times.

                See http://www.oracle.com/technetwork/java/javase/relnotes-139183.html#vm
                ---------------------------------------------------------------------------------------------------------------
                The compiler in the server VM now provides correct stack backtraces for all "cold" built-in exceptions.
                For performance purposes, when such an exception is thrown a few times, the method may be recompiled.
                After recompilation, the compiler may choose a faster tactic using preallocated exceptions that do not provide a stack trace.
                To disable completely the use of preallocated exceptions, use this new flag: -XX:-OmitStackTraceInFastThrow.
                ---------------------------------------------------------------------------------------------------------------
                 */
                return;
            }
            java.lang.StackTraceElement stackEle = stackEles[spoon.reflect.visitor.chain.CtQueryImpl.indexOfCallerInStack];
            if ((stackEle.getMethodName().equals(cceStacktraceMethodName)) && (stackEle.getClassName().equals(cceStacktraceClass))) {
                /* the CCE exception was thrown in the expected method - OK, it can be ignored
                Detect type of parameter of Lambda expression from the CCE message and store it in expectedClass
                so we can check expected type before next call and to avoid slow throwing of ClassCastException
                 */
                expectedClass = spoon.reflect.visitor.chain.CtQueryImpl.detectTargetClassFromCCE(e, input);
                if ((expectedClass) == null) {
                    /* It wasn't able to detect expected class from the CCE.
                    OK, so we cannot optimize next call and we have to let JVM to throw next CCE, but it is only performance problem. Not functional.
                     */
                }
                log(this, e.getMessage(), input);
                return;
            }
            // Do not ignore this exception in client's code. It is not expected. It cannot be ignored.
            throw e;
        }
    }

    /**
     * Wrapper around terminal {@link CtConsumer}, which accepts output of this query
     */
    private class OutputFunctionWrapper extends spoon.reflect.visitor.chain.CtQueryImpl.AbstractStep {
        OutputFunctionWrapper() {
            super();
            localFailurePolicy = spoon.reflect.visitor.chain.QueryFailurePolicy.IGNORE;
        }

        @java.lang.Override
        protected java.lang.Object _accept(java.lang.Object element) {
            nextStep.accept(element);
            return null;
        }

        @java.lang.SuppressWarnings({ "unchecked", "rawtypes" })
        <R> void setNext(spoon.reflect.visitor.chain.CtConsumer<R> out) {
            // we are preparing new query execution.
            reset();
            nextStep = ((spoon.reflect.visitor.chain.CtConsumer) (out));
            handleListenerSetQuery(nextStep);
            onCallbackSet(this.getClass().getName(), "_accept", nextStep.getClass(), "accept", 1, 0);
        }
    }

    /**
     * Called before query is evaluated again
     */
    protected void reset() {
        terminated = false;
    }

    private class LazyFunctionWrapper extends spoon.reflect.visitor.chain.CtQueryImpl.AbstractStep {
        private final spoon.reflect.visitor.chain.CtConsumableFunction<java.lang.Object> fnc;

        @java.lang.SuppressWarnings("unchecked")
        LazyFunctionWrapper(spoon.reflect.visitor.chain.CtConsumableFunction<?> fnc) {
            super();
            this.fnc = ((spoon.reflect.visitor.chain.CtConsumableFunction<java.lang.Object>) (fnc));
            handleListenerSetQuery(this.fnc);
            onCallbackSet(this.getClass().getName(), "_accept", fnc.getClass(), "apply", 2, 0);
        }

        @java.lang.Override
        protected java.lang.Object _accept(java.lang.Object input) {
            fnc.apply(input, nextStep);
            return null;
        }
    }

    /**
     * a step which calls Function. Implements contract of {@link CtQuery#map(CtFunction)}
     */
    private class FunctionWrapper extends spoon.reflect.visitor.chain.CtQueryImpl.AbstractStep {
        private final spoon.reflect.visitor.chain.CtFunction<java.lang.Object, java.lang.Object> fnc;

        @java.lang.SuppressWarnings("unchecked")
        FunctionWrapper(spoon.reflect.visitor.chain.CtFunction<?, ?> code) {
            super();
            fnc = ((spoon.reflect.visitor.chain.CtFunction<java.lang.Object, java.lang.Object>) (code));
            handleListenerSetQuery(fnc);
            onCallbackSet(this.getClass().getName(), "_accept", fnc.getClass(), "apply", 1, 0);
        }

        @java.lang.Override
        protected java.lang.Object _accept(java.lang.Object input) {
            return fnc.apply(input);
        }

        @java.lang.Override
        protected void handleResult(java.lang.Object result, java.lang.Object input) {
            if (result instanceof java.lang.Boolean) {
                // the code is a predicate. send the input to output if result is true
                if (((java.lang.Boolean) (result))) {
                    nextStep.accept(input);
                }else {
                    log(this, "Skipped element, because CtFunction#accept(input) returned false", input);
                }
                return;
            }
            if (result instanceof java.lang.Iterable) {
                // send each item of Iterable to the next step
                for (java.lang.Object out : ((java.lang.Iterable<java.lang.Object>) (result))) {
                    nextStep.accept(out);
                    if (isTerminated()) {
                        return;
                    }
                }
                return;
            }
            if (result.getClass().isArray()) {
                // send each item of Array to the next step
                for (int i = 0; i < (java.lang.reflect.Array.getLength(result)); i++) {
                    nextStep.accept(java.lang.reflect.Array.get(result, i));
                    if (isTerminated()) {
                        return;
                    }
                }
                return;
            }
            nextStep.accept(result);
        }
    }

    private static final java.lang.String JDK9_BASE_PREFIX = "java.base/";

    private static final java.util.regex.Pattern cceMessagePattern = java.util.regex.Pattern.compile("(\\S+) cannot be cast to (\\S+)");

    private static final int indexOfCallerInStack = spoon.reflect.visitor.chain.CtQueryImpl.getIndexOfCallerInStackOfLambda();

    /**
     * JVM implementations reports exception in call of lambda in different way.
     * A) the to be called lambda expression whose input parameters are invalid is on top of stack trace
     * B) the to be called lambda expression whose input parameters are invalid is NOT in stack trace at all
     *
     * This method detects actual behavior of JVM, so the code, which decides whether ClassCastException is expected (part of filtering process)
     * or unexpected - thrown by clients wrong code works on all JVM implementations
     */
    private static int getIndexOfCallerInStackOfLambda() {
        spoon.reflect.visitor.chain.CtConsumer<spoon.reflect.declaration.CtType<?>> f = (spoon.reflect.declaration.CtType<?> t) -> {
        };
        spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> unchecked = ((spoon.reflect.visitor.chain.CtConsumer) (f));
        java.lang.Object obj = new java.lang.Integer(1);
        try {
            unchecked.accept(obj);
            throw new spoon.SpoonException("The lambda expression with input type CtType must throw ClassCastException when input type is Integer. Basic CtQuery contract is violated by JVM!");
        } catch (java.lang.ClassCastException e) {
            java.lang.StackTraceElement[] stack = e.getStackTrace();
            for (int i = 0; i < (stack.length); i++) {
                if ("getIndexOfCallerInStackOfLambda".equals(stack[i].getMethodName())) {
                    // check whether we can detect type of lambda input parameter from CCE
                    java.lang.Class<?> detectectedClass = spoon.reflect.visitor.chain.CtQueryImpl.detectTargetClassFromCCE(e, obj);
                    if ((detectectedClass == null) || ((spoon.reflect.declaration.CtType.class.equals(detectectedClass)) == false)) {
                        // we cannot detect type of lambda input parameter from ClassCastException on this JVM implementation
                        // mark it by negative index, so the query engine will fall back to eating of all CCEs and slow implementation
                        return -1;
                    }
                    return i;
                }
            }
            throw new spoon.SpoonException("Spoon cannot detect index of caller of lambda expression in stack trace.", e);
        }
    }

    private static java.lang.Class<?> detectTargetClassFromCCE(java.lang.ClassCastException e, java.lang.Object input) {
        // detect expected class from CCE message, because we have to quickly and silently ignore elements of other types
        java.lang.String message = e.getMessage();
        if (message != null) {
            java.util.regex.Matcher m = spoon.reflect.visitor.chain.CtQueryImpl.cceMessagePattern.matcher(message);
            if (m.matches()) {
                java.lang.String objectClassName = m.group(1);
                java.lang.String expectedClassName = m.group(2);
                if (objectClassName.startsWith(spoon.reflect.visitor.chain.CtQueryImpl.JDK9_BASE_PREFIX)) {
                    objectClassName = objectClassName.substring(spoon.reflect.visitor.chain.CtQueryImpl.JDK9_BASE_PREFIX.length());
                }
                if (objectClassName.equals(input.getClass().getName())) {
                    try {
                        return java.lang.Class.forName(expectedClassName);
                    } catch (java.lang.ClassNotFoundException e1) {
                        /* It wasn't able to load the expected class from the CCE.
                        OK, so we cannot optimize next call and we have to let JVM to throw next CCE, but it is only performance problem. Not functional.
                         */
                    }
                }
            }
        }
        return null;
    }
}

