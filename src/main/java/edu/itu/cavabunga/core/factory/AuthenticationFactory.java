package edu.itu.cavabunga.core.factory;

import edu.itu.cavabunga.core.entity.Authentication;
import edu.itu.cavabunga.core.entity.authentication.AuthenticationType;

public interface AuthenticationFactory {
    Authentication createAuthentication(AuthenticationType authenticationType);
}
