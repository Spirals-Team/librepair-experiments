package asw.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import asw.entities.Campo;
import asw.entities.Etiqueta;
import asw.entities.Incidence;
import asw.entities.Location;
import asw.entities.Operator;
import asw.repository.CamposRepository;
import asw.repository.EtiquetaRepository;
import asw.repository.IncidenceRepository;
import asw.repository.LocationRepository;

@Service
public class IncidenceService {
	
	@Autowired
	public IncidenceRepository inciRepository;
	
	@Autowired
	public CamposRepository camposRepository;
	
	@Autowired
	LocationRepository locRepo;
	
	@Autowired
	private EtiquetaRepository etRepository;
	
	
	public void addIncidence(Incidence incidence)
	{
		inciRepository.save( incidence );
	}
	
	public Incidence getIncidence(Long identificador) {
		return inciRepository.findOne( identificador );
	}
	
	public List<Incidence> getIncidences() {
		List<Incidence> incidencias = new ArrayList<Incidence>();
		inciRepository.findAll().forEach(incidencias::add);
		
		return incidencias;
	}
	
	public void addEtiqueta(Set<Etiqueta> etiquetas) {
		for(Etiqueta e : etiquetas)
			etRepository.save( e );
	}
	
	public void addCampos(Set<Campo> campos)
	{
		for (Campo c : campos)
		{
			camposRepository.save( c );
		}
	}
	
	public void addCamposAIncidencia(Incidence i, Set<Campo> campos)
	{
		for(Campo c : campos)
		{
			i.addCampo( c );
			c.setincidencia( i );
		}
	}
	
	public void addEtiquetasAIncidencia(Incidence i, Set<Etiqueta> etiquetas) {
		for(Etiqueta e : etiquetas)
		{
			i.addEtiqueta( e );
			e.setIncidencia( i );
		}
	}
	

	public Page<Incidence> getIncidencessForOperator(Pageable pageable, Operator user) {
		Page<Incidence> inci = new PageImpl<Incidence>(new LinkedList<Incidence>());
		inci = inciRepository.findAllByUser(pageable, user);
		return inci;
	} 
	
	
	public List<Incidence> getIncidencessForOperatorForTest( Operator user) {
		List<Incidence> inci = inciRepository.findAllByUser( user );
		return inci;
	} 

	public void addLocation(Location loc)
	{
		locRepo.save( loc );
	}

}
