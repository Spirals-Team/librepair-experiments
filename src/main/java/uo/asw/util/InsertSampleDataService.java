package uo.asw.util;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uo.asw.dbManagement.model.Agent;
import uo.asw.dbManagement.model.Category;
import uo.asw.dbManagement.model.Incidence;
import uo.asw.dbManagement.model.Operator;
import uo.asw.dbManagement.model.Property;
import uo.asw.dbManagement.repositories.AgentsRepository;
import uo.asw.dbManagement.repositories.CategoriesRepository;
import uo.asw.dbManagement.repositories.IncidencesRepository;
import uo.asw.dbManagement.services.OperatorsService;

@Service
public class InsertSampleDataService {
	
	@Autowired
	private AgentsRepository agentsRepository;

	@Autowired
	private OperatorsService operatorsService;
	
	@Autowired
	private IncidencesRepository incidencesRepository;
	
	@Autowired
	private CategoriesRepository categoriesRepository;

	@SuppressWarnings("serial")
	@PostConstruct
	public void init() {
		
		
		// Creamos agentes
		Agent agent1 = new Agent("31668313G", "1234", "Person");
		agent1.setName("Juan");
		agent1.setEmail("email@email.com");
		
		agentsRepository.save(agent1);
	
		//Creamos categorias
		Category categoriaTiempo=new Category("tiempo");
		Category categoriaNormal=new Category("normal");
		
		categoriesRepository.save(categoriaTiempo);
		categoriesRepository.save(categoriaNormal);
		//
		
		// Creamos operarios e incidencias
		Operator operator1 = new Operator("99999999A", "NombreOperador1");
		operator1.setPassword("123456");
		
		Set<Incidence> operator1Incidences = new HashSet<Incidence>() {
			{
				Incidence i1 = new Incidence(UuidGenerator.getUuid());
				Incidence i2 = new Incidence(UuidGenerator.getUuid());
				
			 	Set<Property> i1Properties = new HashSet<Property>();
			    	i1Properties.add(new Property("temperatura", "10"));
			    	i1Properties.add(new Property("aire", "mucho"));

			    	Set<Property> i2Properties = new HashSet<Property>();
			    	i2Properties.add(new Property("temperatura", "20"));
			    	i2Properties.add(new Property("aire", "poco"));
			    	
		
			    Set<String> i1Tags = new HashSet<>(); i1Tags.add(categoriaTiempo.getName()); 
			    Set<String> i2Tags = new HashSet<>(); i2Tags.add(categoriaNormal.getName());
			    	
				i1.setAgent(agent1).setOperator(operator1).setName("NombreInc1").setDescription("DescripcionInc1").setProperties(i1Properties).setTags(i1Tags);
				i1.setStatus("Abierta");
				i2.setAgent(agent1).setOperator(operator1).setName("NombreInc2").setDescription("DescripcionInc2").setProperties(i2Properties).setTags(i2Tags);
				i2.setStatus("Abierta");
				
				add(i2);
				add(i1);
			}
		};
		
		operator1.setIncidences(operator1Incidences);
		
		
		operatorsService.addOperator(operator1);
		
		//Estaba comentado pero lo descomenté porque sino no funciona
		incidencesRepository.save(operator1Incidences);
		
		Operator opreator2 = new Operator("AAAAAAA2", "Juan");
		opreator2.setPassword("123456");
		Operator opreator3 = new Operator("AAAAAAA3", "Francisco");
		opreator3.setPassword("123456");
		Operator opreator4 = new Operator("AAAAAAA4", "Rodrigo");
		opreator4.setPassword("123456");
		Operator opreator5 = new Operator("AAAAAAA5", "Pepe");
		opreator5.setPassword("123456");
		Operator opreator6 = new Operator("AAAAAAA6", "Alberto");
		opreator6.setPassword("123456");

		operatorsService.addOperator(opreator2);
		operatorsService.addOperator(opreator3);
		operatorsService.addOperator(opreator4);
		operatorsService.addOperator(opreator5);
		operatorsService.addOperator(opreator6);
	
		
		
		
		
		
	
	}
	
	
}