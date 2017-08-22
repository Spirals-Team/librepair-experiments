/* Copyright (C) 2004-2007  The Chemistry Development Kit (CDK) project
 *
 * Contact: cdk-devel@lists.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.openscience.cdk.qsar.descriptors.atomic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.qsar.IAtomicDescriptor;
import org.openscience.cdk.qsar.result.BooleanResult;
import org.openscience.cdk.smiles.SmilesParser;

/**
 * TestSuite that runs all QSAR tests.
 *
 * @cdk.module test-qsaratomic
 */
public class IsProtonInConjugatedPiSystemDescriptorTest extends AtomicDescriptorTest {

    public IsProtonInConjugatedPiSystemDescriptorTest() {}

    @Before
    public void setUp() throws Exception {
        setDescriptor(IsProtonInConjugatedPiSystemDescriptor.class);
    }

    @Test
    public void testIsProtonInConjugatedPiSystemDescriptor() throws ClassNotFoundException, CDKException,
            java.lang.Exception {
        IAtomicDescriptor descriptor = new IsProtonInConjugatedPiSystemDescriptor();
        Object[] params = {new Boolean(true)};
        descriptor.setParameters(params);
        SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        IAtomContainer mol = sp.parseSmiles("CNC=CC=C");
        addExplicitHydrogens(mol);
        Assert.assertTrue(((BooleanResult) descriptor.calculate(mol.getAtom(13), mol).getValue()).booleanValue());
    }
}
