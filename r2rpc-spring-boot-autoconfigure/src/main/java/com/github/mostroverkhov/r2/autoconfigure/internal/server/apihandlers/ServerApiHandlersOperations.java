package com.github.mostroverkhov.r2.autoconfigure.internal.server.apihandlers;

import com.github.mostroverkhov.r2.autoconfigure.client.ApiRequesterFactory;
import com.github.mostroverkhov.r2.autoconfigure.internal.ApiHandlersOperations;
import com.github.mostroverkhov.r2.autoconfigure.server.ServerHandlersProvider;
import com.github.mostroverkhov.r2.core.ConnectionContext;

import java.lang.reflect.Method;
import java.util.Optional;

class ServerApiHandlersOperations extends
    ApiHandlersOperations<ServerHandlersProvider<?>, ServerApiHandlersInfo> {

  @Override
  protected Class<?> findApiImplType(ServerHandlersProvider apiImplProvider) {
    try {
      Method method = apiImplProvider
          .getClass()
          .getMethod(
              "apply",
              ConnectionContext.class,
              ApiRequesterFactory.class);
      return method.getReturnType();
    } catch (NoSuchMethodException e) {
      throw new AssertionError(
          "ServerHandlersProvider is expected "
              + "to have T apply(ConnectionContext c) method",
          e);
    }
  }

  @Override
  protected ServerApiHandlersInfo overrideApiName(ServerApiHandlersInfo responderInfo,
                                                  Class<?> apiImplType) {
    return findApiImplName(apiImplType)
        .map(responderInfo::copyWithName)
        .orElse(responderInfo);
  }

  @Override
  protected Optional<ServerApiHandlersInfo> findApi(
      Class<?> apiCandidateType,
      ServerHandlersProvider<?> serverHandlersProvider) {
    return findApiName(apiCandidateType)
        .map(name ->
            new ServerApiHandlersInfo(
                name,
                apiCandidateType,
                serverHandlersProvider));
  }
}
