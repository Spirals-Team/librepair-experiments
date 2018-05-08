package inciManager.uo.asw.mvc.controller;

import java.io.File;
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
import org.springframework.http.MediaType;
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

import inciManager.uo.asw.dbManagement.model.Categoria;
import inciManager.uo.asw.dbManagement.model.Incidencia;
import inciManager.uo.asw.dbManagement.model.Propiedad;
import inciManager.uo.asw.dbManagement.tipos.CategoriaTipos;
import inciManager.uo.asw.dbManagement.tipos.PropiedadTipos;
import inciManager.uo.asw.mvc.service.AgentService;
import inciManager.uo.asw.mvc.service.CategoriaService;
import inciManager.uo.asw.mvc.service.IncidenciasService;
import inciManager.uo.asw.mvc.service.PropiedadesService;
import inciManager.uo.asw.mvc.service.ValorLimiteService;

@Controller
public class IncidenciasController {

	@Autowired
	private ValorLimiteService valorLimiteService;

	@Autowired
	private IncidenciasService incidenciasService;

	@Autowired
	private AgentService agentService;

	@Autowired
	private PropiedadesService propiedadService;

	@Autowired
	private CategoriaService categoriaService;

	/**
	 * GET
	 * Responde a la invocacion de la lista de incidencias + paginacion
	 * si el usuario esta conectado nos devuelve la vista de la lista
	 * @param model de la vista
	 * @param pageable bootstrap
	 * @return vista incidencia/list
	 */
	@RequestMapping("/incidencia/list")
	public String getListado(Model model, Pageable pageable) {

		Page<Incidencia> incidencias = new PageImpl<Incidencia>(new LinkedList<Incidencia>());
		incidencias = incidenciasService.getIncidencias(pageable, agentService.getIdConnected());

		model.addAttribute("incidenciasList", incidencias.getContent());
		model.addAttribute("page", incidencias);
		model.addAttribute("idAgente", agentService.getIdConnected());

		model.addAttribute("valoresList", valorLimiteService.findAll());

		return comprobarConectado("incidencia/list");
	}

	/**
	 * GET
	 * Se invoca para la creacion de una incidencia
	 * comprueba que el usuario esta conectado 
	 * @param model bootstrap
	 * @return la vista incidencia/create
	 */
	@RequestMapping("/incidencia/create")
	public String create(Model model) {
		model.addAttribute("incidencia", new Incidencia());
		model.addAttribute("idAgente", agentService.getIdConnected());

		model.addAttribute("valoresList", valorLimiteService.findAll());

		return comprobarConectado("incidencia/create");
	}

	/**
	 * POST
	 * Recibe la informacion introducida por el usuario para la creacio nde una incidencia
	 * y si todo es correcto la crea
	 * comprueba que el usuario que este logeado
	 * @param incidencia que se quiere crear
	 * @param image de la incidencia
	 * @param category de la incidencia
	 * @param webRequest 
	 * @return la vista /incidencia/list
	 */
	@RequestMapping(value = "/incidencia/create", method = RequestMethod.POST)
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
	 * 
	 * @param incidencia
	 *            la incidencia
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

		if (drivinV != null) {
			Propiedad p = new Propiedad(PropiedadTipos.valueOf("VELOCIDAD_CIRCULACION"), Double.valueOf(drivinV));
			propiedadService.addPropiedad(p);
			propiedades.add(p);
		}
		if (windV != null) {
			Propiedad p = new Propiedad(PropiedadTipos.valueOf("VELOCIDAD_VIENTO"), Double.valueOf(windV));
			propiedadService.addPropiedad(p);
			propiedades.add(p);
		}
		if (preasure != null) {
			Propiedad p = new Propiedad(PropiedadTipos.valueOf("PRESION"), Double.valueOf(preasure));
			propiedadService.addPropiedad(p);
			propiedades.add(p);
		}
		if (humedad != null) {
			Propiedad p = new Propiedad(PropiedadTipos.valueOf("HUMEDAD"), Double.valueOf(humedad));
			propiedadService.addPropiedad(p);
			propiedades.add(p);
		}
		if (temperature != null) {
			Propiedad p = new Propiedad(PropiedadTipos.valueOf("TEMPERATURA"), Double.valueOf(temperature));
			propiedadService.addPropiedad(p);
			propiedades.add(p);
		}

		return propiedades;
	}

	/**
	 * Método para guardar una imagen asociada a una incidencia en el proyecto
	 * 
	 * @param incidencia
	 *            la incidencia
	 * @param image
	 *            la imagen
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

	/**
	 * SOLO SENSORES
	 * 
	 * POST
	 * Recibe el JSON con la informacion de la incidencia
	 * @param datosInci de la incidencia
	 * @return una respuesta afirmativa o negativa que solo un robot entendería
	 */
	@RequestMapping(value = "/inci", method = RequestMethod.POST)
	public ResponseEntity<String> update(@RequestBody Map<String, Object> datosInci) {
		return incidenciasService.cargarIncidencia(datosInci);
	}

	/**
	 * GET
	 * responde a la peticion para enseñar los detalles de una incidencia determinada
	 * ademas comprueba que el usuario este conectado
	 * @param model bootstrap
	 * @param id de la incidencia concreta
	 * @return la vista incidencia/details
	 */
	@RequestMapping("/incidencia/details/{id}")
	public String getDetail(Model model, @PathVariable ObjectId id) {
		Incidencia incidencia = incidenciasService.getOneById(id);
		model.addAttribute("inci", incidencia);
		model.addAttribute("noValor", PropiedadTipos.VALOR_NO_ASIGNADO);
		model.addAttribute("idAgente", agentService.getIdConnected());

		model.addAttribute("valoresList", valorLimiteService.findAll());

		return comprobarConectado("incidencia/details");
	}
	
	/**
	 * Comrpueba que el usuario conectado se haya autenticado
	 * @param destino view a la que se desea acceder
	 * @return view destino si el usuario ha pasado por el login o
	 * view login si no ha pasado por ahi antes
	 */
	private String comprobarConectado(String destino) {
		if (agentService.getIdConnected() == null) {
			return "redirect:/login";
		} else
			return destino;
	}

	/**
	 * GET
	 * Enseña la imagen de una incidencia determinada
	 * @param imageURL (String) con el nombre de la imagen
	 * @return la imagen
	 */
	@RequestMapping(value = "/incidencia/{imageURL}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImage(@PathVariable String imageURL) {
		File fi = new File("src/main/resources/static/img/post/" + imageURL + ".jpg");
		byte[] image;
		try {
			image = Files.readAllBytes(fi.toPath());
			return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] error = new byte[] { 0 };
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(error);
	}

}
