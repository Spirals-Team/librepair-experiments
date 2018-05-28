package com.microsoft.spring.data.gremlin.common;

import com.microsoft.applicationinsights.TelemetryClient;
import fr.inria.spirals.npefix.resi.CallChecker;
import fr.inria.spirals.npefix.resi.context.ConstructorContext;
import fr.inria.spirals.npefix.resi.context.MethodContext;
import fr.inria.spirals.npefix.resi.exception.ForceReturn;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class TelemetryProxy {
    private TelemetryClient client;

    private boolean isTelemetryAllowed;

    private static final String PROJECT_INFO = "spring-data-gremlin/" + (PropertyLoader.getProjectVersion());

    public TelemetryProxy(boolean isTelemetryAllowed) {
        ConstructorContext _bcornu_methode_context1 = new ConstructorContext(TelemetryProxy.class, 21, 626, 781);
        try {
            this.client = new TelemetryClient();
            CallChecker.varAssign(this.client, "this.client", 22, 686, 721);
            this.isTelemetryAllowed = isTelemetryAllowed;
            CallChecker.varAssign(this.isTelemetryAllowed, "this.isTelemetryAllowed", 23, 731, 775);
        } finally {
            _bcornu_methode_context1.methodEnd();
        }
    }

    public void trackEvent(@NonNull
    String name, @Nullable
    Map<String, String> customProperties) {
        MethodContext _bcornu_methode_context1 = new MethodContext(void.class, 26, 788, 943);
        try {
            CallChecker.varInit(this, "this", 26, 788, 943);
            CallChecker.varInit(customProperties, "customProperties", 26, 788, 943);
            CallChecker.varInit(name, "name", 26, 788, 943);
            CallChecker.varInit(PROJECT_INFO, "com.microsoft.spring.data.gremlin.common.TelemetryProxy.PROJECT_INFO", 26, 788, 943);
            CallChecker.varInit(this.isTelemetryAllowed, "isTelemetryAllowed", 26, 788, 943);
            CallChecker.varInit(this.client, "client", 26, 788, 943);
            this.trackEvent(name, customProperties, false);
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context1.methodEnd();
        }
    }

    public void trackEvent(@NonNull
    String name, @Nullable
    Map<String, String> customProperties, boolean isOverrideDefault) {
        MethodContext _bcornu_methode_context2 = new MethodContext(void.class, 30, 950, 1405);
        try {
            CallChecker.varInit(this, "this", 30, 950, 1405);
            CallChecker.varInit(isOverrideDefault, "isOverrideDefault", 30, 950, 1405);
            CallChecker.varInit(customProperties, "customProperties", 30, 950, 1405);
            CallChecker.varInit(name, "name", 30, 950, 1405);
            CallChecker.varInit(PROJECT_INFO, "com.microsoft.spring.data.gremlin.common.TelemetryProxy.PROJECT_INFO", 30, 950, 1405);
            CallChecker.varInit(this.isTelemetryAllowed, "isTelemetryAllowed", 30, 950, 1405);
            CallChecker.varInit(this.client, "client", 30, 950, 1405);
            if (this.isTelemetryAllowed) {
                Map<String, String> properties = CallChecker.varInit(this.getDefaultProperties(), "properties", 33, 1150, 1210);
                properties = this.mergeProperties(properties, customProperties, isOverrideDefault);
                CallChecker.varAssign(properties, "properties", 34, 1224, 1306);
                if (CallChecker.beforeDeref(client, TelemetryClient.class, 35, 1320, 1325)) {
                    client = CallChecker.beforeCalled(client, TelemetryClient.class, 35, 1320, 1325);
                    CallChecker.isCalled(client, TelemetryClient.class, 35, 1320, 1325).trackEvent(name, properties, null);
                }
                if (CallChecker.beforeDeref(client, TelemetryClient.class, 36, 1375, 1380)) {
                    client = CallChecker.beforeCalled(client, TelemetryClient.class, 36, 1375, 1380);
                    CallChecker.isCalled(client, TelemetryClient.class, 36, 1375, 1380).flush();
                }
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context2.methodEnd();
        }
    }

    private Map<String, String> mergeProperties(@NonNull
    Map<String, String> defaultProperties, @Nullable
    Map<String, String> customProperties, boolean isOverrideDefault) {
        MethodContext _bcornu_methode_context3 = new MethodContext(Map.class, 40, 1412, 2346);
        try {
            CallChecker.varInit(this, "this", 40, 1412, 2346);
            CallChecker.varInit(isOverrideDefault, "isOverrideDefault", 40, 1412, 2346);
            CallChecker.varInit(customProperties, "customProperties", 40, 1412, 2346);
            CallChecker.varInit(defaultProperties, "defaultProperties", 40, 1412, 2346);
            CallChecker.varInit(PROJECT_INFO, "com.microsoft.spring.data.gremlin.common.TelemetryProxy.PROJECT_INFO", 40, 1412, 2346);
            CallChecker.varInit(this.isTelemetryAllowed, "isTelemetryAllowed", 40, 1412, 2346);
            CallChecker.varInit(this.client, "client", 40, 1412, 2346);
            if ((customProperties == null) || (customProperties.isEmpty())) {
                return defaultProperties;
            }
            final Map<String, String> mergedProperties = CallChecker.varInit(new HashMap<>(), "mergedProperties", 47, 1804, 1864);
            if (isOverrideDefault) {
                if (CallChecker.beforeDeref(mergedProperties, Map.class, 50, 1912, 1927)) {
                    CallChecker.isCalled(mergedProperties, Map.class, 50, 1912, 1927).putAll(defaultProperties);
                }
                if (CallChecker.beforeDeref(mergedProperties, Map.class, 51, 1968, 1983)) {
                    CallChecker.isCalled(mergedProperties, Map.class, 51, 1968, 1983).putAll(customProperties);
                }
            }else {
                if (CallChecker.beforeDeref(mergedProperties, Map.class, 53, 2040, 2055)) {
                    CallChecker.isCalled(mergedProperties, Map.class, 53, 2040, 2055).putAll(customProperties);
                }
                if (CallChecker.beforeDeref(mergedProperties, Map.class, 54, 2095, 2110)) {
                    CallChecker.isCalled(mergedProperties, Map.class, 54, 2095, 2110).putAll(defaultProperties);
                }
            }
            if (CallChecker.beforeDeref(mergedProperties, Map.class, 57, 2158, 2173)) {
                CallChecker.isCalled(mergedProperties, Map.class, 57, 2158, 2173).forEach(( key, value) -> {
                    if (value.isEmpty()) {
                        mergedProperties.remove(key);
                    }
                });
            }
            return mergedProperties;
        } catch (ForceReturn _bcornu_return_t) {
            return ((Map<String, String>) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context3.methodEnd();
        }
    }

    private Map<String, String> getDefaultProperties() {
        MethodContext _bcornu_methode_context4 = new MethodContext(Map.class, 66, 2353, 2674);
        try {
            CallChecker.varInit(this, "this", 66, 2353, 2674);
            CallChecker.varInit(PROJECT_INFO, "com.microsoft.spring.data.gremlin.common.TelemetryProxy.PROJECT_INFO", 66, 2353, 2674);
            CallChecker.varInit(this.isTelemetryAllowed, "isTelemetryAllowed", 66, 2353, 2674);
            CallChecker.varInit(this.client, "client", 66, 2353, 2674);
            final Map<String, String> properties = CallChecker.varInit(new HashMap<>(), "properties", 67, 2414, 2468);
            if (CallChecker.beforeDeref(properties, Map.class, 69, 2479, 2488)) {
                CallChecker.isCalled(properties, Map.class, 69, 2479, 2488).put(TelemetryProperties.PROPERTY_INSTALLATION_ID, GetHashMac.getHashMac());
            }
            if (CallChecker.beforeDeref(properties, Map.class, 70, 2574, 2583)) {
                CallChecker.isCalled(properties, Map.class, 70, 2574, 2583).put(TelemetryProperties.PROPERTY_VERSION, TelemetryProxy.PROJECT_INFO);
            }
            return properties;
        } catch (ForceReturn _bcornu_return_t) {
            return ((Map<String, String>) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context4.methodEnd();
        }
    }
}

