package org.w4tracking.security;

import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;

@Stateless
@SecurityContextType(name = SecurityContextType.IdentityProvider.KEYCLOAK)
public class KeycloakSecurityContext extends AbstractSecurityContext {

    @Override
    public String getRequestHeader(String headerName) {
        return DefaultSecurityContext.servletRequest.get().getHeader(headerName);
    }

    @Override
    public String getUsername() {
        return DefaultSecurityContext.servletRequest.get().getRemoteUser();
    }

    @Override
    public String getFullName() {
        HttpServletRequest request = DefaultSecurityContext.servletRequest.get();
        org.keycloak.KeycloakSecurityContext session = (org.keycloak.KeycloakSecurityContext) request.getAttribute(org.keycloak.KeycloakSecurityContext.class.getName());
        if (session != null) {
            return session.getToken().getName();
        } else {
            return null;
        }
    }

    @Override
    public String getEmail() {
        HttpServletRequest request = DefaultSecurityContext.servletRequest.get();
        org.keycloak.KeycloakSecurityContext session = (org.keycloak.KeycloakSecurityContext) request.getAttribute(org.keycloak.KeycloakSecurityContext.class.getName());
        if (session != null) {
            return session.getToken().getEmail();
        } else {
            return null;
        }
    }

    @Override
    public String getIdentityId() {
        return null;
    }

    @Override
    public String getIdentityProviderAlias() {
        return "keycloak";
    }

}
