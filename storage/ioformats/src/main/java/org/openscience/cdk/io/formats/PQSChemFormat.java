/* Copyright (C) 2003-2007  The Chemistry Development Kit (CDK) project
 *
 * Contact: cdk-devel@lists.sourceforge.net
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.openscience.cdk.io.formats;

import org.openscience.cdk.tools.DataFeatures;

/**
 * See <a href="http://www.pqs-chem.com/">here</a>.
 *
 * @author Miguel Rojas
 *
 * @cdk.module ioformats
 * @cdk.githash
 */
public class PQSChemFormat extends AbstractResourceFormat implements IChemFormat {

    private static IResourceFormat myself = null;

    public PQSChemFormat() {}

    public static IResourceFormat getInstance() {
        if (myself == null) myself = new PQSChemFormat();
        return myself;
    }

    /** {@inheritDoc} */
    @Override
    public String getFormatName() {
        return "Parallel Quantum Solutions";
    }

    /** {@inheritDoc} */
    @Override
    public String getMIMEType() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public String getPreferredNameExtension() {
        return getNameExtensions()[0];
    }

    /** {@inheritDoc} */
    @Override
    public String[] getNameExtensions() {
        return new String[]{"pqs"};
    }

    /** {@inheritDoc} */
    @Override
    public String getReaderClassName() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public String getWriterClassName() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isXMLBased() {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public int getSupportedDataFeatures() {
        return DataFeatures.NONE;
    }

    /** {@inheritDoc} */
    @Override
    public int getRequiredDataFeatures() {
        return DataFeatures.NONE;
    }
}
