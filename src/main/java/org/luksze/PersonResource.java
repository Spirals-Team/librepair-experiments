package org.luksze;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("person")
public class PersonResource {

    private final PersonRepository personRepository = new PersonRepository();

    @GetMapping
    public List<Person> get() {
        return personRepository.getAll();
    }

    @PostMapping
    public void post(@RequestBody Person person) {
        personRepository.add(person);
    }

}
