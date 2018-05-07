package uo.asw.incidashboard.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import uo.asw.dbManagement.model.Usuario;
import uo.asw.dbManagement.tipos.PerfilTipos;
import uo.asw.incidashboard.services.UsuarioService;

@Controller
public class HomeController {
	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping("/")
	public String index() {
		return "login";
	}

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String setEdit(Model model) {
		
		String mail = SecurityContextHolder.getContext().getAuthentication().getName();
		Usuario user = usuarioService.getUsuarioByMail(mail);
		if (user == null)
			return "redirect:/login";
		if (user.getPerfil().equals(PerfilTipos.OPERARIO))
			return "redirect:/users/operario";
		else if (user.getPerfil().equals(PerfilTipos.ADMIN_CM))
			return "redirect:/users/admin";
		else if (user.getPerfil().equals(PerfilTipos.ANALISIS_DATOS))
			return "redirect:/users/analisis";
		return "redirect:/login";

	}

}
