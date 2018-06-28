package inciManager.incidenceController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import inciManager.entities.Agente;
import inciManager.entities.Incidencia;
import inciManager.incidenceService.IncidenceServiceFacade;

@Controller
public class IncidenceControllerFacadeClass implements IncidenceControllerFacade {

	@Autowired
	private IncidenceServiceFacade incidenceService;

	@RequestMapping(value = "/addincidence", method = RequestMethod.POST)
	@Override
	public String addIncidence(@ModelAttribute Incidencia incidence, @RequestParam("image") MultipartFile image) {

		if (comprobarAgente(incidence.getAgenteAux())) {

			if (!image.isEmpty())
				incidence.setUrlMasInfo("si");

			Incidencia saved = incidenceService.processIncidence(incidence);

			if (!image.isEmpty())
				saveImage(image, saved);

			return "exito";
		}

		return "error";
	}

	@RequestMapping("/checkincidence")
	@Override
	public String checkIncidence(@ModelAttribute Agente agente, Model modelo) {
		if (comprobarAgente(agente)) {
			List<Incidencia> lista = incidenceService.getIncidenceInfo(agente);

			modelo.addAttribute("incidencias", lista);
			return "incidence/list";
		}

		return "error";
	}

	private boolean comprobarAgente(Agente agenteAux) {
		return RestUtil.comprobarDatos(agenteAux);
	}

	private void saveImage(MultipartFile image, Incidencia post) {
		try {
			InputStream is = image.getInputStream();
			Files.copy(is, Paths.get("src/main/resources/static/fotossubidas/" + post.getId() + ".jpg"),
					StandardCopyOption.REPLACE_EXISTING);
			is = image.getInputStream();
			Files.copy(is, Paths.get("target/classes/static/fotossubidas/" + post.getId() + ".jpg"),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
