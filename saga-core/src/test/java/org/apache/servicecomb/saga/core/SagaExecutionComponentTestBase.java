/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.servicecomb.saga.core;

import static org.apache.servicecomb.saga.core.Operation.TYPE_REST;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.waitAtMost;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.servicecomb.saga.core.application.SagaFactory;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import org.apache.servicecomb.saga.core.application.SagaExecutionComponent;
import org.apache.servicecomb.saga.core.application.interpreter.FromJsonFormat;
import org.apache.servicecomb.saga.infrastructure.EmbeddedEventStore;

@SuppressWarnings("unchecked")
public abstract class SagaExecutionComponentTestBase {

  private static final String requestJson = "[\n"
      + "  {\n"
      + "    \"id\": \"request-1\",\n"
      + "    \"serviceName\": \"aaa\",\n"
      + "    \"transaction\": {\n"
      + "      \"method\": \"post\",\n"
      + "      \"path\": \"/rest/as\"\n"
      + "    },\n"
      + "    \"compensation\": {\n"
      + "      \"method\": \"delete\",\n"
      + "      \"path\": \"/rest/as\"\n"
      + "    }\n"
      + "  }\n"
      + "]\n";

  private static final String sagaJson = "{\n"
      + "  \"policy\": \"ForwardRecovery\",\n"
      + "  \"requests\": " + requestJson + "\n"
      + "}";

  private static final String anotherRequestJson = "[\n"
      + "  {\n"
      + "    \"id\": \"request-2\",\n"
      + "    \"serviceName\": \"bbb\",\n"
      + "    \"transaction\": {\n"
      + "      \"method\": \"post\",\n"
      + "      \"path\": \"/rest/bs\"\n"
      + "    },\n"
      + "    \"compensation\": {\n"
      + "      \"method\": \"delete\",\n"
      + "      \"path\": \"/rest/bs\"\n"
      + "    }\n"
      + "  }\n"
      + "]\n";

  private static final String anotherSagaJson = "{\n"
      + "  \"policy\": \"ForwardRecovery\",\n"
      + "  \"requests\": " + anotherRequestJson + "\n"
      + "}";

  public static final Map<String, Map<String, String>> EMPTY_MAP = Collections.<String, Map<String, String>>emptyMap();

  private final SagaRequest request1 = new SagaRequestImpl(
      "request-1",
      "aaa",
      TYPE_REST,
      new TransactionImpl("/rest/as", "post", EMPTY_MAP),
      new CompensationImpl("/rest/as", "delete", EMPTY_MAP)
  );

  private final SagaRequest request2 = new SagaRequestImpl(
      "request-2",
      "bbb",
      TYPE_REST,
      new TransactionImpl("/rest/bs", "post", EMPTY_MAP),
      new CompensationImpl("/rest/bs", "delete", EMPTY_MAP)
  );

  private final SagaDefinition definition1 = new SagaDefinition() {
    @Override
    public RecoveryPolicy policy() {
      return new ForwardRecovery();
    }

    @Override
    public SagaRequest[] requests() {
      return new SagaRequest[]{request1};
    }
  };

  private final SagaDefinition definition2 = new SagaDefinition() {
    @Override
    public RecoveryPolicy policy() {
      return new BackwardRecovery();
    }

    @Override
    public SagaRequest[] requests() {
      return new SagaRequest[]{request2};
    }
  };

  private final FromJsonFormat<SagaDefinition> fromJsonFormat = Mockito.mock(FromJsonFormat.class);
  private final EmbeddedPersistentStore eventStore = new EmbeddedPersistentStore();

  private final SagaExecutionComponent coordinator = new SagaExecutionComponent(
      eventStore,
      fromJsonFormat,
      null,
      sagaFactory(eventStore)
  );

  private final String sagaId = "1";

  @Before
  public void setUp() throws Exception {
    when(fromJsonFormat.fromJson(sagaJson)).thenReturn(definition1);
    when(fromJsonFormat.fromJson(anotherSagaJson)).thenReturn(definition2);
  }

  @Test
  public void recoverSagaWithEventsFromEventStore() throws IOException {
    eventStore.offer(new SagaStartedEvent(sagaId, sagaJson, NoOpSagaRequest.SAGA_START_REQUEST));
    coordinator.reanimate();

    assertThat(eventStore, contains(
        eventWith(NoOpSagaRequest.SAGA_START_REQUEST, SagaStartedEvent.class),
        eventWith(request1, TransactionStartedEvent.class),
        eventWith(request1, TransactionEndedEvent.class),
        eventWith(NoOpSagaRequest.SAGA_END_REQUEST, SagaEndedEvent.class)
    ));
  }

