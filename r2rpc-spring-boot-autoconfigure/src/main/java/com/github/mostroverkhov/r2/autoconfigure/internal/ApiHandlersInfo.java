package com.github.mostroverkhov.r2.autoconfigure.internal;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Objects;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

public abstract class ApiHandlersInfo<ApiProvider> {
  protected final String name;
  protected final Class<?> apiType;
  protected final ApiProvider apiProvider;

  public ApiHandlersInfo(
      String name,
      Class<?> apiType,
      ApiProvider apiProvider) {
    Objects.requireNonNull(name);
    Objects.requireNonNull(apiType);
    Objects.requireNonNull(apiProvider);
    this.name = name;
    this.apiType = apiType;
    this.apiProvider = apiProvider;
  }

  public String name() {
    return name;
  }

  public Class<?> type() {
    return apiType;
  }

  public ApiProvider serverApiProvider() {
    return apiProvider;
  }

  protected Collection<Object> resolveSvcHandlers(Object apiImpl) {
    return stream(
        apiType.getMethods())
        .map(m -> call(m, apiImpl))
        .collect(toList());
  }

  private static Object call(Method m, Object target) {
    try {
      m.setAccessible(true);
      return m.invoke(target);
    } catch (Exception e) {
      String msg = String.format("Could not invoke %s on %s",
          m,
          target.getClass().getName());
      throw new IllegalStateException(msg, e);
    }
  }
}
