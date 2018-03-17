package org.w4tracking.security;

import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import java.util.Optional;

@Stateless
public class SecurityContextFactory {

    @Produces
    public static SecurityContext provideSecurityContext(
            @ConfigurationValue("w4tracking.security-context.type") Optional<String> sc,
            @SecurityContextType(name = SecurityContextType.IdentityProvider.DEFAULT) SecurityContext defaultSC,
            @SecurityContextType(name = SecurityContextType.IdentityProvider.KEYCLOAK) SecurityContext keycloakSC) {
        String defaultSc = sc.orElse("default");
        if ("default".equals(defaultSc)) {
            return defaultSC;
        } else if ("keycloak".equals(defaultSc)) {
            return keycloakSC;
        } else {
            throw new RuntimeException("Unknown security context type: " + sc);
        }
    }

}
