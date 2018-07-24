package dev.controller.api.sondage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.controller.api.sondage.viewModels.ClasseListerSondageVM;
import dev.controller.api.sondage.viewModels.ListerSondageVM;
import dev.controller.api.sondage.viewModels.OptionListerSondageVM;
import dev.controller.api.sondage.viewModels.SondageByIdVM;
import dev.entites.OptionSondage;
import dev.entites.Sondage;
import dev.metiers.SondageService;

@Service
public class SondageBuilder {

	private SondageService sondageService;

	public SondageBuilder(SondageService sondageService) {
		super();
		this.sondageService = sondageService;
	}

	@Transactional
	public List<ListerSondageVM> creerJsonLister() {
		List<ListerSondageVM> listeSondage = new ArrayList<>();
		List<Sondage> sondages = sondageService.lister();
		for (Sondage s : sondages) {
			ListerSondageVM ls = new ListerSondageVM();
			ClasseListerSondageVM classe = new ClasseListerSondageVM();
			classe.setId(s.getClasse().getId());
			classe.setNom(s.getClasse().getNom());
			ls.setId(s.getId());
			ls.setTitre(s.getTitre());
			ls.setClasse(classe);
			ls.setNb_options(s.getOptions().size());
			listeSondage.add(ls);
		}
		return listeSondage;
	}

	@Transactional
	public SondageByIdVM creerJsonListerById(Long id) throws Exception {
		List<Sondage> sondages = sondageService.lister();
		Sondage s = sondageService.findById(id);

		ClasseListerSondageVM classe = new ClasseListerSondageVM();
		classe.setId(s.getClasse().getId());
		classe.setNom(s.getClasse().getNom());

		List<OptionListerSondageVM> options = new ArrayList<>();

		for (OptionSondage os : s.getOptions()) {
			OptionListerSondageVM option = new OptionListerSondageVM();
			option.setId(os.getId());
			option.setLibelle(os.getLibelle());
			option.setDescription(os.getDescription());
			options.add(option);
		}

		SondageByIdVM sondageVM = new SondageByIdVM();
		sondageVM.setId(s.getId());
		sondageVM.setTitre(s.getTitre());
		sondageVM.setClasse(classe);
		sondageVM.setOptions(options);

		return sondageVM;
	}
}
