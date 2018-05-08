package inciManager.uo.asw.mvc.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import inciManager.uo.asw.mvc.service.AgentService;
import inciManager.uo.asw.mvc.service.ValorLimiteService;

@Controller
public class HomeController {
	
	@Autowired
	private HttpSession httpSession;

	@Autowired
	private ValorLimiteService valorLimiteService;
	
	@Autowired
	private AgentService agentService;
	
	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("idAgente", null);
		model.addAttribute("mensajesList", agentService.getMensajesChatBot());
		
		model.addAttribute("valoresList", valorLimiteService.findAll());
		
		return comprobarConectado("home");
	}
	
	/**
	 * Comrpueba que el usuario conectado se haya autenticado
	 * @param destino view a la que se desea acceder
	 * @return view destino si el usuario ha pasado por el login o
	 * view login si no ha pasado por ahi antes
	 */
	private String comprobarConectado(String destino) {
		if(agentService.getIdConnected() == null ||
				((String)httpSession.getAttribute("sesion"))==null) {
			return "redirect:/login";
		}else return destino;
	}
}
