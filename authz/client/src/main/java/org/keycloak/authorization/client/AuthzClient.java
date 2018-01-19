/*
 * JBoss, Home of Professional Open Source
 *
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
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
package org.keycloak.authorization.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.function.Supplier;

import org.keycloak.authorization.client.representation.ServerConfiguration;
import org.keycloak.authorization.client.resource.AuthorizationResource;
import org.keycloak.authorization.client.resource.ProtectionResource;
import org.keycloak.authorization.client.util.Http;
import org.keycloak.jose.jws.JWSInput;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.util.JsonSerialization;

/**
 * <p>This is class serves as an entry point for clients looking for access to Keycloak Authorization Services.
 *
 * <p>When creating a new instances make sure you have a Keycloak Server running at the location specified in the client
 * configuration. The client tries to obtain server configuration by invoking the UMA Discovery Endpoint, usually available
 * from the server at <i>http(s)://{server}:{port}/auth/realms/{realm}/.well-known/uma-configuration</i>.
 *
 * @author <a href="mailto:psilva@redhat.com">Pedro Igor</a>
 */
public class AuthzClient {

    private final Http http;
    private Supplier<String> patSupplier;

    /**
     * <p>Creates a new instance.
     *
     * <p>This method expects a <code>keycloak.json</code> in the classpath, otherwise an exception will be thrown.
     *
     * @return a new instance
     * @throws RuntimeException in case there is no <code>keycloak.json</code> file in the classpath or the file could not be parsed
     */
    public static AuthzClient create() throws RuntimeException {
        InputStream configStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("keycloak.json");

        if (configStream == null) {
            throw new RuntimeException("Could not find any keycloak.json file in classpath.");
        }

        try {
            return create(JsonSerialization.readValue(configStream, Configuration.class));
        } catch (IOException e) {
            throw new RuntimeException("Could not parse configuration.", e);
        }
    }

    /**
     * <p>Creates a new instance.
     *
     * @param configuration the client configuration
     * @return a new instance
     */
    public static AuthzClient create(Configuration configuration) {
        return new AuthzClient(configuration, configuration.getClientAuthenticator());
    }

    /**
     * <p>Creates a new instance.
     *
     * @param configuration the client configuration
     * @param authenticator the client authenticator
     * @return a new instance
     */
    public static AuthzClient create(Configuration configuration, ClientAuthenticator authenticator) {
        return new AuthzClient(configuration, authenticator);
    }

    private final ServerConfiguration serverConfiguration;
    private final Configuration configuration;

    /**
     * <p>Creates a {@link ProtectionResource} instance which can be used to access the Protection API.
     *
     * <p>When using this method, the PAT (the access token with the uma_protection scope) is obtained for the client
     * itself, using any of the supported credential types (client/secret, jwt, etc).
     *
     * @return a {@link ProtectionResource}
     */
    public ProtectionResource protection() {
        return new ProtectionResource(this.http, this.configuration, createPatSupplier());
    }

    /**
     * <p>Creates a {@link ProtectionResource} instance which can be used to access the Protection API.
     *
     * @param the PAT (the access token with the uma_protection scope)
     * @return a {@link ProtectionResource}
     */
    public ProtectionResource protection(final String accessToken) {
        return new ProtectionResource(this.http, this.configuration, new Supplier<String>() {
            @Override
            public String get() {
                return accessToken;
            }
        });
    }

    /**
     * <p>Creates a {@link ProtectionResource} instance which can be used to access the Protection API.
     *
     * <p>When using this method, the PAT (the access token with the uma_protection scope) is obtained for a given user.
     *
     * @return a {@link ProtectionResource}
     */
    public ProtectionResource protection(String userName, String password) {
        return new ProtectionResource(this.http, this.configuration, createPatSupplier(userName, password));
    }

    /**
     * <p>Creates a {@link AuthorizationResource} instance which can be used to obtain permissions from the server.
     *
     * @return a {@link AuthorizationResource}
     */
    public AuthorizationResource authorization() {
        return new AuthorizationResource(configuration, serverConfiguration, this.http, null);
    }

    /**
     * <p>Creates a {@link AuthorizationResource} instance which can be used to obtain permissions from the server.
     *
     * @param claimToken an ID Token or Access Token representing an identity and/or access context
     * @return a {@link AuthorizationResource}
     */
    public AuthorizationResource authorization(final String claimToken) {
        return new AuthorizationResource(configuration, serverConfiguration, this.http, new Supplier<String>() {
            @Override
            public String get() {
                return claimToken;
            }
        });
    }

