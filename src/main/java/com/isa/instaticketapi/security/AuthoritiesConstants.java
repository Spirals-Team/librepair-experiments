package com.isa.instaticketapi.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String SUPER_ADMIN = "SUPER_ADMIN";

    public static final String ADMIN = "ADMIN";
    
    public static final String FANZONE_ADMIN = "FANZONE_ADMIN";

    public static final String USER = "USER";

    public static final String ANONYMOUS = "ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
