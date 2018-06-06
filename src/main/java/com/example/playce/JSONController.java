package com.example.playce;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.RestController;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

@RestController
public class JSONController {

    @RequestMapping("/recreation")
    @ResponseBody
    public String recreation() throws IOException {
        return new String(Files.readAllBytes(Paths.get("/app/src/main/resources/ValidatedQuestionnaires/recreation.json")));
    }

    @RequestMapping("/restaurant")
    @ResponseBody
    public String restaurant() throws IOException {
        return new String(Files.readAllBytes(Paths.get("/app/src/main/resources/ValidatedQuestionnaires/restaurant.json")));
    }

    @RequestMapping("/shopping")
    @ResponseBody
    public String shopping() throws IOException {
        return new String(Files.readAllBytes(Paths.get("/app/src/main/resources/ValidatedQuestionnaires/shopping.json")));
    }
}


