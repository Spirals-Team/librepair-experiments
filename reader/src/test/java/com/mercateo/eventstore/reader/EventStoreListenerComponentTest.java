package com.mercateo.eventstore.reader;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.msemys.esjc.CatchUpSubscription;
import com.mercateo.common.ComponentTest;
import com.mercateo.eventstore.connection.EventStream;
import com.mercateo.eventstore.example.TestData;
import com.mercateo.eventstore.json.EventJsonMapper;
import com.mercateo.eventstore.reader.config.EventStoreReaderConfiguration;
import com.mercateo.eventstore.reader.example.SomethingHappenedEventConsumer;
import com.mercateo.eventstore.reader.example.SomethingHappenedEventReceiver;

import io.vavr.collection.List;
import io.vavr.control.Option;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { EventStoreReaderConfiguration.class, SomethingHappenedEventConsumer.class })
@ActiveProfiles({ "test" })
@Category(ComponentTest.class)
public class EventStoreListenerComponentTest {

    private EventStreamListener uut;

    @Autowired
    private SomethingHappenedEventConsumer dataHandler;

    @MockBean
    private SomethingHappenedEventReceiver eventReceiver;

    @Mock
    private EventStream eventStream;

    @Mock
    private CatchUpSubscription catchUpSubscription;

    @Autowired
    private EventJsonMapper eventJsonMapper;

    @Autowired
    private EventMetadataMapper eventMetadataMapper;

    @Before
    public void setUp() throws Exception {
        uut = new EventStreamListener(new EventHandler(List.of(dataHandler), eventJsonMapper, eventMetadataMapper),
                eventStream, new EventStatisticsCollector(eventStream, Option.none()));
    }

    @Test
    public void processesEventWithLegacyMetaDataFormat() throws Exception {
        uut.onEvent(catchUpSubscription, TestData.EVENT_STORE_RESOLVED_LEGACY_SOMETHING_HAPPENED_EVENT);

        verify(eventReceiver).on(TestData.SOMETHING_HAPPENED);
    }

    @Test
    public void processesEventWithMetaDataFormat() throws Exception {
        uut.onEvent(catchUpSubscription, TestData.EVENT_STORE_RESOLVED_SOMETHING_HAPPENED_EVENT);

        verify(eventReceiver).on(TestData.SOMETHING_HAPPENED);
    }
}
