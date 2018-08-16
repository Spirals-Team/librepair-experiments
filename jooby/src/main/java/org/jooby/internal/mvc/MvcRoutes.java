/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jooby.internal.mvc;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import org.jooby.Env;
import org.jooby.MediaType;
import org.jooby.Route;
import org.jooby.Route.Definition;
import org.jooby.internal.RouteMetadata;
import org.jooby.mvc.CONNECT;
import org.jooby.mvc.Consumes;
import org.jooby.mvc.DELETE;
import org.jooby.mvc.GET;
import org.jooby.mvc.HEAD;
import org.jooby.mvc.OPTIONS;
import org.jooby.mvc.PATCH;
import org.jooby.mvc.POST;
import org.jooby.mvc.PUT;
import org.jooby.mvc.Path;
import org.jooby.mvc.Produces;
import org.jooby.mvc.TRACE;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableSet;

import javaslang.control.Try;

public class MvcRoutes {

  private static final String[] EMPTY = new String[0];

  @SuppressWarnings("unchecked")
  private static final Set<Class<? extends Annotation>> VERBS = ImmutableSet.of(GET.class,
      POST.class, PUT.class, DELETE.class, PATCH.class, HEAD.class, OPTIONS.class, TRACE.class,
      CONNECT.class);

  private static final Set<Class<? extends Annotation>> IGNORE = ImmutableSet
      .<Class<? extends Annotation>> builder()
      .addAll(VERBS)
      .add(Path.class)
      .add(Produces.class)
      .add(Consumes.class)
      .build();

  public static List<Route.Definition> routes(final Env env, final RouteMetadata classInfo,
      final String rpath, final Class<?> routeClass) {

    // check and fail fast
    methods(routeClass, methods -> {
      routes(methods, (m, a) -> {
        if (!Modifier.isPublic(m.getModifiers())) {
          throw new IllegalArgumentException("Not a public method: " + m);
        }
      });
    });

    RequestParamProvider provider = new RequestParamProviderImpl(
        new RequestParamNameProviderImpl(classInfo));

    String[] rootPaths = path(routeClass);
    String[] rootExcludes = excludes(routeClass, EMPTY);

    // we are good, now collect them
    Map<Method, List<Class<?>>> methods = new HashMap<>();
    routes(routeClass.getMethods(), methods::put);

    List<Definition> definitions = new ArrayList<>();
    Map<String, Object> attrs = attrs(routeClass.getAnnotations());
    methods
        .keySet()
        .stream()
        .sorted((m1, m2) -> {
          int l1 = classInfo.startAt(m1);
          int l2 = classInfo.startAt(m2);
          return l1 - l2;
        })
        .forEach(method -> {
          /**
           * Param provider: dev vs none dev
           */
          RequestParamProvider paramProvider = provider;
          if (!env.name().equals("dev")) {
            List<RequestParam> params = provider.parameters(method);
            paramProvider = (h) -> params;
          }

          List<Class<?>> verbs = methods.get(method);
          List<MediaType> produces = produces(method);
          List<MediaType> consumes = consumes(method);
          Map<String, Object> localAttrs = new HashMap<>(attrs);
          localAttrs.putAll(attrs(method.getAnnotations()));

          for (String path : expandPaths(rootPaths, method)) {
            for (Class<?> verb : verbs) {
              String name = routeClass.getSimpleName() + "." + method.getName();

              String[] excludes = excludes(method, rootExcludes);

              Definition definition = new Route.Definition(
                  verb.getSimpleName(), rpath + "/" + path, new MvcHandler(method, paramProvider))
                      .produces(produces)
                      .consumes(consumes)
                      .excludes(excludes)
                      .declaringClass(method.getDeclaringClass().getName())
                      .line(classInfo.startAt(method) - 1)
                      .name(name);

              localAttrs.forEach((n, v) -> definition.attr(n, v));
              definitions.add(definition);
            }
          }
        });

    return definitions;
  }

  private static void methods(final Class<?> clazz, final Consumer<Method[]> callback) {
    if (clazz != Object.class) {
      callback.accept(clazz.getDeclaredMethods());
      methods(clazz.getSuperclass(), callback);
    }
  }

