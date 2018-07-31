package tech.spring.structure.scaffold;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import tech.spring.structure.scaffold.service.ScaffoldService;

@Component
public class ScaffoldInitialization implements CommandLineRunner {

    @Autowired
    private ScaffoldService scaffoldService;

    @Override
    public void run(String... args) throws Exception {
        scaffoldService.scanEntities();
    }

}
