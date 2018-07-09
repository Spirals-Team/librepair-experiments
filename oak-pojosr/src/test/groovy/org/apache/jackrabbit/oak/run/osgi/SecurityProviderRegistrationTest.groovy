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
package org.apache.jackrabbit.oak.run.osgi

import org.apache.felix.connect.launch.PojoServiceRegistry
import org.apache.felix.scr.Component
import org.apache.felix.scr.ScrService
import org.apache.jackrabbit.oak.spi.security.SecurityProvider
import org.apache.jackrabbit.oak.spi.security.authentication.token.TokenConfiguration
import org.apache.jackrabbit.oak.spi.security.authorization.AuthorizationConfiguration
import org.apache.jackrabbit.oak.spi.security.authorization.restriction.RestrictionProvider
import org.apache.jackrabbit.oak.spi.security.principal.PrincipalConfiguration
import org.apache.jackrabbit.oak.spi.security.user.AuthorizableNodeName
import org.apache.jackrabbit.oak.spi.security.user.UserAuthenticationFactory
import org.apache.jackrabbit.oak.spi.security.user.UserConfiguration
import org.apache.jackrabbit.oak.spi.security.user.action.AccessControlAction
import org.apache.jackrabbit.oak.spi.security.user.action.AuthorizableAction
import org.apache.jackrabbit.oak.spi.security.user.action.AuthorizableActionProvider
import org.junit.Before
import org.junit.Test
import org.osgi.framework.ServiceReference
import org.osgi.service.cm.ConfigurationAdmin

import java.util.concurrent.TimeUnit

import static org.mockito.Mockito.mock

class SecurityProviderRegistrationTest extends AbstractRepositoryFactoryTest {

    private PojoServiceRegistry registry;

    @Before
    public void initializeRegistry() {
        registry = repositoryFactory.initializeServiceRegistry(config)
    }

    @Override
    protected PojoServiceRegistry getRegistry() {
        return registry
    }
/**
     * Test that, without any additional configuration, a SecurityProvider
     * service is registered by default.
     */
    @Test
    public void testDefaultSetup() {
        assert securityProviderServiceReferences != null
    }

    /**
     * A SecurityProvider shouldn't start without a required
     * PrincipalConfiguration service.
     */
    @Test
    public void testRequiredPrincipalConfigurationNotAvailable() {
        testRequiredService(PrincipalConfiguration, mock(PrincipalConfiguration))
    }

    /**
     * A SecurityProvider shouldn't start without a required TokenConfiguration
     * service.
     */
    @Test
    public void testRequiredTokenConfigurationNotAvailable() {
        testRequiredService(TokenConfiguration, mock(TokenConfiguration))
    }

    /**
     * A SecurityProvider shouldn't start without a required
     * AuthorizableNodeName service.
     */
    @Test
    public void testRequiredAuthorizableNodeNameNotAvailable() {
        testRequiredService(AuthorizableNodeName, mock(AuthorizableNodeName))
    }

    /**
     * A SecurityProvider shouldn't start without a required
     * AuthorizableActionProvider service.
     */
    @Test
    public void testRequiredAuthorizableActionProviderNotAvailable() {
        testRequiredService(AuthorizableActionProvider, mock(AuthorizableActionProvider))
    }

    /**
     * A SecurityProvider shouldn't start without a required RestrictionProvider
     * service.
     */
    @Test
    public void testRequiredRestrictionProviderNotAvailable() {
        testRequiredService(RestrictionProvider, mock(RestrictionProvider))
    }

    /**
     * A SecurityProvider shouldn't start without a required
     * UserAuthenticationFactory service.
     */
    @Test
    public void testRequiredUserAuthenticationFactoryNotAvailable() {
        testRequiredService(UserAuthenticationFactory, mock(UserAuthenticationFactory))
    }

