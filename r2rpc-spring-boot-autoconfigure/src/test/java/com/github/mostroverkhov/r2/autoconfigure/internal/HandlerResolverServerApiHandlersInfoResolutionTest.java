package com.github.mostroverkhov.r2.autoconfigure.internal;

import com.github.mostroverkhov.r2.autoconfigure.internal.HandlerResolverApiResolutionFixtures.*;
import com.github.mostroverkhov.r2.autoconfigure.internal.server.apihandlers.ServerHandlersResolver;
import com.github.mostroverkhov.r2.autoconfigure.internal.server.apihandlers.ServerApiHandlersInfo;
import com.github.mostroverkhov.r2.autoconfigure.server.ServerHandlersProvider;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlerResolverServerApiHandlersInfoResolutionTest {

  @Test
  public void resolveSingleValidProvider() {
    ServerHandlersResolver handlersResolver = fromProviders(
        new ValidServer());
    Collection<ServerApiHandlersInfo> responderInfos = handlersResolver.resolveAll();
    assertThat(responderInfos).hasSize(1);
    assertThat(responderInfos)
        .flatExtracting(ServerApiHandlersInfo::name, ServerApiHandlersInfo::type)
        .containsExactly("contract", ValidApi.class);
  }

  @Test
  public void resolveSingleValidProviderApiAndApiName() {
    ServerHandlersResolver handlersResolver = fromProviders(
        new ValidNamedServer());
    Collection<ServerApiHandlersInfo> responderInfos = handlersResolver.resolveAll();
    assertThat(responderInfos).hasSize(1);
    assertThat(responderInfos)
        .flatExtracting(ServerApiHandlersInfo::name, ServerApiHandlersInfo::type)
        .contains("named-contract", AnotherValidApi.class);
  }

  @Test
  public void resolveMultipleValidProviders() {
    ServerHandlersResolver handlersResolver = fromProviders(
        new ValidServer(),
        new ValidNamedServer());
    Collection<ServerApiHandlersInfo> responderInfos = handlersResolver.resolveAll();
    assertThat(responderInfos).hasSize(2);
    assertThat(responderInfos)
        .flatExtracting(ServerApiHandlersInfo::name, ServerApiHandlersInfo::type)
        .contains(
            "named-contract",
            AnotherValidApi.class,
            "contract",
            ValidApi.class);
  }

  @Test
  public void resolveProviderRenamed() {
    ServerHandlersResolver handlersResolver = fromProviders(
        new RenamedValidServer());
    Collection<ServerApiHandlersInfo> responderInfos = handlersResolver.resolveAll();
    assertThat(responderInfos).hasSize(1);
    assertThat(responderInfos)
        .flatExtracting(ServerApiHandlersInfo::name, ServerApiHandlersInfo::type)
        .contains(
            "renamed-contract",
            RenamedValidApi.class);
  }

  @Test
  public void resolveSingleProviderNoApis() {
    ServerHandlersResolver handlersResolver = fromProviders(
        new NoApisProviderServer());
    Collection<ServerApiHandlersInfo> responderInfos = handlersResolver.resolveAll();
    assertThat(responderInfos).isEmpty();
  }

  @Test(expected = IllegalArgumentException.class)
  public void resolveSingleProviderMultipleApis() {
    ServerHandlersResolver handlersResolver = fromProviders(
        new TwoApisProviderServer());
    handlersResolver.resolveAll();
  }

  /*@Test
  public void resolveServiceFactoryMultipleApis() {
    ServerHandlersResolver handlersResolver = fromProviders(
        new ValidServer(),
        new ValidNamedServer());
    List<String> keys = keys("contract", "named-contract");
    ServerHandlersFactory serverHandlersFactory = handlersResolver.resolve(keys);

    assertThat(serverHandlersFactory).isNotNull();

    ConnectionContext connCtx = new ConnectionContext(new Builder().build());
    Collection<Object> svcHandlers = serverHandlersFactory.apply(connCtx);
    assertThat(svcHandlers).hasSize(2);
    Iterator<Object> it = svcHandlers.iterator();
    assertThat(it.next()).isInstanceOfAny(Contract.class, AnotherContract.class);
    assertThat(it.next()).isInstanceOfAny(Contract.class, AnotherContract.class);
  }*/
//TODO restore tests
  /*@Test(expected = IllegalArgumentException.class)
  public void resolveServiceFactorySameApisTypePerEndpoint() {
    ServerHandlersResolver handlersResolver = new ServerHandlersResolver(
        Arrays.asList(
            new AnotherValidServer(),
            new ValidNamedServer()));

    List<String> keys = keys("another-contract", "named-contract");
    handlersResolver.resolve(keys);
  }*/

  /*@Test(expected = IllegalArgumentException.class)
  public void resolveMultipleProvidersDuplicateApi() {
    ServerHandlersResolver handlersResolver = fromProviders(
        new ValidServer(),
        new ValidServer());
    handlersResolver.resolve(keys("contract"));
  }*/

  /*@Test(expected = IllegalArgumentException.class)
  public void resolveServiceFactoryDuplicateKeys() {
    ServerHandlersResolver handlersResolver = fromProviders(
        new ValidServer());
    handlersResolver.resolve(keys("contract", "contract"));
  }*/

  /*@Test(expected = IllegalArgumentException.class)
  public void resolveServiceFactoryMissingApi() {
    ServerHandlersResolver handlersResolver = fromProviders(
        new ValidServer());
    handlersResolver.resolve(keys("missing"));
  }*/

  static List<String> keys(String... keys) {
    return Arrays.asList(keys);
  }

  static ServerHandlersResolver fromProviders(ServerHandlersProvider<?>... providers) {
    return new ServerHandlersResolver(Arrays.asList(providers));
  }
}
