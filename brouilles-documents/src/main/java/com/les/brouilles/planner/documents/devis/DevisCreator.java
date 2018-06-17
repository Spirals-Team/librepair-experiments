package com.les.brouilles.planner.documents.devis;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.les.brouilles.planner.persistence.model.devis.Devis;
import com.les.brouilles.planner.persistence.model.devis.enums.TypeProprietePrix;
import com.les.brouilles.planner.service.devis.json.JsonDevisMariage;
import com.les.brouilles.planner.service.devis.json.propriete.ProprietePrix;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Slf4j
@Component
public class DevisCreator {

	@Autowired
	@Qualifier("mapperJsondevis")
	private ObjectMapper objectMapper;

	// -------------------- public methods ----------------------

	public ByteArrayInputStream generateDevisMariagePdf(final Devis devis) {

		log.info("Création d'un devis mariage PDF");

		try (final InputStream jasperInput = new FileInputStream(
				new File("documents/jasper/devis/devis-mariage.jasper"))) {

			final JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperInput);

			final String json = devis.getJson();
			final JsonDevisMariage mariageJsonDevisPojo = objectMapper.readValue(json, JsonDevisMariage.class);

			final List<ProprietePrix> liste = new ArrayList<>(mariageJsonDevisPojo.getProprietes().values());

			// Filtre les propriétés BASE et ALIMENTAIRE dont la quantité est 0
			// pour ne pas afficher dans le devis
			// @formatter:off
			final List<ProprieteDevis> listeFiltreeBase = liste.stream()
					.filter(isProprieteAvecQuantiteNonNulleEtType(TypeProprietePrix.BASE))
					.map(mapToProprieteDevis())
					.collect(Collectors.toList());

			final List<ProprieteDevis> listeFiltreeAlimentaire = liste.stream()
					.filter(isProprieteAvecQuantiteNonNulleEtType(TypeProprietePrix.ALIMENTAIRE))
					.map(mapToProprieteDevis())
					.collect(Collectors.toList());
			// @formatter:on

			final ProprietePrix propPrixSousTotalBase = mariageJsonDevisPojo.getProprietes().get("sousTotalBase");
			final ProprieteDevis propDevisSousTotalBase = buildSousTotalFromPropriete(propPrixSousTotalBase);
			final ProprietePrix propPrixSousTotalAlimentaire = mariageJsonDevisPojo.getProprietes()
					.get("sousTotalAlimentaire");
			final ProprieteDevis propDevisSousTotalAlimentaire = buildSousTotalFromPropriete(
					propPrixSousTotalAlimentaire);

			// Propriete total general
			final List<ProprieteTotalGeneralDevis> listeSummaryTotalGeneral = completerListProprieteTotalGeneralFrom(
					mariageJsonDevisPojo.getProprietes());

			// Infos Entete devis
			final InfosEnteteDevis infosEnteteDevis = DevisUtils.getInfosEnteteDevis(devis);

			// Ajout de toutes les propriétées
			final List<ProprieteDevis> listeComplete = new ArrayList<>();
			listeComplete.add(buildRubriqueWithLabel("Général"));
			listeComplete.addAll(listeFiltreeBase);
			listeComplete.add(propDevisSousTotalBase);
			listeComplete.add(buildRubriqueWithLabel("Alimentaire"));
			listeComplete.addAll(listeFiltreeAlimentaire);
			listeComplete.add(propDevisSousTotalAlimentaire);
			listeComplete.add(empty());
			listeComplete.add(buildTotalFromPropriete(mariageJsonDevisPojo.getProprietes().get("totalDevis")));

			/* Convert List to JRBeanCollectionDataSource */
			final JRBeanCollectionDataSource listeProprietesJRBean = new JRBeanCollectionDataSource(listeComplete);
			final JRBeanCollectionDataSource listeSummaryTotalJRBean = new JRBeanCollectionDataSource(
					listeSummaryTotalGeneral);

			/* Map to hold Jasper report Parameters */
			final Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("dateDevis", infosEnteteDevis.getDateDevis());
			parameters.put("numeroDevis", infosEnteteDevis.getNumeroDevis());
			parameters.put("nomClient", infosEnteteDevis.getNomClient());
			parameters.put("dateEvenement", infosEnteteDevis.getDateEvenement());
			parameters.put("typeEvenement", "Mariage");
			parameters.put("ListeProprietesDevisMariage", listeProprietesJRBean);
			parameters.put("ListeProprietesTotalGeneralDevisMariage", listeSummaryTotalJRBean);

			final JRDataSource dataSource = new JREmptyDataSource();
			final JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

			return new ByteArrayInputStream(JasperExportManager.exportReportToPdf(jasperPrint));

		} catch (final IOException | JRException e) {
			throw new RuntimeException("Erreur à la création du devis", e);
		} finally {
			log.info("Fin de création d'un devis mariage PDF");
		}
	}

	// -------------------- private methods ----------------------

	private List<ProprieteTotalGeneralDevis> completerListProprieteTotalGeneralFrom(
			final Map<String, ProprietePrix> proprietes) {

		final List<ProprieteTotalGeneralDevis> listeTotalGeneral = new ArrayList<>();

		listeTotalGeneral.add(buildTotalFromPropriete(proprietes.get("acompte"), false));
		listeTotalGeneral.add(buildTotalFromPropriete(proprietes.get("totalRestant"), false));
		listeTotalGeneral.add(buildTotalFromPropriete(proprietes.get("acompteAVerser"), true));
		listeTotalGeneral.add(buildTotalFromPropriete(proprietes.get("soldeARegler"), false));

		return listeTotalGeneral;
	}

	private Predicate<ProprietePrix> isProprieteAvecQuantiteNonNulleEtType(final TypeProprietePrix type) {
		return p -> {

			boolean keep = false;
			if (null != type && type.toString().equals(p.getType()) && p.getQuantite() > 0) {
				keep = true;
			} else {
				log.debug("La propriété {} ne sera pas affichée dans le devis", p);
				keep = false;
			}
			return keep;
		};
	}

	private ProprieteDevis buildSousTotalFromPropriete(final ProprietePrix pp) {
		// @formatter:off
		return ProprieteDevis.builder()
					.label("")
					.nom("")
					.prixTotalHT(pp.getPrixTotalHT() + " €")
					.prixTotalTTC(pp.getPrixTotalTTC()+ " €")
					.prixUnitaireHT("Sous-total")
					.prixUnitaireTTC("")
					.quantite("")
					.remise(false)
					.type("SOUS-TOTAL")
					.build();
		// @formatter:on
	}

	private ProprieteDevis buildTotalFromPropriete(final ProprietePrix pp) {
		// @formatter:off
		return ProprieteDevis.builder()
					.label(pp.getLabel())
					.nom("")
					.prixTotalHT(pp.getPrixTotalHT() + " €")
					.prixTotalTTC(pp.getPrixTotalTTC()+ " €")
					.prixUnitaireHT("")
					.prixUnitaireTTC("")
					.quantite("")
					.remise(false)
					.type("TOTAL")
					.build();
		// @formatter:on
	}

	private ProprieteTotalGeneralDevis buildTotalFromPropriete(final ProprietePrix pp, final boolean isRedLabel) {
		// @formatter:off
		return ProprieteTotalGeneralDevis.builder()
					.label(pp.getLabel())
					.prixTotalTTC(pp.getPrixTotalTTC()+ " €")
					.redLabel(isRedLabel)
					.build();
		// @formatter:on
	}

	private ProprieteDevis buildRubriqueWithLabel(final String label) {
		// @formatter:off
		return ProprieteDevis.builder()
					.label(label)
					.nom("")
					.prixTotalHT("")
					.prixTotalTTC("")
					.prixUnitaireHT("")
					.prixUnitaireTTC("")
					.quantite("")
					.remise(false)
					.type("RUBRIQUE")
					.build();
		// @formatter:on
	}

	private ProprieteDevis empty() {
		// @formatter:off
		return ProprieteDevis.builder()
					.label("")
					.nom("")
					.prixTotalHT("")
					.prixTotalTTC("")
					.prixUnitaireHT("")
					.prixUnitaireTTC("")
					.quantite("")
					.remise(false)
					.type("")
					.build();
		// @formatter:on
	}

	private Function<ProprietePrix, ProprieteDevis> mapToProprieteDevis() {
		return pp -> {
			// @formatter:off
			return ProprieteDevis.builder()
						.label(pp.getLabel())
						.nom(pp.getNom())
						.prixTotalHT(String.valueOf(pp.getPrixTotalHT()) + " €")
						.prixTotalTTC(String.valueOf(pp.getPrixTotalTTC())+ " €")
						.prixUnitaireHT(String.valueOf(pp.getPrixUnitaireHT())+ " €")
						.prixUnitaireTTC(String.valueOf(pp.getPrixUnitaireTTC())+ " €")
						.quantite(String.valueOf(pp.getQuantite()))
						.remise(pp.isRemise())
						.type(pp.getType())
						.build();
			// @formatter:on
		};
	}

}
