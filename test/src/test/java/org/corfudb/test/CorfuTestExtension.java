package org.corfudb.test;

import static org.corfudb.test.parameters.Servers.SERVER_0;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.Getter;
import org.corfudb.comm.ChannelImplementation;
import org.corfudb.infrastructure.CorfuServer;
import org.corfudb.infrastructure.ServerContext;
import org.corfudb.runtime.CorfuRuntime;
import org.corfudb.runtime.CorfuRuntime.CorfuRuntimeParameters;
import org.corfudb.runtime.exceptions.unrecoverable.UnrecoverableCorfuError;
import org.corfudb.runtime.view.Layout;
import org.corfudb.runtime.view.ObjectBuilder;
import org.corfudb.runtime.view.ObjectOpenOptions;
import org.corfudb.test.concurrent.ConcurrentScheduler;
import org.corfudb.test.concurrent.ConcurrentStateMachine;
import org.corfudb.test.console.TestConsole;
import org.corfudb.test.parameters.AnnotatedParameterGenerator;
import org.corfudb.test.parameters.CorfuObjectParameter;
import org.corfudb.test.parameters.LayoutProvider;
import org.corfudb.test.parameters.Param;
import org.corfudb.test.parameters.Parameter;
import org.corfudb.test.parameters.Server;
import org.corfudb.test.parameters.TypedParameterGenerator;
import org.corfudb.util.NodeLocator;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import org.slf4j.LoggerFactory;

