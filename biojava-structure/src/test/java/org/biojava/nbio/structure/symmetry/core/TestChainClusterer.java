/*
 *                    BioJava development code
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  If you do not have a copy,
 * see:
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright for this code is held jointly by the individual
 * authors.  These should be listed in @author doc comments.
 *
 * For more information on the BioJava project and its aims,
 * or to join the biojava-l mailing list, visit the home page
 * at:
 *
 *      http://www.biojava.org/
 *
 */
package org.biojava.nbio.structure.symmetry.core;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.biojava.nbio.structure.symmetry.core.ChainClusterer;
import org.junit.Test;

public class TestChainClusterer {

	/**
	 * Test ChainClusterer.getValidFolds
	 */
	@Test
	public void testValidFolds() {
		List<Integer> stoich;
		List<Integer> folds;
		List<Integer> expected;

		stoich = Arrays.asList(6,4);
		expected = Arrays.asList(1,2);
		folds = ChainClusterer.getValidFolds(stoich);
		assertEquals("Wrong folds for "+stoich,expected,folds);

		stoich = Arrays.asList(6,6);
		expected = Arrays.asList(1,2,3,6);
		folds = ChainClusterer.getValidFolds(stoich);
		assertEquals("Wrong folds for "+stoich,expected,folds);

		stoich = Arrays.asList(6,3);
		expected = Arrays.asList(1,3);
		folds = ChainClusterer.getValidFolds(stoich);
		assertEquals("Wrong folds for "+stoich,expected,folds);

		stoich = Arrays.asList(6,5);
		expected = Arrays.asList(1);
		folds = ChainClusterer.getValidFolds(stoich);
		assertEquals("Wrong folds for "+stoich,expected,folds);

	}
}
