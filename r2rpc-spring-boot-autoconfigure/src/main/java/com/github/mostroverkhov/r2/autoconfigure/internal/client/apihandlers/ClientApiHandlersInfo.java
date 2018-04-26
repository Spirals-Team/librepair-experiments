package com.github.mostroverkhov.r2.autoconfigure.internal.client.apihandlers;

import com.github.mostroverkhov.r2.autoconfigure.client.ApiRequesterFactory;
import com.github.mostroverkhov.r2.autoconfigure.client.ClientHandlersProvider;
import com.github.mostroverkhov.r2.autoconfigure.internal.ApiHandlersInfo;

import java.util.Collection;

public class ClientApiHandlersInfo extends ApiHandlersInfo<ClientHandlersProvider<?>> {
  public ClientApiHandlersInfo(String name,
                               Class<?> apiType,
                               ClientHandlersProvider<?> clientHandlersProvider) {
    super(name, apiType, clientHandlersProvider);
  }

  public ClientApiHandlersInfo copyWithName(String name) {
    return new ClientApiHandlersInfo(name, apiType, apiProvider);
  }

  public Collection<Object> createHandlers(ApiRequesterFactory apiRequesterFactory) {
    Object apiImpl = apiProvider.apply(apiRequesterFactory);
    return resolveSvcHandlers(apiImpl);
  }
}
