package uo.asw.apacheKafka.consumer;

import javax.annotation.ManagedBean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import uo.asw.dbManagement.model.Incidence;
import uo.asw.inciDashboard.filter.RIncidenceP;
import uo.asw.inciDashboard.filter.ReceiveIncidence;
import uo.asw.util.exception.BusinessException;

@ManagedBean
public class MessageListener {

    private static final Logger logger = Logger.getLogger(MessageListener.class);

//    @Autowired
//	private ReceiveIncidence receiveIncidence;
    
    @Autowired
	private RIncidenceP rIncidenceP; 
    
    @KafkaListener(topics = "incidences")
    public void listen(String data) {
        logger.info("New message received: \"" + data + "\"");
        System.out.println(data);
        
        Incidence incidence;
		try {
			//jsonStringIncidence = "{" + jsonStringIncidence + "}";
			incidence = rIncidenceP.jsonStringToIncidence(data);
			System.out.println(incidence);
			//receiveIncidence.receiveIncidence(incidence);
		} catch (BusinessException e) {
			System.out.println("Error al parsear la incidencia de String a JSON");
			e.printStackTrace();
			return;
		}
        
        //receiveIncidence.receiveIncidence(data);
        //sendToFilter(data);
    }

//    private void sendToFilter(String data) {
//    		receiveIncidence.receiveIncidence(data);
//    }

}
