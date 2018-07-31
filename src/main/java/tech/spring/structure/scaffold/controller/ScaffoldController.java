package tech.spring.structure.scaffold.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tech.spring.structure.scaffold.exception.ScaffoldNotFoundException;
import tech.spring.structure.scaffold.model.Scaffold;
import tech.spring.structure.scaffold.service.ScaffoldService;

@RestController
public class ScaffoldController {

    @Autowired
    private ScaffoldService scaffoldService;

    @GetMapping("/scaffold")
    public Scaffold scaffold(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "model", required = true) String model) throws ScaffoldNotFoundException {
        Optional<Scaffold> modelValidations = scaffoldService.get(request, response, model);
        if (modelValidations.isPresent()) {
            return modelValidations.get();
        }
        throw new ScaffoldNotFoundException("Scaffold for " + model + " not found!");
    }

    @GetMapping("/scaffolding")
    public List<Scaffold> scaffolding(HttpServletRequest request, HttpServletResponse response) {
        return scaffoldService.get(request, response);
    }

}
