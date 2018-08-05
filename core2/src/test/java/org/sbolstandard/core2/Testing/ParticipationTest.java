package org.sbolstandard.core2.Testing;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.sbolstandard.core2.AccessType;
import org.sbolstandard.core2.ComponentDefinition;
import org.sbolstandard.core2.DirectionType;
import org.sbolstandard.core2.Interaction;
import org.sbolstandard.core2.ModuleDefinition;
import org.sbolstandard.core2.Participation;
import org.sbolstandard.core2.SBOLDocument;
import org.sbolstandard.core2.SBOLValidationException;
import org.sbolstandard.core2.SystemsBiologyOntology;

public class ParticipationTest {
	private SBOLDocument doc = null;
	private ComponentDefinition TetR = null;
	private ModuleDefinition TetRInverter_MD = null;

	@Before
	public void setUp() throws Exception {
		doc = new SBOLDocument();
		doc.setDefaultURIprefix("http://sbols.org/CRISPR_Example/");
		doc.setComplete(true);
		doc.setCreateDefaults(true);
		doc.setComplete(true);
		TetRInverter_MD = doc.createModuleDefinition("TetRInverter_MD");

		TetR = doc.createComponentDefinition("TetR", ComponentDefinition.PROTEIN);
		TetRInverter_MD.createFunctionalComponent(
				"TetRInverter_fc", AccessType.PUBLIC, TetR.getIdentity(), DirectionType.INOUT);

	}

	@Test
	public void test_RoleMethods()
	{
		assertTrue(TetR.addRole(SystemsBiologyOntology.INHIBITOR));
		assertTrue(TetR.containsRole(SystemsBiologyOntology.INHIBITOR));
		assertTrue(TetR.removeRole(SystemsBiologyOntology.INHIBITOR));
		assertTrue(TetR.getRoles().size() == 0);
	}
	
	@Test
	public void test_ParticipantMethods() throws SBOLValidationException
	{		
		TetR.addRole(SystemsBiologyOntology.INHIBITOR);
		Interaction TetR_Interaction = TetRInverter_MD.createInteraction("TetR_Interaction", SystemsBiologyOntology.NON_COVALENT_BINDING);
		Participation TetR_part = TetR_Interaction.createParticipation("TetR", "TetR", SystemsBiologyOntology.PRODUCT);
		assertTrue(TetR_Interaction.getParticipation("TetR").equals(TetR_part));
		assertTrue(TetR_part.addRole(SystemsBiologyOntology.COMPETITIVE_INHIBITOR));
		assertTrue(TetR_part.containsRole(SystemsBiologyOntology.COMPETITIVE_INHIBITOR));
		assertTrue(TetR_part.getRoles().size()== 2);
		assertTrue(TetR_part.removeRole(SystemsBiologyOntology.COMPETITIVE_INHIBITOR));
		assertFalse(TetR_part.containsRole(SystemsBiologyOntology.COMPETITIVE_INHIBITOR));
		assertTrue(TetR_part.getParticipantDefinition().getIdentity().equals(TetR.getIdentity()));
		assertTrue(TetR_part.getParticipant().getIdentity().equals(TetRInverter_MD.getFunctionalComponent("TetR").getIdentity()));
		assertNotNull(TetR_part);
	}
	

}