  @SuppressWarnings({"rawtypes", "unchecked" })
  private static void routes(final Method[] methods,
      final BiConsumer<Method, List<Class<?>>> consumer) {
    for (Method method : methods) {
      List<Class<?>> annotations = new ArrayList<>();
      for (Class annotationType : VERBS) {
        Annotation annotation = method.getAnnotation(annotationType);
        if (annotation != null) {
          annotations.add(annotationType);
        }
      }
      if (annotations.size() > 0) {
        consumer.accept(method, annotations);
      } else if (method.isAnnotationPresent(Path.class)) {
        consumer.accept(method, Arrays.asList(GET.class));
      }
    }
  }

  private static Map<String, Object> attrs(final Annotation[] annotations) {
    Map<String, Object> result = new LinkedHashMap<>();
    for (Annotation annotation : annotations) {
      result.putAll(attrs(annotation));
    }
    return result;
  }

  private static Map<String, Object> attrs(final Annotation annotation) {
    Map<String, Object> result = new LinkedHashMap<>();
    Class<? extends Annotation> annotationType = annotation.annotationType();
    if (!IGNORE.contains(annotationType)) {
      Method[] attrs = annotation.annotationType().getDeclaredMethods();
      for (Method attr : attrs) {
        Try.of(() -> attr.invoke(annotation))
            .onSuccess(value -> result.put(attrName(annotation, attr), value));
      }
    }
    return result;
  }

  private static String attrName(final Annotation annotation, final Method attr) {
    String name = attr.getName();
    if (name.equals("value")) {
      return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL,
          annotation.annotationType().getSimpleName());
    }
    return name;
  }

  private static List<MediaType> produces(final Method method) {
    Function<AnnotatedElement, Optional<List<MediaType>>> fn = (element) -> {
      Produces produces = element.getAnnotation(Produces.class);
      if (produces != null) {
        return Optional.of(MediaType.valueOf(produces.value()));
      }
      return Optional.empty();
    };

    // method level
    return fn.apply(method)
        // class level
        .orElseGet(() -> fn.apply(method.getDeclaringClass())
            // none
            .orElse(MediaType.ALL));
  }

  private static List<MediaType> consumes(final Method method) {
    Function<AnnotatedElement, Optional<List<MediaType>>> fn = (element) -> {
      Consumes consumes = element.getAnnotation(Consumes.class);
      if (consumes != null) {
        return Optional.of(MediaType.valueOf(consumes.value()));
      }
      return Optional.empty();
    };

    // method level
    return fn.apply(method)
        // class level
        .orElseGet(() -> fn.apply(method.getDeclaringClass())
            // none
            .orElse(MediaType.ALL));
  }

  private static String[] path(final AnnotatedElement owner) {
    Path annotation = owner.getAnnotation(Path.class);
    if (annotation == null) {
      return EMPTY;
    }
    return annotation.value();
  }

  private static String[] excludes(final AnnotatedElement owner, final String[] parent) {
    Path annotation = owner.getAnnotation(Path.class);
    if (annotation == null) {
      return parent;
    }
    String[] excludes = annotation.excludes();
    if (excludes.length == 0) {
      return parent;
    }
    if (parent.length == 0) {
      return excludes;
    }
    // join everything
    int size = parent.length + excludes.length;
    String[] result = new String[size];
    System.arraycopy(parent, 0, result, 0, parent.length);
    System.arraycopy(excludes, 0, result, parent.length, excludes.length);
    return result;
  }

  private static String[] expandPaths(final String[] root, final Method m) {
    String[] path = path(m);
    if (root.length == 0) {
      if (path.length == 0) {
        throw new IllegalArgumentException("No path(s) found for: " + m);
      }
      return path;
    }
    if (path.length == 0) {
      return root;
    }
    String[] result = new String[root.length * path.length];
    int k = 0;
    for (String base : root) {
      for (String element : path) {
        result[k] = base + "/" + element;
        k += 1;
      }
    }
    return result;
  }
}
