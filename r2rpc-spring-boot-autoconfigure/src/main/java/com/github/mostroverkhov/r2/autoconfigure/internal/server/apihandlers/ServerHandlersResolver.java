package com.github.mostroverkhov.r2.autoconfigure.internal.server.apihandlers;

import com.github.mostroverkhov.r2.autoconfigure.client.ApiRequesterFactory;
import com.github.mostroverkhov.r2.autoconfigure.internal.BaseHandlersResolver;
import com.github.mostroverkhov.r2.autoconfigure.server.ServerHandlersProvider;
import com.github.mostroverkhov.r2.core.RequesterFactory;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class ServerHandlersResolver extends
    BaseHandlersResolver<
            ServerApiHandlersFactory,
        ServerHandlersProvider<?>,
            ServerApiHandlersInfo> {

  public ServerHandlersResolver(List<ServerHandlersProvider<?>> providers) {
    super(providers, new ServerApiHandlersOperations());
  }

  @Override
  protected ServerApiHandlersFactory handlersFactory(
      Collection<ServerApiHandlersInfo> apiInfos,
      Function<RequesterFactory, ApiRequesterFactory> requesterFactoryConverter) {
    assertTypesUnique(apiInfos);

    return (connCtx, requesterFactory) -> {

      ApiRequesterFactory apiRequesterFactory =
          requesterFactoryConverter
              .apply(requesterFactory);

      return apiInfos
          .stream()
          .flatMap(apiInfo -> {
            Collection<Object> handlers = apiInfo
                .createHandlers(connCtx, apiRequesterFactory);
            return handlers.stream();
          })
          .collect(Collectors.toList());
    };
  }

  private static void assertTypesUnique(
      Collection<ServerApiHandlersInfo> responderInfos) {

    Set<Class<?>> apiTypes = responderInfos
        .stream()
        .map(ServerApiHandlersInfo::type)
        .collect(toSet());

    if (apiTypes.size() != responderInfos.size()) {
      throw new IllegalArgumentException("There must be at most 1"
          + " implementation of API per endpoint");
    }
  }

}
