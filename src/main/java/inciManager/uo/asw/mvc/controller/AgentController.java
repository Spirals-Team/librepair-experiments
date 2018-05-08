package inciManager.uo.asw.mvc.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import inciManager.uo.asw.mvc.service.AgentService;
import inciManager.uo.asw.mvc.service.ValorLimiteService;

@Controller
public class AgentController {
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	private AgentService agentsService;
	
	@Autowired
	private ValorLimiteService valorLimiteService;
	
	private String username;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGet(Model model) {
		model.addAttribute("valoresList", valorLimiteService.findAll());
		return "login";
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		model.addAttribute("idAgente", agentsService.getIdConnected());
		model.addAttribute("nombreUsuario", username);
		model.addAttribute("mensajesList", agentsService.getMensajesChatBot());
		model.addAttribute("valoresList", valorLimiteService.findAll());
		return comprobarConectado("home");
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	public String loginPost(Model model, @RequestParam String username, @RequestParam String password,
			@RequestParam String kind) {
		Map<String,Object> infoAgente = agentsService.communicationAgents(username, password, kind);
		if(infoAgente != null) {
			agentsService.setIdConnected((String)infoAgente.get("id"));
			this.username = username;
			return "redirect:/home";
		} else {
			agentsService.setIdConnected(null);
		}
		model.addAttribute("valoresList", valorLimiteService.findAll());
		return "/login";
	}
	
	@RequestMapping("/signout")
	public String logout() {
		agentsService.setIdConnected(null);
		agentsService.borrarDatosAgente();
		return "redirect:/login";
	}
	
	/**
	 * Comrpueba que el usuario conectado se haya autenticado
	 * @param destino view a la que se desea acceder
	 * @return view destino si el usuario ha pasado por el login o
	 * view login si no ha pasado por ahi antes
	 */
	private String comprobarConectado(String destino) {
		if(agentsService.getIdConnected() == null ||
				((String)httpSession.getAttribute("sesion"))==null) {
			return "redirect:/login";
		}else return destino;
	}

	/**
	 * GET
	 * Actualiza la vista del chat bot pero solo un fragmento determinado
	 * @param model bootstrap
	 * @return la vista user/chatbot con el fragmento actualizado
	 */
	@RequestMapping("/user/chatbot/update") 
	public String updateList(Model model){
		model.addAttribute("mensajesList", agentsService.getMensajesChatBot());
		model.addAttribute("valoresList", valorLimiteService.findAll());
		return "user/chatbot :: chatList";
	}
	
	/**
	 * POST
	 * Se utilzia para enviar un nuevo mensaje en el chatbot por parte del usuario
	 * añadiendo el mensaje a la lista de nuevos mensajes del chat
	 * @param model bootstrap
	 * @param contenido del mensaje que envia el usuario
	 * @return la vista /home
	 */
	@RequestMapping(value="/user/chatbot/send", method=RequestMethod.POST) 
	public String sendResquest(Model model, @RequestParam String contenido){
		agentsService.addNewMensajeChat(contenido); /* BOTON ENVIAR */
		model.addAttribute("valoresList", valorLimiteService.findAll());
		return "redirect:/home";
	}
	
	
}
