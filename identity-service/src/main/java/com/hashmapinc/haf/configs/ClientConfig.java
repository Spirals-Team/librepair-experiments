package com.hashmapinc.haf.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;

@Component
@ConfigurationProperties("oauth2")
public class ClientConfig {

    private HashMap<String, ClientDetails> clients = new HashMap<>();

    public HashMap<String, ClientDetails> getClients() {
        return clients;
    }

    public void setClients(HashMap<String, ClientDetails> clients) {
        this.clients = clients;
    }

    public static class ClientDetails{
        private List<String> grantTypes;
        private String clientSecret;
        private List<String> scopes;

        public List<String> getGrantTypes() {
            return grantTypes;
        }

        public void setGrantTypes(List<String> grantTypes) {
            this.grantTypes = grantTypes;
        }

        public List<String> getScopes() {
            return scopes;
        }

        public void setScopes(List<String> scopes) {
            this.scopes = scopes;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }
    }
}
