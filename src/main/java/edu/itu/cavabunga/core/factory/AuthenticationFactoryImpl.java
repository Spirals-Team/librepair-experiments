package edu.itu.cavabunga.core.factory;

import edu.itu.cavabunga.core.entity.Authentication;
import edu.itu.cavabunga.core.entity.authentication.AuthenticationType;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFactoryImpl implements AuthenticationFactory {
    @Override
    public Authentication createAuthentication(AuthenticationType authenticationType){
        Authentication result = new Authentication();
        result.setAuthenticationType(authenticationType);
        return result;
    }
}
