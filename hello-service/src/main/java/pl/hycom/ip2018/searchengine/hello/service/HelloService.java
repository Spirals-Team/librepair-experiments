package pl.hycom.ip2018.searchengine.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    @Autowired
    Environment env;

    public String getMessage() {
        return "Hello, World! from "+env.getProperty("server.port");
    }
}
