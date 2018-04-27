package test.nc.noumea.mairie.bilan.energie.business.dao;

import java.security.Principal;
import java.util.List;

import nc.noumea.mairie.bilan.energie.business.dao.AbstractCrudBusiness;
import nc.noumea.mairie.bilan.energie.contract.dto.Client;
import nc.noumea.mairie.bilan.energie.contract.dto.CodeLabel;
import nc.noumea.mairie.bilan.energie.contract.dto.TypeCompteur;
import nc.noumea.mairie.bilan.energie.contract.dto.TypeEmplacement;
import nc.noumea.mairie.bilan.energie.contract.dto.TypePolice;
import nc.noumea.mairie.bilan.energie.contract.dto.TypeSource;
import nc.noumea.mairie.bilan.energie.contract.dto.TypeSupport;
import nc.noumea.mairie.bilan.energie.contract.dto.Utilisateur;
import nc.noumea.mairie.bilan.energie.contract.service.ClientService;
import nc.noumea.mairie.bilan.energie.contract.service.EclairagePublicService;
import nc.noumea.mairie.bilan.energie.contract.service.TypeCompteurService;
import nc.noumea.mairie.bilan.energie.contract.service.TypeEmplacementService;
import nc.noumea.mairie.bilan.energie.contract.service.TypePoliceService;
import nc.noumea.mairie.bilan.energie.contract.service.TypeSourceService;
import nc.noumea.mairie.bilan.energie.contract.service.TypeSupportService;
import nc.noumea.mairie.bilan.energie.contract.service.UtilisateurService;
import nc.noumea.mairie.bilan.energie.core.exception.BusinessException;
import nc.noumea.mairie.bilan.energie.core.exception.TechnicalException;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Test des méthodes GetAll et getAllReferentiel
 * 
 * @author David ALEXIS
 * 
 */
public class GetAllTest extends BilanBusinessTestNG {

	/*
	 * @see
	 * test.nc.noumea.mairie.bilan.energie.business.BilanBusinessTestNG#init()
	 */
	@BeforeMethod
	protected void init() {
		super.init();

	}

	/*
	 * @see
	 * test.nc.noumea.mairie.bilan.energie.business.BilanBusinessTestNG#destroy
	 * ()
	 */
	@AfterMethod
	protected void destroy() {
		super.destroy();
	}

	/**
	 * Utilisation des services Paramètre et EclairagePublic pour vérifier la class
	 * {@link AbstractCrudBusiness}
	 */
	@Autowired
	private TypeSupportService typeSupportService;
	
	@Autowired
	private TypeSourceService typeSourceService;

	@Autowired
	private TypePoliceService typePoliceService;

	@Autowired
	private TypeEmplacementService typeEmplacementService;
	
	@Autowired
	private TypeCompteurService typeCompteurService;

	@Autowired
	private ClientService clientService;
	
	@Autowired
	private Principal principal;
	
	@Autowired
	private UtilisateurService utilisateurService;
	
	/**
	 * Test
	 * 
     * @throws TechnicalException Exception technique
	 * @throws BusinessException Exception métier
	 */
	@Test
	public void testReferentiel() throws TechnicalException, BusinessException {
		
		List<Utilisateur> liste = utilisateurService.getAll();
		for (Utilisateur uti : liste ) {
			System.out.println("Utilisateur " + uti.getLogin() + "  " + uti.getNom());
			
		}
		
		System.out.println("-----------------------------------------------------------------------------");
		System.out.println("Principal " + principal.getName());
		System.out.println("-----------------------------------------------------------------------------");

		// Type Support
		TypeSupport typeSupport1 = new TypeSupport();
		typeSupport1.setLibelle("Type Support 1");
		typeSupport1 = typeSupportService.create(typeSupport1);

		TypeSupport typeSupport2 = new TypeSupport();
		typeSupport2.setLibelle("Type Support 2");
		typeSupport1 = typeSupportService.create(typeSupport2);
		
		List<TypeSupport> listeTypeSupport = typeSupportService.getAll();
		Assert.assertEquals(listeTypeSupport.size(),2);
		
		List<CodeLabel> listeCodeLabel = typeSupportService.getAllReferentiel();
		Assert.assertEquals(listeCodeLabel.size(), 2);
		
		// Type Source
		TypeSource typeSource1 = new TypeSource();
		typeSource1.setLibelle("Type Source 1");
		typeSource1 = typeSourceService.create(typeSource1);

		TypeSource typeSource2 = new TypeSource();
		typeSource2.setLibelle("Type Support 2");
		typeSource1 = typeSourceService.create(typeSource2);
		
		List<TypeSource> listeTypeSource = typeSourceService.getAll();
		Assert.assertEquals(listeTypeSource.size(),2);
		
		listeCodeLabel = typeSourceService.getAllReferentiel();
		Assert.assertEquals(listeCodeLabel.size(), 2);
		
		// Type Police
		TypePolice typePolice1 = new TypePolice();
		typePolice1.setLibelle("Type Police 1");
		typePolice1 = typePoliceService.create(typePolice1);

		TypePolice typePolice2 = new TypePolice();
		typePolice2.setLibelle("Type Police 2");
		typePolice1 = typePoliceService.create(typePolice2);
		
		List<TypePolice> listeTypePolice = typePoliceService.getAll();
		Assert.assertEquals(listeTypePolice.size(),2);
		
		listeCodeLabel = typePoliceService.getAllReferentiel();
		Assert.assertEquals(listeCodeLabel.size(), 2);

		// Type Emplacement
		TypeEmplacement typeEmplacement1 = new TypeEmplacement();
		typeEmplacement1.setLibelle("Type Emplacement 1");
		typeEmplacement1 = typeEmplacementService.create(typeEmplacement1);

		TypeEmplacement typeEmplacement2 = new TypeEmplacement();
		typeEmplacement2.setLibelle("Type Emplacement 2");
		typeEmplacement1 = typeEmplacementService.create(typeEmplacement2);
		
		List<TypeEmplacement> listeTypeEmplacement = typeEmplacementService.getAll();
		Assert.assertEquals(listeTypeEmplacement.size(),2);
		
		listeCodeLabel = typeEmplacementService.getAllReferentiel();
		Assert.assertEquals(listeCodeLabel.size(), 2);
		
		// Type Compteur
		TypeCompteur typeCompteur1 = new TypeCompteur();
		typeCompteur1.setLibelle("Type Compteur 1");
		typeCompteur1 = typeCompteurService.create(typeCompteur1);

		TypeCompteur typeCompteur2 = new TypeCompteur();
		typeCompteur2.setLibelle("Type Compteur 2");
		typeCompteur1 = typeCompteurService.create(typeCompteur2);
		
		List<TypeCompteur> listeTypeCompteur = typeCompteurService.getAll();
		Assert.assertEquals(listeTypeCompteur.size(),2);
		
		listeCodeLabel = typeCompteurService.getAllReferentiel();
		Assert.assertEquals(listeCodeLabel.size(), 2);
		
		// Client
		Client client1 = new Client();
		client1.setLibelle("Client 1");
		client1 = clientService.create(client1);

		Client client2 = new Client();
		client2.setLibelle("Client 2");
		client1 = clientService.create(client2);
		
		List<Client> listeClient = clientService.getAll();
		Assert.assertEquals(listeClient.size(),2);
		
		listeCodeLabel = clientService.getAllReferentiel();
		Assert.assertEquals(listeCodeLabel.size(), 2);
		
	}
	

}