public class CorfuTestExtension implements
    TestInstancePostProcessor, BeforeAllCallback, BeforeTestExecutionCallback,
    BeforeEachCallback, ExecutionCondition,
    AfterTestExecutionCallback, AfterAllCallback, ParameterResolver,
    TestTemplateInvocationContextProvider
{
    public static final Namespace NAMESPACE = Namespace.create(CorfuTestExtension.class);
    public static final String CONSOLE = "CONSOLE";
    public static final String TEST_INSTANCE = "TEST_INSTANCE";
    public static final String GLOBAL_TEST_INSTANCE = "GLOBAL_TEST_INSTANCE";
    public static final String LOGGER = "LOGGER";

    public static class CorfuGlobalTestInstance {

        @Getter
        final EventLoopGroup bossGroup;

        @Getter
        final EventLoopGroup workerGroup;

        @Getter
        final EventLoopGroup clientGroup;

        @Getter
        final EventLoopGroup runtimeGroup;

        public CorfuGlobalTestInstance(@Nonnull ExtensionContext extensionContext) {
            bossGroup = new DefaultEventLoopGroup(1,
                new ThreadFactoryBuilder()
                    .setNameFormat("boss-%d")
                    .setDaemon(true)
                    .build());
            int numThreads = Runtime.getRuntime().availableProcessors() * 2;

            workerGroup = new DefaultEventLoopGroup(numThreads,
                new ThreadFactoryBuilder()
                    .setNameFormat("worker-%d")
                    .setDaemon(true)
                    .build());

            clientGroup = new DefaultEventLoopGroup(numThreads,
                new ThreadFactoryBuilder()
                    .setNameFormat("client-%d")
                    .setDaemon(true)
                    .build());

            runtimeGroup = new DefaultEventLoopGroup(numThreads,
                new ThreadFactoryBuilder()
                    .setNameFormat("netty-%d")
                    .setDaemon(true)
                    .build());
        }

        void shutdown() {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            clientGroup.shutdownGracefully();
            runtimeGroup.shutdownGracefully();
        }
    }

    public static class CorfuTestInstance {

        final Map<NodeLocator, CorfuServer> serverMap;
        final List<CorfuRuntime> runtimeList;

        public CorfuTestInstance(@Nonnull ExtensionContext extensionContext) {
            final CorfuGlobalTestInstance globalInstance =
                extensionContext.getStore(NAMESPACE)
                    .get(GLOBAL_TEST_INSTANCE, CorfuGlobalTestInstance.class);

            serverMap = new HashMap<>();
            runtimeList = new ArrayList<>();

            // If there are no server configurations present,
            // and there is at least one runtime, generate a
            // default single-node configuration.
            if (testContainsParameter(extensionContext, CorfuRuntime.class)
                && !testContainsParameter(extensionContext, CorfuServer.class)) {
                CorfuServer singleServer =
                    new CorfuServer(new ServerContext(ServerOptionsMap
                                                    .builder()
                                                    .port("0")
                                                    .bossGroup(globalInstance.getBossGroup())
                                                    .workerGroup(globalInstance.getWorkerGroup())
                                                    .clientGroup(globalInstance.getClientGroup())
                                                    .build().toMap()));
                singleServer.start();
                serverMap.put(SERVER_0.getLocator(), singleServer);
            }
        }

        void registerRuntime(@Nonnull CorfuRuntime runtime) {
            runtimeList.add(runtime);
        }

        @Nullable CorfuRuntime getLastRuntime() {
            if (runtimeList.size() == 0) {
                return null;
            }
            return runtimeList.get(runtimeList.size() - 1);
        }

        void shutdown() {
            runtimeList.forEach(CorfuRuntime::shutdown);
            serverMap.values().forEach(CorfuServer::close);
        }

        public static boolean testContainsParameter(@Nonnull ExtensionContext extensionContext,
                                                    @Nonnull Class<?> type) {
            return Arrays.stream(extensionContext.getRequiredTestMethod().getParameterTypes())
                .anyMatch(e -> e.equals(type));
        }
    }

    @FunctionalInterface
    interface ParameterGeneratorMethod {
        Object generate(@Nonnull CorfuTestExtension testExtension,
                        @Nonnull ParameterContext parameterContext,
                        @Nonnull ExtensionContext extensionContext)
            throws IllegalAccessException, InvocationTargetException;
    }

    @FunctionalInterface
    interface AnnotatedParameterGeneratorMethod {
        Object generate(@Nonnull CorfuTestExtension testExtension,
            @Nonnull Annotation annotation,
            @Nonnull ParameterContext parameterContext,
            @Nonnull ExtensionContext extensionContext)
            throws IllegalAccessException, InvocationTargetException;
    }

    private final Map<Class<?>, ParameterGeneratorMethod> typedParameterGenerators =
        Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(TypedParameterGenerator.class))
                .collect(Collectors.toMap(Method::getReturnType, m -> m::invoke));

    private final Map<Class<? extends Annotation>,
                    AnnotatedParameterGeneratorMethod> annotatedParameterGenerators =
        Arrays.stream(this.getClass().getDeclaredMethods())
            .filter(m -> m.isAnnotationPresent(AnnotatedParameterGenerator.class))
            .collect(Collectors.toMap(m ->
                m.getAnnotation(AnnotatedParameterGenerator.class).value(), m -> m::invoke));

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        // Shutdown and remove the global test instance.
        extensionContext.getStore(NAMESPACE)
            .get(GLOBAL_TEST_INSTANCE, CorfuGlobalTestInstance.class).shutdown();
        extensionContext.getStore(NAMESPACE).remove(GLOBAL_TEST_INSTANCE);
    }

    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {
        final TestConsole console = getConsole(extensionContext);
        console.endTestExecution(extensionContext);

        // Shutdown and remove the test instance.
        extensionContext.getStore(NAMESPACE).get(TEST_INSTANCE, CorfuTestInstance.class).shutdown();
        extensionContext.getStore(NAMESPACE).remove(TEST_INSTANCE);

        extensionContext.getStore(NAMESPACE).get(LOGGER, MemoryAppender.class)
            .reset();
        extensionContext.getStore(NAMESPACE).remove(LOGGER);


        getConsole(extensionContext).shutdown();
        extensionContext.getStore(NAMESPACE).remove(CONSOLE);
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        extensionContext.getStore(NAMESPACE)
            .put(GLOBAL_TEST_INSTANCE, new CorfuGlobalTestInstance(extensionContext));
        // Due to old test suite, routerFn might be overridden, disable it.
        CorfuRuntime.overrideGetRouterFunction = null;
    }

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        if (!extensionContext.getRequiredTestClass().isAnnotationPresent(CorfuTest.class)) {
            throw new UnrecoverableCorfuError("Test class must be annotated with @CorfuTest!");
        }
    }

    @Override
    public void beforeTestExecution(@Nonnull ExtensionContext extensionContext) throws Exception {
        final TestConsole console = getConsole(extensionContext);
        console.startTestExecution(extensionContext);

        extensionContext.getStore(NAMESPACE)
            .put(TEST_INSTANCE, new CorfuTestInstance(extensionContext));

        LoggerContext lc = (LoggerContext) LoggerFactory
            .getILoggerFactory();

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(lc);
        encoder.setPattern("%d{HH:mm:ss.SSS} %highlight(%-5level) "
            + "[%thread] %cyan(%logger{15}) - %msg%n %ex{3}");
        encoder.start();

        MemoryAppender<ILoggingEvent> appender =
            new MemoryAppender<>(Param.LOG_BUFFER.getValue(int.class), encoder);
        Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        appender.setContext(lc);
        appender.start();
        logger.addAppender(appender);

        extensionContext.getStore(NAMESPACE)
            .put(LOGGER, appender);
    }

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext extensionContext) {
        return ConditionEvaluationResult.enabled("");
    }

    @Override
    public boolean supportsParameter(
        @Nonnull ParameterContext parameterContext,
        @Nonnull ExtensionContext extensionContext) throws ParameterResolutionException {

        final java.lang.reflect.Parameter parameter = parameterContext.getParameter();
        for (Class<? extends Annotation> a : annotatedParameterGenerators.keySet()) {
            if (parameter.isAnnotationPresent(a)) {
                return true;
            }
        }

        return typedParameterGenerators.get(parameter.getType()) != null;
    }

    @Override
    public Object resolveParameter(
        @Nonnull ParameterContext parameterContext,
        @Nonnull ExtensionContext extensionContext) throws ParameterResolutionException {

        try {
            final java.lang.reflect.Parameter parameter = parameterContext.getParameter();
            for (Class<? extends Annotation> a : annotatedParameterGenerators.keySet()) {
                if (parameter.isAnnotationPresent(a)) {
                    return annotatedParameterGenerators.get(a)
                        .generate(this, parameter.getAnnotation(a),
                            parameterContext, extensionContext);
                }
            }

            ParameterGeneratorMethod generatorMethod =
                typedParameterGenerators.get(parameterContext.getParameter().getType());

            if (generatorMethod != null) {
                return generatorMethod
                    .generate(this, parameterContext, extensionContext);
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new UnrecoverableCorfuError(e);
        }

        return null;
    }

    @AnnotatedParameterGenerator(Parameter.class)
    private @Nonnull Object resolveParameterAnnotation(
        @Nonnull Parameter annotation,
        @Nonnull ParameterContext parameterContext,
        @Nonnull ExtensionContext extensionContext) throws ParameterResolutionException {
        Object value = annotation.value().getValue();

        if (value.getClass().equals(Integer.class)
            && parameterContext.getParameter().getType().equals(int.class)) {
            // unbox integer
            return value;
        } else if (value.getClass().equals(Long.class)
            && parameterContext.getParameter().getType().equals(long.class)) {
            // and longs
            return value;
        } else if (!parameterContext.getParameter().getType().equals(value.getClass())) {
            // otherwise no match
            throw new ParameterResolutionException(annotation.value().toString()
            + " should be of type " + value.getClass() + " but parameter is of type "
            + parameterContext.getParameter().getType());
        }
        return value;
    }

    @AnnotatedParameterGenerator(CorfuObjectParameter.class)
    private @Nonnull Object resolveCorfuObjectAnnotation(
        @Nonnull CorfuObjectParameter annotation,
        @Nonnull ParameterContext parameterContext,
        @Nonnull ExtensionContext extensionContext) throws ParameterResolutionException {

        final CorfuTestInstance testInstance =
            extensionContext.getStore(NAMESPACE)
                .get(TEST_INSTANCE, CorfuTestInstance.class);

        CorfuRuntime runtime = testInstance.getLastRuntime();
        if (runtime == null) {
            throw new ParameterResolutionException("@CorfuObjectParameter must come AFTER "
            + " a CorfuRuntime parameter!");
        }

        ObjectBuilder<?> builder = runtime.getObjectsView().build()
                                    .setStreamName(annotation.stream())
                                    .setType(parameterContext.getParameter().getType());

        for (ObjectOpenOptions option : annotation.options()) {
            builder.addOption(option);
        }

        return builder
                .open();
    }

    @TypedParameterGenerator
    private @Nonnull CorfuRuntime resolveCorfuRuntime(
        @Nonnull ParameterContext parameterContext,
        @Nonnull ExtensionContext extensionContext) throws ParameterResolutionException {
        final CorfuGlobalTestInstance globalInstance =
            extensionContext.getStore(NAMESPACE)
                .get(GLOBAL_TEST_INSTANCE, CorfuGlobalTestInstance.class);

        final CorfuTestInstance testInstance =
            extensionContext.getStore(NAMESPACE)
                .get(TEST_INSTANCE, CorfuTestInstance.class);

        CorfuRuntime runtime = CorfuRuntime.fromParameters(CorfuRuntimeParameters.builder()
            .layoutServer(SERVER_0.getLocator())
            .autoConnect(true)
            .socketType(ChannelImplementation.LOCAL)
            .nettyEventLoop(globalInstance.runtimeGroup)
            .shutdownNettyEventLoop(false)
            .build());

        testInstance.registerRuntime(runtime);
        return runtime;
    }


    @TypedParameterGenerator
    private @Nonnull CorfuServer resolveCorfuServer(
        @Nonnull ParameterContext parameterContext,
        @Nonnull ExtensionContext extensionContext) throws ParameterResolutionException {
        final CorfuGlobalTestInstance globalInstance =
            extensionContext.getStore(NAMESPACE)
                .get(GLOBAL_TEST_INSTANCE, CorfuGlobalTestInstance.class);

        final CorfuTestInstance testInstance =
            extensionContext.getStore(NAMESPACE)
                .get(TEST_INSTANCE, CorfuTestInstance.class);

        if (!parameterContext.getParameter().isAnnotationPresent(Server.class)) {
            throw new ParameterResolutionException("CorfuServer parameter must have "
                + "@Server annotation!");
        }

        Server annotation = parameterContext.getParameter().getAnnotation(Server.class);
        String portString = Integer.toString(annotation.value().getLocator().getPort());

        ServerContext context = new ServerContext(ServerOptionsMap
            .builder()
            .port(portString)
            .prefix(annotation.value().name())
            .bossGroup(globalInstance.getBossGroup())
            .workerGroup(globalInstance.getWorkerGroup())
            .clientGroup(globalInstance.getClientGroup())
            .build().toMap());

        if (!annotation.initialLayout().equals("")) {
            Class<?> testClass = parameterContext.getDeclaringExecutable().getDeclaringClass();
            Optional<Field> field = Arrays.stream(testClass.getDeclaredFields())
                .filter(x -> x.getAnnotation(LayoutProvider.class) != null)
                .filter(x -> x.getAnnotation(LayoutProvider.class)
                    .value().equals(annotation.initialLayout()))
                .findFirst();

            if (!field.isPresent()) {
                throw new ParameterResolutionException("Couldn't find a @LayoutProvider with name "
                    + annotation.initialLayout());
            }

            try {
                Field f = field.get();
                f.setAccessible(true);
                Layout l = (Layout) f.get(extensionContext.getRequiredTestInstance());
                context.setCurrentLayout(l);
                context.setManagementLayout(l);
            } catch (IllegalAccessException e) {
                throw new ParameterResolutionException("Illegal access exception");
            }
        }

        CorfuServer server = new CorfuServer(context);
        server.start();
        testInstance.serverMap.put(annotation.value().getLocator(), server);

        return server;
    }

    @TypedParameterGenerator
    private @Nonnull ConcurrentScheduler resolveConcurrentScheduler(
        @Nonnull ParameterContext parameterContext,
        @Nonnull ExtensionContext extensionContext) throws ParameterResolutionException {
            return new ConcurrentScheduler(extensionContext);
    }

    @TypedParameterGenerator
    private @Nonnull ConcurrentStateMachine resolveConcurrentStateMachine(
        @Nonnull ParameterContext parameterContext,
        @Nonnull ExtensionContext extensionContext) throws ParameterResolutionException {
        return new ConcurrentStateMachine(extensionContext);
    }


    @Override
    public void postProcessTestInstance(Object o, ExtensionContext extensionContext)
        throws Exception {
    }

    @Override
    public boolean supportsTestTemplate(ExtensionContext extensionContext) {
        return true;
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(
        ExtensionContext extensionContext) {
        return Stream.empty();
    }

    private TestConsole getConsole(@Nonnull ExtensionContext extensionContext) {
        return extensionContext.getStore(NAMESPACE)
            .getOrComputeIfAbsent(CONSOLE, TestConsole::computer, TestConsole.class);
    }
}