  @Test
  public void runSagaWithEventStore() throws IOException {
    SagaResponse response = coordinator.run(sagaJson);

    assertThat(response, is(SagaResponse.EMPTY_RESPONSE));
    assertThat(eventStore, contains(
        eventWith(NoOpSagaRequest.SAGA_START_REQUEST, SagaStartedEvent.class),
        eventWith(request1, TransactionStartedEvent.class),
        eventWith(request1, TransactionEndedEvent.class),
        eventWith(NoOpSagaRequest.SAGA_END_REQUEST, SagaEndedEvent.class)
    ));
  }

  @Test
  public void processRequestsInParallel() {
    ExecutorService executor = Executors.newFixedThreadPool(2);

    executor.submit(new Runnable() {
      @Override
      public void run() {
        coordinator.run(sagaJson);
      }
    });
    executor.submit(new Runnable() {
      @Override
      public void run() {
        coordinator.run(anotherSagaJson);
      }
    });

    waitAtMost(2, SECONDS).until(new Callable<Boolean>() {
      @Override
      public Boolean call() throws Exception {
        return eventStore.size() == 8;
      }
    });

    assertThat(eventStore, containsInAnyOrder(
        eventWith(NoOpSagaRequest.SAGA_START_REQUEST, SagaStartedEvent.class),
        eventWith(request1, TransactionStartedEvent.class),
        eventWith(request1, TransactionEndedEvent.class),
        eventWith(NoOpSagaRequest.SAGA_END_REQUEST, SagaEndedEvent.class),
        eventWith(NoOpSagaRequest.SAGA_START_REQUEST, SagaStartedEvent.class),
        eventWith(request2, TransactionStartedEvent.class),
        eventWith(request2, TransactionEndedEvent.class),
        eventWith(NoOpSagaRequest.SAGA_END_REQUEST, SagaEndedEvent.class)
    ));
  }

  @Test
  public void runSagaAfterRecovery() throws IOException {
    eventStore.offer(new SagaStartedEvent(sagaId, sagaJson, NoOpSagaRequest.SAGA_START_REQUEST));
    coordinator.reanimate();

    coordinator.run(anotherSagaJson);

    assertThat(eventStore, contains(
        eventWith(NoOpSagaRequest.SAGA_START_REQUEST, SagaStartedEvent.class),
        eventWith(request1, TransactionStartedEvent.class),
        eventWith(request1, TransactionEndedEvent.class),
        eventWith(NoOpSagaRequest.SAGA_END_REQUEST, SagaEndedEvent.class),
        eventWith(NoOpSagaRequest.SAGA_START_REQUEST, SagaStartedEvent.class),
        eventWith(request2, TransactionStartedEvent.class),
        eventWith(request2, TransactionEndedEvent.class),
        eventWith(NoOpSagaRequest.SAGA_END_REQUEST, SagaEndedEvent.class)
    ));
  }

  private Matcher<SagaEvent> eventWith(
      final SagaRequest sagaRequest,
      final Class<?> type) {

    return new TypeSafeMatcher<SagaEvent>() {
      @Override
      protected boolean matchesSafely(SagaEvent event) {
        SagaRequest request = event.payload();
        return sagaRequest.equals(request)
            && event.getClass().equals(type);
      }

      @Override
      protected void describeMismatchSafely(SagaEvent item, Description mismatchDescription) {
        SagaRequest request = item.payload();
        mismatchDescription.appendText(
            "SagaEvent {" + request + "}");
      }

      @Override
      public void describeTo(Description description) {
        description.appendText(
            "SagaEvent {" + sagaRequest + "}");
      }
    };
  }

  private class EmbeddedPersistentStore extends EmbeddedEventStore implements PersistentStore {

    @Override
    public Map<String, List<EventEnvelope>> findPendingSagaEvents() {
      return singletonMap(sagaId, singletonList(
          new EventEnvelope(1L, new SagaStartedEvent(sagaId, sagaJson, NoOpSagaRequest.SAGA_START_REQUEST))));
    }
  }

  protected abstract SagaFactory sagaFactory(PersistentStore eventStore);
}
