/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facebook.presto.client;

import com.facebook.presto.spi.type.TimeZoneKey;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.airlift.units.Duration;

import java.net.URI;
import java.nio.charset.CharsetEncoder;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static com.google.common.base.MoreObjects.toStringHelper;
import static com.google.common.base.Preconditions.checkArgument;
import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.util.Objects.requireNonNull;

public class ClientSession
{
    private final URI server;
    private final String user;
    private final String source;
    private final Set<String> clientTags;
    private final String clientInfo;
    private final String catalog;
    private final String schema;
    private final TimeZoneKey timeZone;
    private final Locale locale;
    private final Map<String, String> properties;
    private final Map<String, String> preparedStatements;
    private final String transactionId;
    private final boolean debug;
    private final Duration clientRequestTimeout;

    public static ClientSession withCatalogAndSchema(ClientSession session, String catalog, String schema)
    {
        return new ClientSession(
                session.getServer(),
                session.getUser(),
                session.getSource(),
                session.getClientTags(),
                session.getClientInfo(),
                catalog,
                schema,
                session.getTimeZone().getId(),
                session.getLocale(),
                session.getProperties(),
                session.getPreparedStatements(),
                session.getTransactionId(),
                session.isDebug(),
                session.getClientRequestTimeout());
    }

    public static ClientSession withProperties(ClientSession session, Map<String, String> properties)
    {
        return new ClientSession(
                session.getServer(),
                session.getUser(),
                session.getSource(),
                session.getClientTags(),
                session.getClientInfo(),
                session.getCatalog(),
                session.getSchema(),
                session.getTimeZone().getId(),
                session.getLocale(),
                properties,
                session.getPreparedStatements(),
                session.getTransactionId(),
                session.isDebug(),
                session.getClientRequestTimeout());
    }

    public static ClientSession withPreparedStatements(ClientSession session, Map<String, String> preparedStatements)
    {
        return new ClientSession(
                session.getServer(),
                session.getUser(),
                session.getSource(),
                session.getClientTags(),
                session.getClientInfo(),
                session.getCatalog(),
                session.getSchema(),
                session.getTimeZone().getId(),
                session.getLocale(),
                session.getProperties(),
                preparedStatements,
                session.getTransactionId(),
                session.isDebug(),
                session.getClientRequestTimeout());
    }

    public static ClientSession withTransactionId(ClientSession session, String transactionId)
    {
        return new ClientSession(
                session.getServer(),
                session.getUser(),
                session.getSource(),
                session.getClientTags(),
                session.getClientInfo(),
                session.getCatalog(),
                session.getSchema(),
                session.getTimeZone().getId(),
                session.getLocale(),
                session.getProperties(),
                session.getPreparedStatements(),
                transactionId,
                session.isDebug(),
                session.getClientRequestTimeout());
    }

    public static ClientSession stripTransactionId(ClientSession session)
    {
        return new ClientSession(
                session.getServer(),
                session.getUser(),
                session.getSource(),
                session.getClientTags(),
                session.getClientInfo(),
                session.getCatalog(),
                session.getSchema(),
                session.getTimeZone().getId(),
                session.getLocale(),
                session.getProperties(),
                session.getPreparedStatements(),
                null,
                session.isDebug(),
                session.getClientRequestTimeout());
    }

    public ClientSession(
            URI server,
            String user,
            String source,
            Set<String> clientTags,
            String clientInfo,
            String catalog,
            String schema,
            String timeZoneId,
            Locale locale,
            Map<String, String> properties,
            Map<String, String> preparedStatements,
            String transactionId,
            boolean debug,
            Duration clientRequestTimeout)
    {
        this.server = requireNonNull(server, "server is null");
        this.user = user;
        this.source = source;
        this.clientTags = ImmutableSet.copyOf(requireNonNull(clientTags, "clientTags is null"));
        this.clientInfo = clientInfo;
        this.catalog = catalog;
        this.schema = schema;
        this.locale = locale;
        this.timeZone = TimeZoneKey.getTimeZoneKey(timeZoneId);
        this.transactionId = transactionId;
        this.debug = debug;
        this.properties = ImmutableMap.copyOf(requireNonNull(properties, "properties is null"));
        this.preparedStatements = ImmutableMap.copyOf(requireNonNull(preparedStatements, "preparedStatements is null"));
        this.clientRequestTimeout = clientRequestTimeout;

        for (String clientTag : clientTags) {
            checkArgument(!clientTag.contains(","), "client tag cannot contain ','");
        }

        // verify the properties are valid
        CharsetEncoder charsetEncoder = US_ASCII.newEncoder();
        for (Entry<String, String> entry : properties.entrySet()) {
            checkArgument(!entry.getKey().isEmpty(), "Session property name is empty");
            checkArgument(entry.getKey().indexOf('=') < 0, "Session property name must not contain '=': %s", entry.getKey());
            checkArgument(charsetEncoder.canEncode(entry.getKey()), "Session property name is not US_ASCII: %s", entry.getKey());
            checkArgument(charsetEncoder.canEncode(entry.getValue()), "Session property value is not US_ASCII: %s", entry.getValue());
        }
    }

    public URI getServer()
    {
        return server;
    }

    public String getUser()
    {
        return user;
    }

    public String getSource()
    {
        return source;
    }

    public Set<String> getClientTags()
    {
        return clientTags;
    }

    public String getClientInfo()
    {
        return clientInfo;
    }

    public String getCatalog()
    {
        return catalog;
    }

    public String getSchema()
    {
        return schema;
    }

    public TimeZoneKey getTimeZone()
    {
        return timeZone;
    }

    public Locale getLocale()
    {
        return locale;
    }

    public Map<String, String> getProperties()
    {
        return properties;
    }

    public Map<String, String> getPreparedStatements()
    {
        return preparedStatements;
    }

    public String getTransactionId()
    {
        return transactionId;
    }

    public boolean isDebug()
    {
        return debug;
    }

    public Duration getClientRequestTimeout()
    {
        return clientRequestTimeout;
    }

    @Override
    public String toString()
    {
        return toStringHelper(this)
                .add("server", server)
                .add("user", user)
                .add("clientTags", clientTags)
                .add("clientInfo", clientInfo)
                .add("catalog", catalog)
                .add("schema", schema)
                .add("timeZone", timeZone)
                .add("locale", locale)
                .add("properties", properties)
                .add("transactionId", transactionId)
                .add("debug", debug)
                .toString();
    }
}
