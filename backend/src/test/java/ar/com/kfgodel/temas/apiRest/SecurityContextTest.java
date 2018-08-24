package ar.com.kfgodel.temas.apiRest;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * Created by fede on 22/06/17.
 */
public class SecurityContextTest implements SecurityContext {

    protected  Long principal;

    @Override
    public Principal getUserPrincipal() {
        return new JettyIdentityAdapterTest(principal);
    }

    @Override
    public boolean isUserInRole(String s) {
        return true;
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    @Override
    public String getAuthenticationScheme() {
        return null;
    }

    public  SecurityContextTest(Long principal){
        this.principal=principal;
    }
}