    /**
     * A SecurityProvider should be registered only if every every prerequisite
     * is satisfied.
     */
    @Test
    public void testMultipleRequiredServices() {

        // Set up the SecurityProvider to require three services

        setRequiredServicePids("test.RequiredPrincipalConfiguration", "test.RequiredTokenConfiguration", "test.AuthorizableNodeName")
        TimeUnit.MILLISECONDS.sleep(500)
        assert securityProviderServiceReferences == null

        // Start the services and verify that only at the end the
        // SecurityProvider registers itself

        registry.registerService(PrincipalConfiguration.class.name, mock(PrincipalConfiguration), dict("service.pid": "test.RequiredPrincipalConfiguration"))
        assert securityProviderServiceReferences == null

        registry.registerService(TokenConfiguration.class.name, mock(TokenConfiguration), dict("service.pid": "test.RequiredTokenConfiguration"))
        assert securityProviderServiceReferences == null

        registry.registerService(TokenConfiguration.class.name, mock(TokenConfiguration), dict("service.pid": "test.AuthorizableNodeName"))
        assert securityProviderServiceReferences != null
    }

    @Test
    public void testSecurityConfigurations() {
        //Keep a dummy reference to UserConfiguration such that SCR does not deactivate it
        //once SecurityProviderRegistration gets deactivated. Otherwise if SecurityProviderRegistration is
        //the only component that refers it then upon its deactivation UserConfiguration would also be
        //deactivate and its internal state would be reset
        UserConfiguration userConfiguration = getServiceWithWait(UserConfiguration.class)

        ScrService scr = getServiceWithWait(ScrService.class)
        Component[] c = scr.getComponents('org.apache.jackrabbit.oak.security.authentication.AuthenticationConfigurationImpl')
        assert c

        // 1. Disable AuthenticationConfiguration such that SecurityProvider is unregistered
        c[0].disable()

        TimeUnit.SECONDS.sleep(1)

        assert securityProviderServiceReferences == null

        // 2. Modify the config for AuthorizableActionProvider. It's expected that this config change is picked up
        setConfiguration([
                "org.apache.jackrabbit.oak.spi.security.user.action.DefaultAuthorizableActionProvider": [
                        "enabledActions":"org.apache.jackrabbit.oak.spi.security.user.action.AccessControlAction",
                        "groupPrivilegeNames":"jcr:read"
                ]
        ])

        TimeUnit.SECONDS.sleep(1)

        // 3. Enable component again such that SecurityProvider gets reactivated
        c[0].enable()

        SecurityProvider securityProvider = getServiceWithWait(SecurityProvider.class)
        assertAuthorizationConfig(securityProvider)
        assertUserConfig(securityProvider, "jcr:read")
    }

    @Test
    public void testSecurityConfigurations2() {
        setConfiguration([
                "org.apache.jackrabbit.oak.spi.security.user.action.DefaultAuthorizableActionProvider": [
                        "enabledActions":"org.apache.jackrabbit.oak.spi.security.user.action.AccessControlAction"
                ]
        ])
        SecurityProvider securityProvider = registry.getService(registry.getServiceReference(SecurityProvider.class.name))
        assertAuthorizationConfig(securityProvider)
        assertUserConfig(securityProvider)

        //Keep a dummy reference to UserConfiguration such that SCR does not deactivate it
        //once SecurityProviderRegistration gets deactivated. Otherwise if SecurityProviderRegistration is
        //the only component that refers it then upon its deactivation UserConfiguration would also be
        //deactivate and its internal state would be reset
        UserConfiguration userConfiguration = getServiceWithWait(UserConfiguration.class)

        //1. Modify the config for AuthorizableActionProvider. It's expected that this config change is picked up
        setConfiguration([
                "org.apache.jackrabbit.oak.spi.security.user.action.DefaultAuthorizableActionProvider": [
                        "enabledActions":"org.apache.jackrabbit.oak.spi.security.user.action.AccessControlAction",
                        "groupPrivilegeNames":"jcr:read"
                ]
        ])

        TimeUnit.SECONDS.sleep(1)

        securityProvider = getServiceWithWait(SecurityProvider.class)
        assertAuthorizationConfig(securityProvider)
        assertUserConfig(securityProvider, "jcr:read")

        ScrService scr = getServiceWithWait(ScrService.class)
        Component[] c = scr.getComponents('org.apache.jackrabbit.oak.security.authentication.AuthenticationConfigurationImpl')
        assert c

        // 2. Disable AuthenticationConfiguration such that SecurityProvider is unregistered
        c[0].disable()

        TimeUnit.SECONDS.sleep(1)

        assert securityProviderServiceReferences == null

        // 3. Enable component again such that SecurityProvider gets reactivated
        c[0].enable()

        securityProvider = getServiceWithWait(SecurityProvider.class)
        assertAuthorizationConfig(securityProvider)
        assertUserConfig(securityProvider, "jcr:read")
    }

