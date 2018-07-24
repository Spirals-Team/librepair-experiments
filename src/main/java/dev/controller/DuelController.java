package dev.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import dev.entites.Duel;
import dev.metiers.DuelService;
import dev.repositories.quizz.QuizzRepository;
import dev.repositories.stagiaire.StagiaireRepository;

/**
 * @author Mayeul
 *
 */
@RestController
@RequestMapping("/duels")
public class DuelController {

	private DuelService duelService;
	private StagiaireRepository stagiaireRepository;
	private QuizzRepository quizzRepository;

	/**
	 * Constructeur
	 * 
	 * @param duelService
	 * @param stagiaireRepository
	 * @param quizzRepository
	 */
	public DuelController(DuelService duelService, StagiaireRepository stagiaireRepository,
			QuizzRepository quizzRepository) {
		super();
		this.duelService = duelService;
		this.stagiaireRepository = stagiaireRepository;
		this.quizzRepository = quizzRepository;
	}

	/**
	 * Liste des duels
	 * 
	 * @return
	 */
	@GetMapping("/lister")
	public ModelAndView lister() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("listeDuels", duelService.lister());
		mav.setViewName("duels/listerDuels");
		return mav;
	}

	/**
	 * Affichage du formulaire d'ajout d'un duel
	 * 
	 * @return
	 */
	@GetMapping("/ajouter")
	public ModelAndView setupAjouterForm() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("duel", new Duel());
		mav.addObject("listeStagiaires", stagiaireRepository.findAll());
		mav.addObject("listeQuizzes", quizzRepository.findAll());
		mav.setViewName("duels/ajouterDuel");
		return mav;
	}

	/**
	 * Envoi du formulaire d'ajout d'un duel
	 * 
	 * @param duel
	 * @param result
	 * @param listeIds
	 * @return
	 */
	@PostMapping("/ajouter")
	public ModelAndView submitAjouterForm(@ModelAttribute("duel") @Valid Duel duel, BindingResult result,
			@RequestParam List<Long> listeIds) {
		ModelAndView mav = new ModelAndView();

		boolean isListeIdsOK = validateListeIds(listeIds);

		if (!isListeIdsOK || result.hasErrors()) {
			mav.addObject("duel", new Duel());
			mav.addObject("listeStagiaires", stagiaireRepository.findAll());
			mav.addObject("listeQuizzes", quizzRepository.findAll());
			mav.setViewName("duels/ajouterDuel");
			if (!isListeIdsOK)
				mav.addObject("erreurListeIds", "#");
		} else {
			try {
				duelService.creer(listeIds, duel.getQuizz());
				mav.setViewName("redirect:/duels/lister");
			} catch (Exception e) {
				System.out.println("allo");
				e.printStackTrace();
			}
		}
		return mav;
	}

	/**
	 * Affichage du formulaire de mise à jour d'un duel
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/editer")
	public ModelAndView setupEditerForm(@RequestParam("id") Long id) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("duel", duelService.findDuelById(id));
		mav.addObject("listeStagiaires", stagiaireRepository.findAll());
		mav.addObject("listeQuizzes", quizzRepository.findAll());
		mav.setViewName("duels/editerDuel");
		return mav;
	}

	/**
	 * Envoi du formulaire de mise à jour d'un duel
	 * 
	 * @param duel
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/editer")
	public ModelAndView submitEditerForm(@ModelAttribute("duel") Duel duel, @RequestParam("id") Long id)
			throws Exception {
		ModelAndView mav = new ModelAndView();

		if (duel.getStagiaireA().getId().equals(duel.getStagiaireB().getId())) {
			mav.addObject("duel", duelService.findDuelById(id));
			mav.addObject("listeStagiaires", stagiaireRepository.findAll());
			mav.addObject("listeQuizzes", quizzRepository.findAll());
			mav.addObject("erreurDoublons", "#");
			mav.setViewName("duels/editerDuel");
		} else {
			duel.setId(id);
			duel.setStagiaireA(stagiaireRepository.findById(duel.getStagiaireA().getId()).orElseThrow(Exception::new));
			duel.setStagiaireB(stagiaireRepository.findById(duel.getStagiaireB().getId()).orElseThrow(Exception::new));
			duel.setQuizz(quizzRepository.findById(duel.getQuizz().getId()).orElseThrow(Exception::new));
			duelService.editer(duel);
			mav.setViewName("redirect:/duels/lister");
		}

		return mav;
	}

	/**
	 * Envoi de la requête de suppression d'un duel
	 * 
	 * @param duel
	 * @param id
	 * @return
	 */
	@PostMapping("/supprimer")
	public ModelAndView supprimer(@RequestParam("id") Long id) {
		ModelAndView mav = new ModelAndView();
		Duel duel = duelService.findDuelById(id);
		duelService.supprimer(duel);
		mav.setViewName("redirect:/duels/lister");
		return mav;
	}

	/**
	 * Indique si une liste d'ids de stagiaires contient bien 2 et seulement 2
	 * stagiaires différents pour créer un duel cohérent
	 * 
	 * @param listeIds
	 * @return
	 */
	private boolean validateListeIds(List<Long> listeIds) {
		if (listeIds.size() != 2)
			return false;
		else if (listeIds.get(0).equals(listeIds.get(1)))
			return false;
		return true;
	}
}
