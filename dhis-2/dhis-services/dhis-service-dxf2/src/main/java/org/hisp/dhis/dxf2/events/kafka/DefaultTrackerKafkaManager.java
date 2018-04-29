package org.hisp.dhis.dxf2.events.kafka;

/*
 * Copyright (c) 2004-2018, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.hisp.dhis.dxf2.common.ImportOptions;
import org.hisp.dhis.dxf2.events.enrollment.Enrollment;
import org.hisp.dhis.dxf2.events.event.Event;
import org.hisp.dhis.dxf2.events.trackedentity.TrackedEntityInstance;
import org.hisp.dhis.kafka.KafkaManager;
import org.hisp.dhis.render.DefaultRenderService;
import org.hisp.dhis.user.User;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.List;

/**
 * Tracker specific KafkaManager, uses JsonSerializer/JsonDeserializer to automatically serialize deserialize Jackson objects.
 *
 * @author Morten Olav Hansen <mortenoh@gmail.com>
 */
public class DefaultTrackerKafkaManager
    implements TrackerKafkaManager
{
    private final KafkaManager kafkaManager;

    private ConsumerFactory<String, KafkaEvent> cfEvent;
    private ConsumerFactory<String, KafkaEnrollment> cfEnrollment;
    private ConsumerFactory<String, KafkaTrackedEntity> cfTrackedEntity;

    private ProducerFactory<String, KafkaEvent> pfEvent;
    private ProducerFactory<String, KafkaEnrollment> pfEnrollment;
    private ProducerFactory<String, KafkaTrackedEntity> pfTrackedEntity;

    private KafkaTemplate<String, KafkaEvent> ktEvent;
    private KafkaTemplate<String, KafkaEnrollment> ktEnrollment;
    private KafkaTemplate<String, KafkaTrackedEntity> ktTrackedEntity;

    public DefaultTrackerKafkaManager( KafkaManager kafkaManager )
    {
        this.kafkaManager = kafkaManager;
    }

    @EventListener
    public void init( ContextRefreshedEvent event )
    {
        if ( !kafkaManager.isEnabled() )
        {
            return;
        }

        ObjectMapper jsonMapper = DefaultRenderService.getJsonMapper();

        this.pfEvent = kafkaManager.getProducerFactory( new StringSerializer(), new JsonSerializer<>( jsonMapper ) );
        this.pfEnrollment = kafkaManager.getProducerFactory( new StringSerializer(), new JsonSerializer<>( jsonMapper ) );
        this.pfTrackedEntity = kafkaManager.getProducerFactory( new StringSerializer(), new JsonSerializer<>( jsonMapper ) );

        this.cfEvent = kafkaManager.getConsumerFactory(
            new StringDeserializer(), new JsonDeserializer<>( KafkaEvent.class, jsonMapper ), GROUP_BULK_EVENTS );
        this.cfEnrollment = kafkaManager.getConsumerFactory(
            new StringDeserializer(), new JsonDeserializer<>( KafkaEnrollment.class, jsonMapper ), GROUP_BULK_ENROLLMENTS );
        this.cfTrackedEntity = kafkaManager.getConsumerFactory(
            new StringDeserializer(), new JsonDeserializer<>( KafkaTrackedEntity.class, jsonMapper ), GROUP_BULK_TRACKED_ENTITIES );

        this.ktEvent = kafkaManager.getKafkaTemplate( this.pfEvent );
        this.ktEnrollment = kafkaManager.getKafkaTemplate( this.pfEnrollment );
        this.ktTrackedEntity = kafkaManager.getKafkaTemplate( this.pfTrackedEntity );
    }

    @Override
    public boolean isEnabled()
    {
        return kafkaManager.isEnabled();
    }

    @Override
    public ConsumerFactory<String, KafkaEvent> getCfEvent()
    {
        return cfEvent;
    }

    @Override
    public ConsumerFactory<String, KafkaEnrollment> getCfEnrollment()
    {
        return cfEnrollment;
    }

    @Override
    public ConsumerFactory<String, KafkaTrackedEntity> getCfTrackedEntity()
    {
        return cfTrackedEntity;
    }

    @Override
    public ProducerFactory<String, KafkaEvent> getPfEvent()
    {
        return pfEvent;
    }

    @Override
    public ProducerFactory<String, KafkaEnrollment> getPfEnrollment()
    {
        return pfEnrollment;
    }

    @Override
    public ProducerFactory<String, KafkaTrackedEntity> getPfTrackedEntity()
    {
        return pfTrackedEntity;
    }

    @Override
    public KafkaTemplate<String, KafkaEvent> getKtEvent()
    {
        return ktEvent;
    }

    @Override
    public KafkaTemplate<String, KafkaEnrollment> getKtEnrollment()
    {
        return ktEnrollment;
    }

    @Override
    public KafkaTemplate<String, KafkaTrackedEntity> getKtTrackedEntity()
    {
        return ktTrackedEntity;
    }

    @Override
    public void dispatchEvents( User user, ImportOptions importOptions, List<Event> events )
    {
        for ( Event event : events )
        {
            ktEvent.send( TOPIC_BULK_EVENTS, new KafkaEvent( user.getUid(), importOptions, event ) );
        }
    }

    @Override
    public void dispatchEnrollments( User user, ImportOptions importOptions, List<Enrollment> enrollments )
    {
        for ( Enrollment enrollment : enrollments )
        {
            ktEnrollment.send( TOPIC_BULK_ENROLLMENTS, new KafkaEnrollment( user.getUid(), importOptions, enrollment ) );
        }
    }

    @Override
    public void dispatchTrackedEntity( User user, ImportOptions importOptions, List<TrackedEntityInstance> trackedEntities )
    {
        for ( TrackedEntityInstance trackedEntity : trackedEntities )
        {
            ktTrackedEntity.send( TOPIC_BULK_TRACKED_ENTITIES, new KafkaTrackedEntity( user.getUid(), importOptions, trackedEntity ) );
        }
    }
}
