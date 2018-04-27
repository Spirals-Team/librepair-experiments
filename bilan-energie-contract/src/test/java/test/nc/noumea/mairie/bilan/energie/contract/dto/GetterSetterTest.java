package test.nc.noumea.mairie.bilan.energie.contract.dto;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import nc.noumea.mairie.bilan.energie.contract.dto.Adresse;
import nc.noumea.mairie.bilan.energie.contract.dto.AdresseLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.Analyse;
import nc.noumea.mairie.bilan.energie.contract.dto.Batiment;
import nc.noumea.mairie.bilan.energie.contract.dto.BatimentLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.BatimentSimple;
import nc.noumea.mairie.bilan.energie.contract.dto.CategoriePolice;
import nc.noumea.mairie.bilan.energie.contract.dto.Client;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeCtme;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeSIG;
import nc.noumea.mairie.bilan.energie.contract.dto.Comptage;
import nc.noumea.mairie.bilan.energie.contract.dto.Compteur;
import nc.noumea.mairie.bilan.energie.contract.dto.Conversion;
import nc.noumea.mairie.bilan.energie.contract.dto.Direction;
import nc.noumea.mairie.bilan.energie.contract.dto.EclairagePublic;
import nc.noumea.mairie.bilan.energie.contract.dto.EclairagePublicLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.EclairagePublicSimple;
import nc.noumea.mairie.bilan.energie.contract.dto.Facture;
import nc.noumea.mairie.bilan.energie.contract.dto.FactureEau;
import nc.noumea.mairie.bilan.energie.contract.dto.FactureElectricite;
import nc.noumea.mairie.bilan.energie.contract.dto.FichierAnomalie;
import nc.noumea.mairie.bilan.energie.contract.dto.FichierFacture;
import nc.noumea.mairie.bilan.energie.contract.dto.FichierFactureSimple;
import nc.noumea.mairie.bilan.energie.contract.dto.Infrastructure;
import nc.noumea.mairie.bilan.energie.contract.dto.InfrastructureSimple;
import nc.noumea.mairie.bilan.energie.contract.dto.Luminaire;
import nc.noumea.mairie.bilan.energie.contract.dto.Parametrage;
import nc.noumea.mairie.bilan.energie.contract.dto.Police;
import nc.noumea.mairie.bilan.energie.contract.dto.PoliceSimple;
import nc.noumea.mairie.bilan.energie.contract.dto.StructureLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.Support;
import nc.noumea.mairie.bilan.energie.contract.dto.TypeCompteur;
import nc.noumea.mairie.bilan.energie.contract.dto.TypeEmplacement;
import nc.noumea.mairie.bilan.energie.contract.dto.TypePolice;
import nc.noumea.mairie.bilan.energie.contract.dto.TypeSource;
import nc.noumea.mairie.bilan.energie.contract.dto.TypeSupport;
import nc.noumea.mairie.bilan.energie.contract.dto.TypeZone;
import nc.noumea.mairie.bilan.energie.contract.dto.Utilisateur;
import nc.noumea.mairie.bilan.energie.contract.enumeration.EtatFichier;
import nc.noumea.mairie.bilan.energie.contract.enumeration.TypeAnalyse;
import nc.noumea.mairie.bilan.energie.contract.enumeration.TypeAnomalie;
import nc.noumea.mairie.bilan.energie.contract.enumeration.TypeStructure;
import nc.noumea.mairie.bilan.energie.test.BilanTestNG;

import org.testng.annotations.Test;

/**
 * Test des getters/setters
 *
 * @author Greg Dujardin
 *
 */
public class GetterSetterTest extends BilanTestNG {

	
	/**
	 * Map des valeurs de test pour les types spécifiques
	 */
	private static Map<Class<?>, Object> mapType = new Hashtable<Class<?>, Object>();
	static {

		mapType.put(List.class, new ArrayList<Object>());
		mapType.put(TypeStructure.class, TypeStructure.EP);
		mapType.put(TypeAnomalie.class, TypeAnomalie.ERREUR_NUM_POLICE);
		mapType.put(EtatFichier.class, EtatFichier.ANOMALIE_IMPORT);
		mapType.put(TypeAnalyse.class, TypeAnalyse.PERMANENTE);

		
	}
	
	
	/**
	 * Test des méthodes Getter / Setter des DTOs
	 * 
	 * @throws Throwable
	 */
	@Test
	public void testAllGetterSetter() throws Throwable {

		testClassGetterSetter(Adresse.class);
		testClassGetterSetter(AdresseLabel.class);
		testClassGetterSetter(Analyse.class, mapType);
		testClassGetterSetter(Batiment.class, mapType);
		testClassGetterSetter(BatimentLabel.class);
		testClassGetterSetter(BatimentSimple.class);
		testClassGetterSetter(CategoriePolice.class);
		testClassGetterSetter(Client.class);
		testClassGetterSetter(CodeCtme.class);
		testClassGetterSetter(CodeLabel.class);
		testClassGetterSetter(CodeSIG.class);
		testClassGetterSetter(Comptage.class);
		testClassGetterSetter(Compteur.class, mapType);
		testClassGetterSetter(Conversion.class);
		testClassGetterSetter(Direction.class);
		testClassGetterSetter(EclairagePublic.class, mapType);
		testClassGetterSetter(EclairagePublicLabel.class, mapType);
		testClassGetterSetter(EclairagePublicSimple.class);
		testClassGetterSetter(Facture.class);
		testClassGetterSetter(FactureEau.class);
		testClassGetterSetter(FactureElectricite.class);
		testClassGetterSetter(FichierAnomalie.class, mapType);
		testClassGetterSetter(FichierFacture.class, mapType);
		testClassGetterSetter(FichierFactureSimple.class, mapType);
		testClassGetterSetter(Infrastructure.class, mapType);
		testClassGetterSetter(InfrastructureSimple.class, mapType);
		testClassGetterSetter(Luminaire.class);
		testClassGetterSetter(Parametrage.class);
		testClassGetterSetter(Police.class, mapType);
		testClassGetterSetter(PoliceSimple.class);
		testClassGetterSetter(StructureLabel.class);
		testClassGetterSetter(Support.class, mapType);
		testClassGetterSetter(TypeCompteur.class);
		testClassGetterSetter(TypeEmplacement.class);
		testClassGetterSetter(TypePolice.class);
		testClassGetterSetter(TypeSource.class);
		testClassGetterSetter(TypeSupport.class);
		testClassGetterSetter(TypeZone.class);
		testClassGetterSetter(Utilisateur.class, mapType);

	}
	
	
}





