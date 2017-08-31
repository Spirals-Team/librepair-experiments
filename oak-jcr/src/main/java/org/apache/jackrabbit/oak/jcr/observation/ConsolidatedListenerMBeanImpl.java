/*
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

package org.apache.jackrabbit.oak.jcr.observation;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.management.ObjectName;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularData;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.scr.annotations.References;
import org.apache.jackrabbit.api.jmx.EventListenerMBean;
import org.apache.jackrabbit.oak.jcr.observation.jmx.ConsolidatedListenerMBean;
import org.apache.jackrabbit.oak.osgi.OsgiWhiteboard;
import org.apache.jackrabbit.oak.plugins.observation.filter.FilterConfigMBean;
import org.apache.jackrabbit.oak.spi.commit.BackgroundObserverMBean;
import org.apache.jackrabbit.oak.spi.commit.Observer;
import org.apache.jackrabbit.oak.spi.whiteboard.Registration;
import org.apache.jackrabbit.oak.spi.whiteboard.Whiteboard;
import org.osgi.framework.BundleContext;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.jackrabbit.oak.spi.whiteboard.WhiteboardUtils.registerMBean;

@Component
@References({
        @Reference(name = "observer",
                bind = "bindObserver",
                unbind = "unbindObserver",
                referenceInterface = Observer.class,
                policy = ReferencePolicy.DYNAMIC,
                cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE),
        @Reference(name = "listenerMBean",
                bind = "bindListenerMBean",
                unbind = "unbindListenerMBean",
                referenceInterface = EventListenerMBean.class,
                policy = ReferencePolicy.DYNAMIC,
                cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE),
        @Reference(name = "backgroundObserverMBean",
                bind = "bindBackgroundObserverMBean",
                unbind = "unbindBackgroundObserverMBean",
                referenceInterface = BackgroundObserverMBean.class,
                policy = ReferencePolicy.DYNAMIC,
                cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE),
        @Reference(name = "filterConfigMBean",
                bind = "bindFilterConfigMBean",
                unbind = "unbindFilterConfigMBean",
                referenceInterface = FilterConfigMBean.class,
                policy = ReferencePolicy.DYNAMIC,
                cardinality = ReferenceCardinality.OPTIONAL_MULTIPLE)

})
public class ConsolidatedListenerMBeanImpl implements ConsolidatedListenerMBean {
    private final AtomicInteger observerCount = new AtomicInteger();
    private final Map<ObjectName, EventListenerMBean> eventListeners = Maps.newConcurrentMap();
    private final Map<ObjectName, BackgroundObserverMBean> bgObservers = Maps.newConcurrentMap();
    private final Map<ObjectName, FilterConfigMBean> filterConfigs = Maps.newConcurrentMap();

    private Registration mbeanReg;

    @Override
    public TabularData getListenerStats() {
        TabularDataSupport tds;
        try {
            int id = 0;
            TabularType tt = new TabularType(ListenerStatsData.class.getName(),
                    "Consolidated Listener Stats", ListenerStatsData.TYPE, new String[]{"index"});
            tds = new TabularDataSupport(tt);
            for(ListenerMBeans beans : getListenerMBeans()){
                tds.put(new ListenerStatsData(++id, beans).toCompositeData());
            }
        } catch (OpenDataException e) {
            throw new IllegalStateException(e);
        }
        return tds;
    }

    @Override
    public TabularData getObserversStats() {
        TabularDataSupport tds;
        try {
            int id = 0;
            TabularType tt = new TabularType(ObserverStatsData.class.getName(),
                    "Consolidated Observer Stats", ObserverStatsData.TYPE, new String[]{"index"});
            tds = new TabularDataSupport(tt);
            for(BackgroundObserverMBean o: collectNonJcrObservers()){
                tds.put(new ObserverStatsData(++id, o).toCompositeData());
            }
        } catch (OpenDataException e) {
            throw new IllegalStateException(e);
        }
        return tds;
    }

    @Override
    public int getObserversCount() {
        return observerCount.get();
    }

    @Override
    public int getListenersCount() {
        return eventListeners.size();
    }

    private Collection<BackgroundObserverMBean> collectNonJcrObservers() {
        List<BackgroundObserverMBean> observers = Lists.newArrayList();

        for (Map.Entry<ObjectName, BackgroundObserverMBean> o : bgObservers.entrySet()){
           String listenerId = getListenerId(o.getKey());
            if (listenerId == null){
                observers.add(o.getValue());
            }
        }

        return observers;
    }

    /**
     * Performs the mapping between EventListenerMBean and the Observer
     * based on the JMX ObjectName service property of the MBean
     *
     * @return map of EventListenerMBean and corresponding Observer
     */
    private List<ListenerMBeans> getListenerMBeans() {
        List<ListenerMBeans> mbeans = Lists.newArrayListWithCapacity(eventListeners.size());
        for (Map.Entry<ObjectName, EventListenerMBean> e : eventListeners.entrySet()){
            String listenerId = getListenerId(e.getKey());
            ListenerMBeans m = new ListenerMBeans();
            m.eventListenerMBean = e.getValue();
            for (Map.Entry<ObjectName, FilterConfigMBean> ef : filterConfigs.entrySet()){
                if (Objects.equal(getListenerId(ef.getKey()), listenerId)){
                    m.filterConfigMBean = ef.getValue();
                }
            }
            for (Map.Entry<ObjectName, BackgroundObserverMBean> ef : bgObservers.entrySet()){
                if (Objects.equal(getListenerId(ef.getKey()), listenerId)){
                    m.observerMBean = ef.getValue();
                }
            }
            mbeans.add(m);
        }
        return mbeans;
    }

    //~---------------------------------------< OSGi >

    @Activate
    private void activate(BundleContext context){
        Whiteboard wb = new OsgiWhiteboard(context);
        mbeanReg = registerMBean(wb,
                ConsolidatedListenerMBean.class,
                this,
                ConsolidatedListenerMBean.TYPE,
                "Consolidated Event Listener statistics");
    }

    @Deactivate
    private void deactivate(){
        if(mbeanReg != null){
            mbeanReg.unregister();
        }
        eventListeners.clear();
        bgObservers.clear();
        filterConfigs.clear();
    }

    @SuppressWarnings("unused")
    protected void bindObserver(Observer observer, Map<String, ?> config){
        observerCount.incrementAndGet();
    }

    @SuppressWarnings("unused")
    protected synchronized void unbindObserver(Observer observer, Map<String, ?> config){
        observerCount.decrementAndGet();
    }

    @SuppressWarnings("unused")
    protected void bindBackgroundObserverMBean(BackgroundObserverMBean mbean, Map<String, ?> config){
        bgObservers.put(getObjectName(config), mbean);
    }

    @SuppressWarnings("unused")
    protected void unbindBackgroundObserverMBean(BackgroundObserverMBean mbean, Map<String, ?> config){
        bgObservers.remove(getObjectName(config));
    }

    @SuppressWarnings("unused")
    protected void bindListenerMBean(EventListenerMBean mbean, Map<String, ?> config){
        eventListeners.put(getObjectName(config), mbean);
    }

    @SuppressWarnings("unused")
    protected void unbindListenerMBean(EventListenerMBean mbean, Map<String, ?> config){
        eventListeners.remove(getObjectName(config));
    }

    @SuppressWarnings("unused")
    protected void bindFilterConfigMBean(FilterConfigMBean mbean, Map<String, ?> config){
        filterConfigs.put(getObjectName(config), mbean);
    }

    @SuppressWarnings("unused")
    protected void unbindFilterConfigMBean(FilterConfigMBean mbean, Map<String, ?> config){
        filterConfigs.remove(getObjectName(config));
    }

    private static ObjectName getObjectName(Map<String, ?> config){
        return checkNotNull((ObjectName) config.get("jmx.objectname"),
                "No 'jmx.objectname' property defined for MBean %s", config);
    }

    private static String getListenerId(ObjectName name){
        return name.getKeyProperty(ChangeProcessor.LISTENER_ID);
    }

    private static class ListenerMBeans {
        EventListenerMBean eventListenerMBean;
        BackgroundObserverMBean observerMBean;
        FilterConfigMBean filterConfigMBean;
    }

    //~------------------------------------------< JMX >

    private static class ListenerStatsData {
        static final String[] FIELD_NAMES = new String[]{
                "index",
                "className",
                "isDeep",
                "nodeTypeNames",
                "deliveries",
                "deliveries/hr",
                "us/delivery",
                "delivered",
                "delivered/hr",
                "us/delivered",
                "ratioOfTimeSpentProcessingEvents",
                "queueSize",
                "localEventCount",
                "externalEventCount",
                "paths",
                "clusterExternal",
                "clusterLocal",
                "maxQueueSize"
        };

        static final String[] FIELD_DESCRIPTIONS = FIELD_NAMES;

        @SuppressWarnings("rawtypes")
        static final OpenType[] FIELD_TYPES = new OpenType[]{
                SimpleType.INTEGER,
                SimpleType.STRING,
                SimpleType.BOOLEAN,
                SimpleType.STRING,
                SimpleType.LONG,
                SimpleType.LONG,
                SimpleType.LONG,
                SimpleType.LONG,
                SimpleType.LONG,
                SimpleType.LONG,
                SimpleType.DOUBLE,
                SimpleType.INTEGER,
                SimpleType.INTEGER,
                SimpleType.INTEGER,
                SimpleType.STRING,
                SimpleType.BOOLEAN,
                SimpleType.BOOLEAN,
                SimpleType.INTEGER,
        };

        static final CompositeType TYPE = createCompositeType();

        static CompositeType createCompositeType() {
            try {
                return new CompositeType(
                        ListenerStatsData.class.getName(),
                        "Composite data type for Listener statistics",
                        ListenerStatsData.FIELD_NAMES,
                        ListenerStatsData.FIELD_DESCRIPTIONS,
                        ListenerStatsData.FIELD_TYPES);
            } catch (OpenDataException e) {
                throw new IllegalStateException(e);
            }
        }

        private final ListenerMBeans mbeans;
        private final int index;

        public ListenerStatsData(int i, ListenerMBeans mbeans){
            this.index = i;
            this.mbeans = mbeans;
        }

        CompositeDataSupport toCompositeData() {
            Object[] values = new Object[]{
                    index,
                    mbeans.eventListenerMBean.getClassName(),
                    mbeans.eventListenerMBean.isDeep(),
                    Arrays.toString(mbeans.eventListenerMBean.getNodeTypeName()),
                    mbeans.eventListenerMBean.getEventDeliveries(),
                    mbeans.eventListenerMBean.getEventDeliveriesPerHour(),
                    mbeans.eventListenerMBean.getMicrosecondsPerEventDelivery(),
                    mbeans.eventListenerMBean.getEventsDelivered(),
                    mbeans.eventListenerMBean.getEventsDeliveredPerHour(),
                    mbeans.eventListenerMBean.getMicrosecondsPerEventDelivered(),
                    mbeans.eventListenerMBean.getRatioOfTimeSpentProcessingEvents(),
                    mbeans.observerMBean.getQueueSize(),
                    mbeans.observerMBean.getLocalEventCount(),
                    mbeans.observerMBean.getExternalEventCount(),
                    Arrays.toString(mbeans.filterConfigMBean.getSubTrees()),
                    mbeans.filterConfigMBean.isIncludeClusterExternal(),
                    mbeans.filterConfigMBean.isIncludeClusterLocal(),
                    mbeans.observerMBean.getMaxQueueSize(),
            };
            try {
                return new CompositeDataSupport(TYPE, FIELD_NAMES, values);
            } catch (OpenDataException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private static class ObserverStatsData {
        static final String[] FIELD_NAMES = new String[]{
                "index",
                "className",
                "maxQueueSize",
                "queueSize",
                "localEventCount",
                "externalEventCount",
        };

        static final String[] FIELD_DESCRIPTIONS = FIELD_NAMES;

        @SuppressWarnings("rawtypes")
        static final OpenType[] FIELD_TYPES = new OpenType[]{
                SimpleType.INTEGER,
                SimpleType.STRING,
                SimpleType.INTEGER,
                SimpleType.INTEGER,
                SimpleType.INTEGER,
                SimpleType.INTEGER,
        };

        static final CompositeType TYPE = createCompositeType();

        static CompositeType createCompositeType() {
            try {
                return new CompositeType(
                        ObserverStatsData.class.getName(),
                        "Composite data type for Observer statistics",
                        ObserverStatsData.FIELD_NAMES,
                        ObserverStatsData.FIELD_DESCRIPTIONS,
                        ObserverStatsData.FIELD_TYPES);
            } catch (OpenDataException e) {
                throw new IllegalStateException(e);
            }
        }

        private final int index;
        private BackgroundObserverMBean mbean;

        public ObserverStatsData(int i, BackgroundObserverMBean observer){
            this.index = i;
            this.mbean = observer;
        }

        CompositeDataSupport toCompositeData() {
            Object[] values = new Object[]{
                    index,
                    mbean.getClassName(),
                    mbean.getMaxQueueSize(),
                    mbean.getQueueSize(),
                    mbean.getLocalEventCount(),
                    mbean.getExternalEventCount(),

            };
            try {
                return new CompositeDataSupport(TYPE, FIELD_NAMES, values);
            } catch (OpenDataException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
