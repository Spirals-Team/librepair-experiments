package com.github.mostroverkhov.r2.autoconfigure.internal.server.apihandlers;

import com.github.mostroverkhov.r2.autoconfigure.client.ApiRequesterFactory;
import com.github.mostroverkhov.r2.autoconfigure.internal.ApiHandlersInfo;
import com.github.mostroverkhov.r2.autoconfigure.server.ServerHandlersProvider;
import com.github.mostroverkhov.r2.core.ConnectionContext;

import java.util.Collection;

public final class ServerApiHandlersInfo extends
    ApiHandlersInfo<ServerHandlersProvider<?>> {

  public ServerApiHandlersInfo(
      String name,
      Class<?> apiType,
      ServerHandlersProvider<?> serverHandlersProvider) {
    super(name, apiType, serverHandlersProvider);
  }

  public ServerApiHandlersInfo copyWithName(String name) {
    return new ServerApiHandlersInfo(name, apiType, apiProvider);
  }

  public Collection<Object> createHandlers(ConnectionContext ctx,
                                           ApiRequesterFactory apiRequesterFactory) {
    Object apiImpl = apiProvider.apply(ctx, apiRequesterFactory);
    return resolveSvcHandlers(apiImpl);
  }
}
