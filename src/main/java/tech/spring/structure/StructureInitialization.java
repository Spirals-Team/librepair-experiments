package tech.spring.structure;

import static tech.spring.structure.StructureConstants.PASSWORD_DURATION_IN_DAYS;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tech.spring.structure.auth.model.User;
import tech.spring.structure.auth.model.repo.UserRepo;

@Component
public class StructureInitialization implements CommandLineRunner {

    @Value("${structure.password.duration:180}")
    private int structurePasswordDuration;

    @Value("${structure.authorize.header:'X-Auth-Token'}")
    private String structureAuthorizeHeader;

    @Value("classpath:data/users.json")
    private Resource usersResource;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        initializePropertyConstants();
        initializeUsers();
    }

    private void initializePropertyConstants() {
        PASSWORD_DURATION_IN_DAYS = structurePasswordDuration;
    }

    private void initializeUsers() throws JsonParseException, JsonMappingException, IOException {
        // @formatter:off
        List<User> users = objectMapper.readValue(usersResource.getFile(), new TypeReference<List<User>>() {});
        // @formatter:on
        users.forEach(user -> {
            createUserIfNotExists(user);
        });
    }

    private void createUserIfNotExists(User user) {
        Optional<User> superAdmin = userRepo.findByUsername(user.getUsername());
        if (!superAdmin.isPresent()) {
            superAdmin = Optional.of(userRepo.create(user));
        }
    }

}