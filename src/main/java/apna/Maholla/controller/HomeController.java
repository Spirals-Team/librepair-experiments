package apna.Maholla.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String getAllNotes() {
        return "Welcome to rest api console";
    }
}
