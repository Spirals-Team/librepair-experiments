package test.nc.noumea.mairie.bilan.energie.business.entities;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import nc.noumea.mairie.bilan.energie.business.entities.AdresseEntity;
import nc.noumea.mairie.bilan.energie.business.entities.AdressesConsolideesEntity;
import nc.noumea.mairie.bilan.energie.business.entities.AnalyseEntity;
import nc.noumea.mairie.bilan.energie.business.entities.BatimentEntity;
import nc.noumea.mairie.bilan.energie.business.entities.CategoriePoliceEntity;
import nc.noumea.mairie.bilan.energie.business.entities.ClientEntity;
import nc.noumea.mairie.bilan.energie.business.entities.CodeCtmeEntity;
import nc.noumea.mairie.bilan.energie.business.entities.CodeSIGEntity;
import nc.noumea.mairie.bilan.energie.business.entities.ComptageEntity;
import nc.noumea.mairie.bilan.energie.business.entities.CompteurEntity;
import nc.noumea.mairie.bilan.energie.business.entities.ConversionEntity;
import nc.noumea.mairie.bilan.energie.business.entities.DirectionEntity;
import nc.noumea.mairie.bilan.energie.business.entities.EclairagePublicEntity;
import nc.noumea.mairie.bilan.energie.business.entities.FactureEauEntity;
import nc.noumea.mairie.bilan.energie.business.entities.FactureElectriciteEntity;
import nc.noumea.mairie.bilan.energie.business.entities.FichierAnomalieEntity;
import nc.noumea.mairie.bilan.energie.business.entities.FichierFactureEntity;
import nc.noumea.mairie.bilan.energie.business.entities.InfrastructureEntity;
import nc.noumea.mairie.bilan.energie.business.entities.LuminaireEntity;
import nc.noumea.mairie.bilan.energie.business.entities.ParametrageEntity;
import nc.noumea.mairie.bilan.energie.business.entities.PoliceEntity;
import nc.noumea.mairie.bilan.energie.business.entities.StructureEntity;
import nc.noumea.mairie.bilan.energie.business.entities.SupportEntity;
import nc.noumea.mairie.bilan.energie.business.entities.TypeCompteurEntity;
import nc.noumea.mairie.bilan.energie.business.entities.TypeEmplacementEntity;
import nc.noumea.mairie.bilan.energie.business.entities.TypePoliceEntity;
import nc.noumea.mairie.bilan.energie.business.entities.TypeSourceEntity;
import nc.noumea.mairie.bilan.energie.business.entities.TypeSupportEntity;
import nc.noumea.mairie.bilan.energie.business.entities.TypeZoneEntity;
import nc.noumea.mairie.bilan.energie.business.entities.UtilisateurEntity;
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
		mapType.put(StructureEntity.class, new EclairagePublicEntity());
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

		testClassGetterSetter(AdresseEntity.class);
		testClassGetterSetter(AdressesConsolideesEntity.class);
		testClassGetterSetter(AnalyseEntity.class, mapType);
		testClassGetterSetter(BatimentEntity.class, mapType);
		testClassGetterSetter(CategoriePoliceEntity.class);
		testClassGetterSetter(ClientEntity.class);
		testClassGetterSetter(CodeCtmeEntity.class);
		testClassGetterSetter(CodeSIGEntity.class);
		testClassGetterSetter(ComptageEntity.class);
		testClassGetterSetter(CompteurEntity.class, mapType);
		testClassGetterSetter(ConversionEntity.class);
		testClassGetterSetter(DirectionEntity.class);
		testClassGetterSetter(EclairagePublicEntity.class, mapType);
		testClassGetterSetter(FactureEauEntity.class, mapType);
		testClassGetterSetter(FactureElectriciteEntity.class, mapType);
		testClassGetterSetter(FichierAnomalieEntity.class, mapType);
		testClassGetterSetter(FichierFactureEntity.class, mapType);
		testClassGetterSetter(InfrastructureEntity.class,mapType);
		testClassGetterSetter(LuminaireEntity.class);
		testClassGetterSetter(ParametrageEntity.class);
		testClassGetterSetter(PoliceEntity.class, mapType);
		testClassGetterSetter(StructureEntity.class, mapType);
		testClassGetterSetter(SupportEntity.class, mapType);
		testClassGetterSetter(TypeCompteurEntity.class);
		testClassGetterSetter(TypeEmplacementEntity.class);
		testClassGetterSetter(TypePoliceEntity.class);
		testClassGetterSetter(TypeSourceEntity.class);
		testClassGetterSetter(TypeSupportEntity.class);
		testClassGetterSetter(TypeZoneEntity.class);
		testClassGetterSetter(UtilisateurEntity.class, mapType);

	}
	
	
}