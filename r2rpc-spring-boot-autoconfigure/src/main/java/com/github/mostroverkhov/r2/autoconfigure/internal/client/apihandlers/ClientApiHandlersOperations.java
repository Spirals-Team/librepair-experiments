package com.github.mostroverkhov.r2.autoconfigure.internal.client.apihandlers;

import com.github.mostroverkhov.r2.autoconfigure.client.ApiRequesterFactory;
import com.github.mostroverkhov.r2.autoconfigure.client.ClientHandlersProvider;
import com.github.mostroverkhov.r2.autoconfigure.internal.ApiHandlersOperations;

import java.lang.reflect.Method;
import java.util.Optional;

public class ClientApiHandlersOperations extends
    ApiHandlersOperations<ClientHandlersProvider<?>,
        ClientApiHandlersInfo> {
  @Override
  protected ClientApiHandlersInfo overrideApiName(ClientApiHandlersInfo responderInfo,
                                                  Class<?> apiImplType) {
    return findApiImplName(apiImplType)
        .map(responderInfo::copyWithName)
        .orElse(responderInfo);
  }

  @Override
  protected Class<?> findApiImplType(ClientHandlersProvider<?> apiImplProvider) {
    try {
      Method method = apiImplProvider
          .getClass()
          .getMethod(
              "apply",
              ApiRequesterFactory.class);
      return method.getReturnType();
    } catch (NoSuchMethodException e) {
      throw new AssertionError(
          "ClientHandlersProvider is expected "
              + "to have T apply(ConnectionContext c) method",
          e);
    }
  }

  @Override
  protected Optional<ClientApiHandlersInfo> findApi(Class<?> apiCandidateType,
                                                    ClientHandlersProvider<?> clientHandlersProvider) {
    return findApiName(apiCandidateType)
        .map(name ->
            new ClientApiHandlersInfo(
                name,
                apiCandidateType,
                clientHandlersProvider));

  }
}
