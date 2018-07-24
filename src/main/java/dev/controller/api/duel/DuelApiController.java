package dev.controller.api.duel;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.controller.api.viewModels.duel.DuelCreateVm;
import dev.controller.api.viewModels.duel.DuelReturnVm;
import dev.controller.api.viewModels.duel.DuelVm;
import dev.entites.Duel;
import dev.entites.Quizz;
import dev.entites.Stagiaire;
import dev.metiers.DuelService;
import dev.metiers.QuizzService;
import dev.metiers.StagiaireService;
import dev.metiers.api.duel.DuelVmService;

@RestController
@RequestMapping("/api/duels")
public class DuelApiController {

	private DuelService duelService;
	private DuelVmService duelVmService;

	private StagiaireService stagiaireService;
	private QuizzService quizzService;

	/**
	 * Constructeur
	 * 
	 * @param duelService
	 * @param duelVmService
	 * @param stagiaireService
	 * @param quizzService
	 */
	public DuelApiController(DuelService duelService, DuelVmService duelVm, StagiaireService stagiaireService,
			QuizzService quizzService) {
		super();
		this.duelService = duelService;
		this.duelVmService = duelVm;
		this.stagiaireService = stagiaireService;
		this.quizzService = quizzService;
	}

	@GetMapping
	public ResponseEntity<List<DuelVm>> lister() {
		return ResponseEntity.ok(duelVmService.listAllDuel());
	}

	@PostMapping
	public ResponseEntity<?> ajouter(@RequestBody DuelCreateVm duelCreateVm) throws Exception {
		Duel duel = new Duel();
		Stagiaire stagiaireA = new Stagiaire();
		Stagiaire stagiaireB = new Stagiaire();
		Quizz quizz = new Quizz();

		if (stagiaireService.findStagiaireById(duelCreateVm.getStagiaireAId()) == null
				|| stagiaireService.findStagiaireById(duelCreateVm.getStagiaireBId()) == null
				|| quizzService.findQuizzById(duelCreateVm.getQuizzId()) == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Paramètres incohérents");
		else {

			stagiaireA.setId(duelCreateVm.getStagiaireAId());
			stagiaireB.setId(duelCreateVm.getStagiaireBId());
			quizz.setId(duelCreateVm.getQuizzId());

			duel.setStagiaireA(stagiaireA);
			duel.setStagiaireB(stagiaireB);
			duel.setQuizz(quizz);

			Duel duelSauve = duelService.creer(duel);

			return ResponseEntity.status(HttpStatus.OK).body(new DuelReturnVm(duelSauve));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<DuelVm> afficherDuel(@PathVariable Long id) throws Exception {
		return ResponseEntity.ok(duelVmService.findById(id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> supprimer(@PathVariable Long id) {

		if (duelService.exist(id)) {
			duelService.deleteById(id);
			return ResponseEntity.status(HttpStatus.OK).body("Duel correctement supprimé");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ce duel n'existe pas");
		}

	}

}
