package dev.paie.service;

import java.util.List;

import dev.paie.entite.Periode;

public interface PeriodeService {

	void sauvegarder(Periode nouvellePeriode);

	void mettreAJour(Periode periode);

	List<Periode> lister();

}
