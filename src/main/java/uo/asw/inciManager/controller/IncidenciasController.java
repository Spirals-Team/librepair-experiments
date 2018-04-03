package uo.asw.inciManager.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import uo.asw.dbManagement.model.Categoria;
import uo.asw.dbManagement.model.Incidencia;
import uo.asw.dbManagement.model.Propiedad;
import uo.asw.dbManagement.tipos.CategoriaTipos;
import uo.asw.dbManagement.tipos.PropiedadTipos;
import uo.asw.inciManager.service.AgentService;
import uo.asw.inciManager.service.CategoriaService;
import uo.asw.inciManager.service.IncidenciasService;
import uo.asw.inciManager.service.PropiedadesService;

@Controller
public class IncidenciasController {
	
	@Autowired
	private IncidenciasService incidenciasService;
	
	@Autowired
	private AgentService agentService;
	
	@Autowired
	private PropiedadesService propiedadService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@RequestMapping("/incidencia/list" )
	public String getListado(Model model, Pageable pageable){
		
		Page<Incidencia> incidencias = new PageImpl<Incidencia>(new LinkedList<Incidencia>());
		incidencias = incidenciasService.getIncidencias(pageable, agentService.getIdConnected());
		
		model.addAttribute("incidenciasList", incidencias.getContent() );
		model.addAttribute("page", incidencias);
		model.addAttribute("idAgente", agentService.getIdConnected());
		
		return comprobarConectado("incidencia/list");
	}
	
	@RequestMapping("/incidencia/create")
	public String create(Model model) {
		model.addAttribute("incidencia", new Incidencia());
		model.addAttribute("idAgente", agentService.getIdConnected());
		return comprobarConectado("incidencia/create");
	}
	
	@RequestMapping(value="/incidencia/create", method = RequestMethod.POST)
	public String createNewIncidence(Incidencia incidencia, @RequestParam("image") MultipartFile image,
			@RequestParam("category") String category, WebRequest webRequest) {
		
		Categoria categoria = new Categoria(CategoriaTipos.valueOf(category));
		categoriaService.addCategoria(categoria);
		
		incidencia.setPropiedades(obtainProperties(incidencia, webRequest));
		incidencia.setLocation(agentService.getLatitude(), agentService.getLongitude());
		saveImage(incidencia, image); 
				
		incidenciasService.createNewIncidencia(incidencia, categoria, agentService.getIdConnected());
		incidenciasService.enviarIncidenciaWeb(incidencia);
		
		return comprobarConectado("redirect:/incidencia/list");
	}

	/**
	 * Método que devuelve una coleccion de las propiedades introducidas
	 * @param incidencia la incidencia
	 * @param webRequest
	 * @return set con todas las propiedades introducidas.
	 */
	private Set<Propiedad> obtainProperties(Incidencia incidencia, WebRequest webRequest) {
		Set<Propiedad> propiedades = new HashSet<Propiedad>();
		
		String drivinV = webRequest.getParameter("drivinVelocity");
		String windV = webRequest.getParameter("windVelocity");
		String preasure = webRequest.getParameter("preasure");
		String humedad = webRequest.getParameter("humedad");
		String temperature = webRequest.getParameter("temperature");
		
		if (drivinV!=null) {
			Propiedad p = new Propiedad(PropiedadTipos.valueOf("VELOCIDAD_CIRCULACION"), Double.valueOf(drivinV));
			propiedadService.addPropiedad(p);
			propiedades.add(p);
		} if (windV!=null) {
			Propiedad p = new Propiedad(PropiedadTipos.valueOf("VELOCIDAD_VIENTO"),  Double.valueOf(windV));
			propiedadService.addPropiedad(p);
			propiedades.add(p);
		} if (preasure!=null) {
			Propiedad p = new Propiedad(PropiedadTipos.valueOf("PRESION"), Double.valueOf(preasure));
			propiedadService.addPropiedad(p);
			propiedades.add(p);
		} if (humedad!=null) {
			Propiedad p = new Propiedad(PropiedadTipos.valueOf("HUMEDAD"),Double.valueOf(humedad));
			propiedadService.addPropiedad(p);
			propiedades.add(p);
		} if (temperature!=null) {
			Propiedad p = new Propiedad(PropiedadTipos.valueOf("TEMPERATURA"), Double.valueOf(temperature));
			propiedadService.addPropiedad(p);
			propiedades.add(p);
		}
		
		return propiedades;
	}
	
	/**
	 * Método para guardar una imagen asociada a una incidencia en el proyecto
	 * @param incidencia la incidencia
	 * @param image la imagen
	 */
	private void saveImage(Incidencia incidencia, MultipartFile image) {
		
		try {
			String fileName = image.getOriginalFilename();
			InputStream is = image.getInputStream();
			Files.copy(is, Paths.get("src/main/resources/static/img/post/" + fileName),
					StandardCopyOption.REPLACE_EXISTING);
			incidencia.setImageURL("/img/post/" + fileName); 
		} catch (IOException e) { 
		}
	}
	
	@RequestMapping(value = "/inci", method = RequestMethod.POST)
	public ResponseEntity<String> update(@RequestBody Map<String, Object> datosInci) {
	   return incidenciasService.cargarIncidencia(datosInci);
	}

	@RequestMapping("/incidencia/details/{id}")
	public String getDetail(Model model, @PathVariable ObjectId id) {
		Incidencia incidencia = incidenciasService.getOneById(id);
		model.addAttribute("inci", incidencia);
		model.addAttribute("noValor",PropiedadTipos.VALOR_NO_ASIGNADO);
		model.addAttribute("idAgente", agentService.getIdConnected());
		return comprobarConectado("incidencia/details");
	}
	
	private String comprobarConectado(String destino) {
		if(agentService.getIdConnected() == null) {
			return "redirect:/login";
		}else return destino;
	}
}
