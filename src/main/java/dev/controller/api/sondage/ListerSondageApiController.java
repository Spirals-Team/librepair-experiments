package dev.controller.api.sondage;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.controller.api.sondage.viewModels.ListerSondageVM;
import dev.controller.api.sondage.viewModels.SondageByIdVM;

@RestController
@RequestMapping("api/sondages")
public class ListerSondageApiController {

	SondageBuilder sondageBuilder;

	public ListerSondageApiController(SondageBuilder sondageBuilder) {
		super();
		this.sondageBuilder = sondageBuilder;
	}

	@GetMapping("/lister")
	public ResponseEntity<List<ListerSondageVM>> getList() {
		return ResponseEntity.ok(sondageBuilder.creerJsonLister());

	}

	@GetMapping("/lister/{id}")
	public ResponseEntity<SondageByIdVM> getSondage(@PathVariable Long id) throws Exception {
		return ResponseEntity.ok(sondageBuilder.creerJsonListerById(id));
	}
}
