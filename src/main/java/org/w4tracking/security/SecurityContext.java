package org.w4tracking.security;

public interface SecurityContext {

    String getUsername();
    String getEmail();
    String getFullName();

    String getIdentityId();
    String getIdentityProviderAlias();

    String getRequestHeader(String headerName);
}
