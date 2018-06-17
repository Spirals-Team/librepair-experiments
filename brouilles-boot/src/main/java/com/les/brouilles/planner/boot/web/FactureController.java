package com.les.brouilles.planner.boot.web;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class FactureController {

	// @GetMapping("/factures")
	// public List<Facture> factures() {
	//
	// // FIXME : on devrait stocker la facture en base sinon on est mort si le
	// // client et le serveur sont pas sur la même machine!!
	// final List<Facture> list = new ArrayList<>();
	// list.add(Facture.builder().id(1L).numero("F-18-0001").url("/home/julien/Divers/test_angular.pdf").build());
	// list.add(Facture.builder().id(1L).numero("F-18-0002").build());
	// return list;
	// }
	//
	// @GetMapping("/facture/{id}")
	// public Facture factureParId(@RequestParam(value = "id") final long id) {
	// final Instant now = Instant.now();
	//
	// return Facture.builder().build();
	// }
}