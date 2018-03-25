package main;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import kafka.sender.Sender;
import model.Incidence;
import model.Operator;
import repository.IncidenceRepository;
import repository.OperatorRepository;
import utils.IncidenceUtils;

@SpringBootApplication
@ComponentScan({"controller", "kafka"})
@EnableMongoRepositories(basePackageClasses = IncidenceRepository.class)
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }
    
    @Autowired
    private Sender sender;
    
    @Autowired
    private IncidenceRepository inciRepository;
    
    @Autowired
    private OperatorRepository operatorRepository;

    @Override
    public void run(String... strings) throws Exception {
//    	if(operatorRepository.count()==0)
//    		addMockOperators();
//    	inciRepository.deleteAll();
//        int i = 0;
//        while (true) {
//        	Incidence inci = IncidenceUtils.randomInci(i);
//        	sender.send(inci);
//        	inciRepository.save(inci);
//        	if(inciRepository.count()>i)
//        		System.err.println("Incidence saved");
//        	TimeUnit.SECONDS.sleep(5);
//        	i++;
//        }

    }

	private void addMockOperators() {
		Operator o1 = new Operator("operator1", "asd");
		Operator o2 = new Operator("operator2", "asd");
		Operator o3 = new Operator("operator3", "asd");
		Operator o4 = new Operator("operator4", "asd");
		operatorRepository.insert(o1);
		operatorRepository.insert(o2);
		operatorRepository.insert(o3);
		operatorRepository.insert(o4);
		
	}
}