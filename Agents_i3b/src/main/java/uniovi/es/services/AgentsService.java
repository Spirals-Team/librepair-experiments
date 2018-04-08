package uniovi.es.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uniovi.es.entities.Agent;
import uniovi.es.repository.AgentsRepository;

@Service
public class AgentsService {
	
	@Autowired
    private
    AgentsRepository repository;
	
	public Agent getAgent(String id, String password, String kindCode) {
		Agent user = repository.findOne(id);
        if (user != null && user.getPassword().equals(password) && user.getKindCode().equals(kindCode))
            return user;
        else
            return null;
	}
	
	public void insertUser(Agent user) {
		repository.insert(user);
		
	}

	public void deleteUser(Agent user) {
		repository.delete(user);
		
	}

}
