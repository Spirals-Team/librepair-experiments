package com.isa.instaticketapi.config;

import java.util.*;

import com.isa.instaticketapi.config.ApplicationProperties.Http.Version;

public interface ApplicationDefaults {


    interface Async {

        int corePoolSize = 2;
        int maxPoolSize = 50;
        int queueCapacity = 10000;
    }

    interface Http {

        Version version = Version.V_1_1;

    }

    interface Mail {

        String from = "";
        String baseUrl = "";
    }

    interface Security {

        interface ClientAuthorization {

            String accessTokenUri = null;
            String tokenServiceId = null;
            String clientId = null;
            String clientSecret = null;
        }

        interface Authentication {

            interface Jwt {

                String secret =  "isa";
                long tokenValidityInSeconds = 1800; // 0.5 hour
                long tokenValidityInSecondsForRememberMe = 2592000; // 30 hours;
            }
        }

        interface RememberMe {

            String key = null;
        }
    }

    interface Swagger {

        String title = "Application API";
        String description = "API documentation";
        String version = "0.0.1";
        String termsOfServiceUrl = null;
        String contactName = null;
        String contactUrl = null;
        String contactEmail = null;
        String license = null;
        String licenseUrl = null;
        String defaultIncludePattern = "/api/.*";
        String host = null;
        String[] protocols = {};
    }

    interface Metrics {

        interface Jmx {

            boolean enabled = true;
        }

        interface Graphite {

            boolean enabled = false;
            String host = "localhost";
            int port = 2003;
            String prefix = "instaticketApplication";
        }

        interface Prometheus {

            boolean enabled = false;
            String endpoint = "/prometheusMetrics";
        }

        interface Logs {

            boolean enabled = false;
            long reportFrequency = 60;

        }
    }

    interface Logging {

        interface Logstash {

            boolean enabled = false;
            String host = "localhost";
            int port = 5000;
            int queueSize = 512;
        }

        interface SpectatorMetrics {

            boolean enabled = false;
        }
    }
}
