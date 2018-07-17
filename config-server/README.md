# Spring Cloud Config Server

**Good Pratices**

1. Should typically run on several instances behind a load balancer and auto healing configuration.
2. Client application can control policy of how to handle missing config server. Set the property
`spring.cloud.config.failFast=true` to say that client will not going to run without a configuration server.
3. Config Server settings override local setting, so provide local fallback settings.


**RUN**

`$ ./mvnw clean spring-boot:run`


**E.g.**

**Retrive DEFAULT profile properties:**<br>
`$ curl http://localhost:8001/config-client/default
`

```json
{  
   name:"config-client",
   profiles:[  
      "default"
   ],
   label:null,
   version:"24b0b5240cf0d900235aeb745476c46928ed1137",
   state:null,
   propertySources:[  
      {  
         name:"https://github.com/fpjunqueira/spring-cloud-netflix-playground/config-server/config/config-client.yml",
         source:{  
            message:"hello world"
         }
      }
   ]
}
```

**Retrive DEV profile properties:**<br>
`$ curl http://localhost:8001/config-client/dev
`

```json
{  
   name:"config-client",
   profiles:[  
      "dev"
   ],
   label:null,
   version:"24b0b5240cf0d900235aeb745476c46928ed1137",
   state:null,
   propertySources:[  
      {  
         name:"https://github.com/fpjunqueira/spring-cloud-netflix-playground/config-server/config/config-client-dev.yml",
         source:{  
            message:"hello from dev profile"
         }
      },
      {  
         name:"https://github.com/fpjunqueira/spring-cloud-netflix-playground/config-server/config/config-client.yml",
         source:{  
            message:"hello world"
         }
      }
   ]
}
```

**Retrive QA profile properties:**<br>
`$ curl http://localhost:8001/config-client/qa
`

```json
{  
   name:"config-client",
   profiles:[  
      "qa"
   ],
   label:null,
   version:"24b0b5240cf0d900235aeb745476c46928ed1137",
   state:null,
   propertySources:[  
      {  
         name:"https://github.com/fpjunqueira/spring-cloud-netflix-playground/config-server/config/config-client-qa.yml",
         source:{  
            message:"hello hello from qa profile"
         }
      },
      {  
         name:"https://github.com/fpjunqueira/spring-cloud-netflix-playground/config-server/config/config-client.yml",
         source:{  
            message:"hello world"
         }
      }
   ]
}
```

**Retrive PROD profile properties**:<br>
`$ curl http://localhost:8001/config-client/prod`

```json
{  
   name:"config-client",
   profiles:[  
      "prod"
   ],
   label:null,
   version:"24b0b5240cf0d900235aeb745476c46928ed1137",
   state:null,
   propertySources:[  
      {  
         name:"https://github.com/fpjunqueira/spring-cloud-netflix-playground/config-server/config/config-client-prod.yml",
         source:{  
            message:"hello from prod profile"
         }
      },
      {  
         name:"https://github.com/fpjunqueira/spring-cloud-netflix-playground/config-server/config/config-client.yml",
         source:{  
            message:"hello world"
         }
      }
   ]
}
```