    private <T> void testRequiredService(Class<T> serviceClass, T service) {

        // Adding a new precondition on a missing service PID forces the
        // SecurityProvider to unregister.

        setRequiredServicePids("test.Required" + serviceClass.simpleName)
        TimeUnit.MILLISECONDS.sleep(500)
        assert securityProviderServiceReferences == null

        // If a service is registered, and if the PID of the service matches the
        // precondition, the SecurityProvider is registered again.

        def registration = registry.registerService(serviceClass.name, service, dict("service.pid": "test.Required" + serviceClass.simpleName))
        assert securityProviderServiceReferences != null

        // If the service is unregistered, but the precondition is still in
        // place, the SecurityProvider unregisters again.

        registration.unregister()
        assert securityProviderServiceReferences == null

        // Removing the precondition allows the SecurityProvider to register.

        setRequiredServicePids()
        TimeUnit.MILLISECONDS.sleep(500)
        assert securityProviderServiceReferences != null
    }

    private ServiceReference<?>[] getSecurityProviderServiceReferences() {
        return registry.getServiceReferences(SecurityProvider.class.name, "(type=default)")
    }

    private void setRequiredServicePids(String... pids) {
        setConfiguration([
                "org.apache.jackrabbit.oak.security.internal.SecurityProviderRegistration": [
                        "requiredServicePids": pids
                ]
        ])
    }

    private void setConfiguration(Map<String, Map<String, Object>> configuration) {
        getConfigurationInstaller().installConfigs(configuration)
    }

    private ConfigInstaller getConfigurationInstaller() {
        return new ConfigInstaller(getConfigurationAdmin(), registry.bundleContext)
    }

    private ConfigurationAdmin getConfigurationAdmin() {
        return registry.getService(registry.getServiceReference(ConfigurationAdmin.class.name)) as ConfigurationAdmin
    }

    private static <K, V> Dictionary<K, V> dict(Map<K, V> map) {
        return new Hashtable<K, V>(map);
    }

    private static void assertAuthorizationConfig(SecurityProvider securityProvider, String... adminPrincipals) {
        AuthorizationConfiguration ac = securityProvider.getConfiguration(AuthorizationConfiguration.class)

        assert ac.getParameters().containsKey("restrictionProvider")
        assert ac.getRestrictionProvider() != null
        assert ac.getParameters().get("restrictionProvider") == ac.getRestrictionProvider()

        String[] found = ac.getParameters().getConfigValue("administrativePrincipals", new String[0])
        assert Arrays.equals(adminPrincipals, found)
    }

    private static void assertUserConfig(SecurityProvider securityProvider, String... groupPrivilegeNames) {
        UserConfiguration uc = securityProvider.getConfiguration(UserConfiguration.class)

        assert uc.getParameters().containsKey("authorizableActionProvider")
        AuthorizableActionProvider ap = uc.getParameters().get("authorizableActionProvider")
        assert ap != null;

        List<AuthorizableAction> actionList = ap.getAuthorizableActions(securityProvider)
        assert actionList.size() == 1
        AuthorizableAction action = actionList.get(0)
        assert Arrays.equals(groupPrivilegeNames, ((AccessControlAction) action).groupPrivilegeNames)
    }

}
