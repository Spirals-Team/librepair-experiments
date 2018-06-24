package pl.sda.jira.documentation.rest;

import org.springframework.web.bind.annotation.*;
import pl.sda.jira.documentation.domain.DocumentationService;

@RestController
@RequestMapping("/documentation")
public class DocumentationController {

    private final DocumentationService documentationService;

    public DocumentationController(DocumentationService documentationService) {
        this.documentationService = documentationService;
    }

    @RequestMapping("/hello")
    public String printHello(@RequestParam(defaultValue = "No name" , name = "name") String name){
        return "Hello " + name;
    }
     @RequestMapping(path = "/{id}" , method = RequestMethod.DELETE)
    public String remove (@PathVariable String id){
        return "Removed " + id ;
     }
}
