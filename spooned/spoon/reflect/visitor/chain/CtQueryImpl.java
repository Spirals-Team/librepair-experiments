package spoon.reflect.visitor.chain;


public class CtQueryImpl implements spoon.reflect.visitor.chain.CtQuery {
    private java.util.List<java.lang.Object> inputs;

    private spoon.reflect.visitor.chain.CtQueryImpl.OutputFunctionWrapper outputStep = new spoon.reflect.visitor.chain.CtQueryImpl.OutputFunctionWrapper();

    private spoon.reflect.visitor.chain.CtQueryImpl.AbstractStep lastStep = outputStep;

    private spoon.reflect.visitor.chain.CtQueryImpl.AbstractStep firstStep = lastStep;

    private boolean terminated = false;

    public CtQueryImpl(java.lang.Object... input) {
        setInput(input);
    }

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

        private java.lang.String getName() {
            return name;
        }

        private void setName(java.lang.String name) {
            this.name = name;
        }

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

        protected boolean isAcceptableType(java.lang.Object input) {
            if (isFailOnCCE()) {
                return true;
            }
            if (((expectedClass) != null) && ((expectedClass.isAssignableFrom(input.getClass())) == false)) {
                log(this, (((input.getClass().getName()) + " cannot be cast to ") + (expectedClass.getName())), input);
                return false;
            }
            return true;
        }

        protected void onCallbackSet(java.lang.String stackClass, java.lang.String stackMethodName, java.lang.Class<?> callbackClass, java.lang.String callbackMethod, int nrOfParams, int idxOfInputParam) {
            this.cceStacktraceClass = stackClass;
            this.cceStacktraceMethodName = stackMethodName;
            if ((callbackClass.getName().indexOf("$$Lambda$")) >= 0) {
                this.expectedClass = null;
            }else {
                java.lang.reflect.Method method = spoon.support.util.RtHelper.getMethod(callbackClass, callbackMethod, nrOfParams);
                if (method == null) {
                    throw new spoon.SpoonException(((("The method " + callbackMethod) + " with one parameter was not found on the class ") + (callbackClass.getName())));
                }
                this.expectedClass = ((java.lang.Class<?>) (method.getParameterTypes()[idxOfInputParam]));
            }
        }

        protected void onClassCastException(java.lang.ClassCastException e, java.lang.Object input) {
            if ((isFailOnCCE()) || ((expectedClass) != null)) {
                throw e;
            }
            if ((spoon.reflect.visitor.chain.CtQueryImpl.indexOfCallerInStack) < 0) {
                return;
            }
            java.lang.StackTraceElement[] stackEles = e.getStackTrace();
            if ((stackEles.length) == 0) {
                return;
            }
            java.lang.StackTraceElement stackEle = stackEles[spoon.reflect.visitor.chain.CtQueryImpl.indexOfCallerInStack];
            if ((stackEle.getMethodName().equals(cceStacktraceMethodName)) && (stackEle.getClassName().equals(cceStacktraceClass))) {
                expectedClass = spoon.reflect.visitor.chain.CtQueryImpl.detectTargetClassFromCCE(e, input);
                if ((expectedClass) == null) {
                }
                log(this, e.getMessage(), input);
                return;
            }
            throw e;
        }
    }

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
            reset();
            nextStep = ((spoon.reflect.visitor.chain.CtConsumer) (out));
            handleListenerSetQuery(nextStep);
            onCallbackSet(this.getClass().getName(), "_accept", nextStep.getClass(), "accept", 1, 0);
        }
    }

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
                if (((java.lang.Boolean) (result))) {
                    nextStep.accept(input);
                }else {
                    log(this, "Skipped element, because CtFunction#accept(input) returned false", input);
                }
                return;
            }
            if (result instanceof java.lang.Iterable) {
                for (java.lang.Object out : ((java.lang.Iterable<java.lang.Object>) (result))) {
                    nextStep.accept(out);
                    if (isTerminated()) {
                        return;
                    }
                }
                return;
            }
            if (result.getClass().isArray()) {
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
                    java.lang.Class<?> detectectedClass = spoon.reflect.visitor.chain.CtQueryImpl.detectTargetClassFromCCE(e, obj);
                    if ((detectectedClass == null) || ((spoon.reflect.declaration.CtType.class.equals(detectectedClass)) == false)) {
                        return -1;
                    }
                    return i;
                }
            }
            throw new spoon.SpoonException("Spoon cannot detect index of caller of lambda expression in stack trace.", e);
        }
    }

    private static java.lang.Class<?> detectTargetClassFromCCE(java.lang.ClassCastException e, java.lang.Object input) {
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
                    }
                }
            }
        }
        return null;
    }
}

