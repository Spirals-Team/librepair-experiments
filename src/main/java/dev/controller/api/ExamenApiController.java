package dev.controller.api;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.entites.Examen;
import dev.metiers.ClasseService;
import dev.metiers.ExamenService;
import dev.metiers.QuizzService;
import dev.metiers.StagiaireService;

@RestController
@RequestMapping("/api/examens")
public class ExamenApiController {

	private ExamenService examenService;
	private QuizzService quizzService;
	private ClasseService classeService;

	private StagiaireService stagiaireService;

	public ExamenApiController(ExamenService examenService, QuizzService quizzService, ClasseService classeService,
			StagiaireService stagiaireService) {
		super();
		this.examenService = examenService;
		this.quizzService = quizzService;
		this.classeService = classeService;
		this.stagiaireService = stagiaireService;

	}
	
    @RequestMapping("/lister")
    public ResponseEntity<List<Examen>> lister() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("MyResponseHeader", "MyValue");
        
        List<Examen> listExam  = examenService.lister();
        
        if(listExam != null){
        	return ResponseEntity.ok(listExam);
        }
        
        return null;
        //return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED);
    }
	
	
	
}
