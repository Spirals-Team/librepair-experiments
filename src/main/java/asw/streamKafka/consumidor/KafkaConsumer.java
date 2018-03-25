package asw.streamKafka.consumidor;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import asw.controllers.DashboardAdminController;
import asw.entities.Agent;
import asw.entities.Campo;
import asw.entities.CamposCriticos;
import asw.entities.Etiqueta;
import asw.entities.Incidence;
import asw.entities.Location;
import asw.entities.Operator;
import asw.entities.Status;
import asw.entities.TipoCampos;
import asw.services.AgentService;
import asw.services.CamposCriticosService;
import asw.services.IncidenceService;
import asw.services.OperadorService;
import asw.streamKafka.Topics;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


@Controller
public class KafkaConsumer {
	
	@Autowired
	AgentService agentService;
	
	@Autowired
	IncidenceService inciService;
	
	@Autowired
	CamposCriticosService ccService;
	
	@Autowired
	DashboardAdminController dsController;
	
	@Autowired
	OperadorService opService;

	private static final Logger logger = Logger.getLogger(KafkaConsumer.class);
	

    @KafkaListener(topics = "newIncidence")
    public void listen(String data) {
    	 if(observer != null)
             observer.onNext(data);
    	System.out.println(data);
        
       	Incidence incidencia = parseToIncidence( data );
       	inciService.addIncidence( incidencia );
      /*
        SseEventBuilder evento = SseEmitter.event().name((Topics.NEW_INCIDENCE)).data( incidencia );
        this.dsController.sendData(evento);
       */
    }
    
    public Incidence parseToIncidence(String data) {
    	//NombreUsuario@nombreIncidencia@descripcion@localizacion@etiquetas_#1
		//@listaCampos_#3@estado@entidadAsignada@comentarioOperario@caducidad
		
		String[] camposSeparados=separaCampos(data);
		Incidence incidence=new Incidence();
		inciService.addIncidence( incidence ); // persistimos la incidencia en la bbdd
		//agent
		Agent agente = agentService.findByName(camposSeparados[0]);
		incidence.setAgent( agente );
		//nombreIncidencia
		incidence.setNombre(camposSeparados[1]);
		//descripcion
		incidence.setDescripcion(camposSeparados[2]);
		//localizacion
		Location location = location(camposSeparados[3]);
		incidence.setLocalizacion( location );
		//etiquetas
		Set<Etiqueta> etiquetas = etiquetas(camposSeparados[4]);
		incidence.setEtiquetas( etiquetas );
		//lista de campos
		Set<Campo> campos = listaCampos(camposSeparados[5]);
		incidence.setCampos( campos );
		//estado
		incidence.setEstado(Status.ABIERTO);
		//comentario operario
		Operator operario = asignarOperarioRandom();
		incidence.setOperadorAsignado( operario );
		//caducidad falta el parseo a date
		incidence.setCaducidad(parseFecha(camposSeparados[6]));
		
		actualizar(incidence, agente, location, etiquetas, campos, operario);
		
		return incidence;
	}
    
    
    private void actualizar(Incidence i, Agent ag, Location loc, Set<Etiqueta> ets, Set<Campo> campos, Operator op) {
    	// Actualización del agente
    	ag.addIncidencia( i );
    	agentService.actualizarAgente(ag);
    	
    	// Actualización de la entidad localización
    	loc.setIncidence( i );
    	inciService.addLocation( loc );
    	
    	// Actualización etiquetas
    	for(Etiqueta e : ets)
    		e.setIncidencia( i );
    	inciService.addEtiqueta(ets);
    	
    	// Actualización campos
    	for(Campo c : campos)
    		c.setincidencia( i );
    	inciService.addCampos(campos);
    	
    	// Actualización operador
    	op.addIncidencia( i );
    	opService.actualizarOperario(op);
    }
    
	    
    private String[] separaCampos(String data) {
    	String[] aux = data.split("@");
    	return aux;
    }
    

    private Set<Etiqueta> etiquetas(String etiquetas){
    	HashSet<Etiqueta> set=new HashSet<Etiqueta>();
    	String[] aux = etiquetas.split("\\$");
    	for(String s:aux) {
    		Etiqueta etiqueta=new Etiqueta();
    		etiqueta.setValor(s);
    		set.add(etiqueta);
    	}
    	return set;
    }
    
    
    
    private Set<Campo> listaCampos(String campos){
    	HashSet<Campo> set=new HashSet<Campo>();
    	String[] aux = campos.split("\\$");
    	for(String s: aux) {
    		set.add(claveValor(s));
    	}
    	return set;
    }

	private Campo claveValor(String s) {
		Campo campo=new Campo();
		String[] aux = s.split(":");
		CamposCriticos cCritico = ccService.findByClave(aux[0]);
		//Esto habria que hacerlo bien pero hace falta 
		//saber primero como vienen exactamente el formato del valor de los campos
		if((cCritico != null) && cCritico.getValor().equals(aux[1])){
			campo.setClave(aux[0]);
			campo.setValor(aux[1]);
			campo.setTipo(TipoCampos.CRITICO);
		}
		else{
			campo.setClave(aux[0]);
			campo.setValor(aux[1]);
			campo.setTipo(TipoCampos.NO_CRITICO);
		}
		return campo;
		
	}
    
	public static Date parseFecha(String fecha)
    {
	long fechaMilis = Long.parseLong(fecha);
        Date fechaDate = new Date(fechaMilis);
        return fechaDate;
    }
	
	private Location location(String s) {
		Location loc=new Location();
		String[] aux = s.split("\\$");
		System.out.println(aux[0]);
		System.out.println(aux[1]);
		loc.setLatitud(Double.parseDouble(aux[0]));
		loc.setLongitud(Double.parseDouble(aux[1]));
		return loc;
	}
    
	private Operator asignarOperarioRandom() {
		return opService.obtainOperatorForIncidence();
	}
	
    public Observable<String> getObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                observer = observableEmitter;
            }
        });
    }
    
    private ObservableEmitter<String> observer;
}
