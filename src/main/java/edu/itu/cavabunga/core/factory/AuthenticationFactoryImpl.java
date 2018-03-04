package edu.itu.cavabunga.core.factory;

import edu.itu.cavabunga.core.entity.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFactoryImpl implements AuthenticationFactory {
    @Override
    public Authentication createAuthentication(){
        return new Authentication();
    }
}
