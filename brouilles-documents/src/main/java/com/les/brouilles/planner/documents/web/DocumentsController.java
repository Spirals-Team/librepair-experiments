package com.les.brouilles.planner.documents.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.les.brouilles.planner.documents.devis.DevisCreator;
import com.les.brouilles.planner.persistence.model.devis.Devis;
import com.les.brouilles.planner.service.bean.DevisService;
import com.les.brouilles.planner.service.exception.BrouillesDevisNonTrouveException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/documents")
public class DocumentsController {

	@Autowired
	private DevisService devisService;

	@Autowired
	private DevisCreator devisCreator;

	@Autowired
	@Qualifier("mapperJsondevis")
	private ObjectMapper objectMapper;

	@GetMapping(value = "/devisMariagePdf/{idDevis}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> devisMariagePdf(@PathVariable("idDevis") final Long idDevis)
			throws IOException {

		final Devis devis = devisService.findById(idDevis);

		if (null == devis) {
			throw new BrouillesDevisNonTrouveException(idDevis);
		}

		final ByteArrayInputStream bis = devisCreator.generateDevisMariagePdf(devis);

		final HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=Devis-Mariage-" + devis.getNumeroDevis() + ".pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}
}