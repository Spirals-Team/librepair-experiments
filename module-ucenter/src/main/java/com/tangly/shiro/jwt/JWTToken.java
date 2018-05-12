package com.tangly.shiro.jwt;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author tangly
 */
@Data
public class JWTToken implements AuthenticationToken {

    private String jwt;

    private String host;

    public JWTToken(String jwt , String host) {
        this.host = host;
        this.jwt = jwt;
    }

    @Override
    public Object getPrincipal() {
        return this.jwt;
    }

    @Override
    public Object getCredentials() {
        return jwt;
    }

}