    /**
     * <p>Creates a {@link AuthorizationResource} instance which can be used to obtain permissions from the server.
     *
     * @param userName an ID Token or Access Token representing an identity and/or access context
     * @param password
     * @return a {@link AuthorizationResource}
     */
    public AuthorizationResource authorization(final String userName, final String password) {
        return new AuthorizationResource(configuration, serverConfiguration, this.http, createRefreshableAccessTokenSupplier(userName, password));
    }

    /**
     * Obtains an access token using the client credentials.
     *
     * @return an {@link AccessTokenResponse}
     */
    public AccessTokenResponse obtainAccessToken() {
        return this.http.<AccessTokenResponse>post(this.serverConfiguration.getTokenEndpoint())
                .authentication()
                    .client()
                .response()
                    .json(AccessTokenResponse.class)
                .execute();
    }

    /**
     * Obtains an access token using the resource owner credentials.
     *
     * @return an {@link AccessTokenResponse}
     */
    public AccessTokenResponse obtainAccessToken(String userName, String password) {
        return this.http.<AccessTokenResponse>post(this.serverConfiguration.getTokenEndpoint())
                .authentication()
                    .oauth2ResourceOwnerPassword(userName, password)
                .response()
                    .json(AccessTokenResponse.class)
                .execute();
    }

    /**
     * Returns the configuration obtained from the server at the UMA Discovery Endpoint.
     *
     * @return the {@link ServerConfiguration}
     */
    public ServerConfiguration getServerConfiguration() {
        return this.serverConfiguration;
    }

    /**
     * Obtains the client configuration
     *
     * @return the {@link Configuration}
     */
    public Configuration getConfiguration() {
        return this.configuration;
    }

    private AuthzClient(Configuration configuration, ClientAuthenticator authenticator) {
        if (configuration == null) {
            throw new IllegalArgumentException("Client configuration can not be null.");
        }

        String configurationUrl = configuration.getAuthServerUrl();

        if (configurationUrl == null) {
            throw new IllegalArgumentException("Configuration URL can not be null.");
        }

        configurationUrl += "/realms/" + configuration.getRealm() + "/.well-known/uma-configuration";

        this.configuration = configuration;

        this.http = new Http(configuration, authenticator != null ? authenticator : configuration.getClientAuthenticator());

        try {
            this.serverConfiguration = this.http.<ServerConfiguration>get(URI.create(configurationUrl))
                    .response().json(ServerConfiguration.class)
                    .execute();
        } catch (Exception e) {
            throw new RuntimeException("Could not obtain configuration from server [" + configurationUrl + "].", e);
        }

        this.http.setServerConfiguration(this.serverConfiguration);
    }

    private Supplier<String> createRefreshableAccessTokenSupplier() {
        return createRefreshableAccessTokenSupplier(null, null);
    }

    private Supplier<String> createPatSupplier(String userName, String password) {
        if (patSupplier == null) {
            patSupplier = createRefreshableAccessTokenSupplier(userName, password);
        }
        return patSupplier;
    }

    private Supplier<String> createPatSupplier() {
        return createPatSupplier(null, null);
    }

    private Supplier<String> createRefreshableAccessTokenSupplier(final String userName, final String password) {
        return new Supplier<String>() {
            AccessTokenResponse clientToken;

            @Override
            public String get() {
                if (clientToken == null) {
                    if (userName == null || password == null) {
                        clientToken = obtainAccessToken();
                    } else {
                        clientToken = obtainAccessToken(userName, password);
                    }
                }

                String token = clientToken.getToken();

                try {
                    AccessToken accessToken = JsonSerialization.readValue(new JWSInput(token).getContent(), AccessToken.class);

                    if (accessToken.isActive()) {
                        return token;
                    }

                    clientToken = http.<AccessTokenResponse>post(serverConfiguration.getTokenEndpoint())
                            .authentication().client()
                            .form()
                            .param("grant_type", "refresh_token")
                            .param("refresh_token", clientToken.getRefreshToken())
                            .response()
                            .json(AccessTokenResponse.class)
                            .execute();
                } catch (Exception e) {
                    patSupplier = null;
                    throw new RuntimeException(e);
                }

                return clientToken.getToken();
            }
        };
    }
}