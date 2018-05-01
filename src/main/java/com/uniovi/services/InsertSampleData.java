package com.uniovi.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uniovi.model.Incidence;
import com.uniovi.model.Incidence.Estado;
import com.uniovi.model.User;
import com.uniovi.repositories.IncidencesRepository;
import com.uniovi.repositories.UsersRepository;

@Service
public class InsertSampleData {

    private List<Incidence> incidences;

    @Autowired
    IncidencesRepository incidenceRepository;

    @Autowired
    UserService userService;

    @PostConstruct
    public void init() {
	incidences = new ArrayList<Incidence>();
	List<User> usuarios = new ArrayList<User>();

	incidences.add(new Incidence("descripcion", "43.3579649;-5.8733862", new HashSet<String>(),
		new HashMap<String, String>(), Estado.ABIERTA));
	incidences.add(new Incidence("descripcion", "43.5322065;-5.6611416000000645", new HashSet<String>(),
		new HashMap<String, String>(), Estado.ABIERTA));
	incidences.add(new Incidence("descripcion", "43.5579767;-5.92448289999993", new HashSet<String>(),
		new HashMap<String, String>(), Estado.ABIERTA));
	incidences.add(new Incidence("descripcion", "43.54212589999999;-6.536139700000035", new HashSet<String>(),
		new HashMap<String, String>(), Estado.ABIERTA));
	incidences.add(new Incidence("descripcion", "43.5322065;-5.6611416000000645", new HashSet<String>(),
		new HashMap<String, String>(), Estado.ABIERTA));
	incidences.add(new Incidence("descripcion", "43.3579649;-5.8733862", new HashSet<String>(),
		new HashMap<String, String>(), Estado.ABIERTA));
	incidences.add(new Incidence("descripcion", "43.5579767;-5.92448289999993", new HashSet<String>(),
		new HashMap<String, String>(), Estado.ABIERTA));
	incidences.add(new Incidence("descripcion", "43.5322065;-5.6611416000000645", new HashSet<String>(),
		new HashMap<String, String>(), Estado.ABIERTA));
	incidences.add(new Incidence("descripcion", "43.3579649;-5.8733862", new HashSet<String>(),
		new HashMap<String, String>(), Estado.ABIERTA));
	incidences.add(new Incidence("descripcion", "43.5579767;-5.92448289999993", new HashSet<String>(),
		new HashMap<String, String>(), Estado.ABIERTA));

	userService.addUser(new User("Admin", "", "admin@gmail.com", "admin", "Operador", "1234"));
	userService.addUser(new User("Susana", "", "susana@gmail.com", "susana", "Operador", "1234"));

	incidenceRepository.save(incidences);

    }

    public List<Incidence> getIncidences() {
	return incidences;
    }

}
