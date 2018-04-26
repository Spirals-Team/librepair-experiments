package com.github.mostroverkhov.r2.autoconfigure.internal.client.apihandlers;

import com.github.mostroverkhov.r2.autoconfigure.client.ApiRequesterFactory;
import com.github.mostroverkhov.r2.autoconfigure.client.ClientHandlersProvider;
import com.github.mostroverkhov.r2.autoconfigure.internal.BaseHandlersResolver;
import com.github.mostroverkhov.r2.core.RequesterFactory;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ClientHandlersResolver extends BaseHandlersResolver<
    ClientApiHandlersFactory,
    ClientHandlersProvider<?>,
    ClientApiHandlersInfo> {
  public ClientHandlersResolver(List<ClientHandlersProvider<?>> clientHandlersProviders) {
    super(clientHandlersProviders, new ClientApiHandlersOperations());
  }

  @Override
  protected ClientApiHandlersFactory handlersFactory(
      Collection<ClientApiHandlersInfo> apiInfos,
      Function<RequesterFactory, ApiRequesterFactory> requesterFactoryConverter) {

    return (requesterFactory) -> {

      ApiRequesterFactory apiRequesterFactory =
          requesterFactoryConverter
              .apply(requesterFactory);

      return apiInfos
          .stream()
          .flatMap(apiInfo -> {
            Collection<Object> handlers = apiInfo
                .createHandlers(apiRequesterFactory);
            return handlers.stream();
          })
          .collect(Collectors.toList());
    };

  }
}
