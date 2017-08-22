/* Copyright (C) 2006-2007  The Chemistry Development Kit (CDK) project
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
package org.openscience.cdk.qsar;

import java.util.HashMap;
import java.util.Map;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.qsar.result.IDescriptorResult;

/**
 * Abstract bond descriptor class with helper functions for descriptors
 * that require the whole molecule to calculate the descriptor values,
 * which in turn need to be cached for all bonds, so that they can be
 * retrieved one by one.
 *
 * @cdk.module qsar
 * @cdk.githash
 */
public abstract class AbstractBondDescriptor extends AbstractDescriptor implements IBondDescriptor {

    private static final String PREVIOUS_ATOMCONTAINER = "previousAtomContainer";

    private Map                 cachedDescriptorValues = null;

    /**
     * Returns true if the cached IDescriptorResult's are for the given IAtomContainer.
     *
     * @param container
     * @return false, if the cache is for a different IAtomContainer
     */
    public boolean isCachedAtomContainer(IAtomContainer container) {
        if (cachedDescriptorValues == null) return false;
        return (cachedDescriptorValues.get(PREVIOUS_ATOMCONTAINER) == container);
    }

    /**
     * Returns the cached DescriptorValue for the given IBond.
     *
     * @param bond the IAtom for which the DescriptorValue is requested
     * @return     null, if no DescriptorValue was cached for the given IBond
     */
    public IDescriptorResult getCachedDescriptorValue(IBond bond) {
        if (cachedDescriptorValues == null) return null;
        return (IDescriptorResult) cachedDescriptorValues.get(bond);
    }

    /**
     * Caches a DescriptorValue for a given IBond. This method may only
     * be called after setNewContainer() is called.
     *
     * @param bond  IBond to cache the value for
     * @param doubleResult DescriptorValue for the given IBond
     */
    public void cacheDescriptorValue(IBond bond, IAtomContainer container, IDescriptorResult doubleResult) {
        if (cachedDescriptorValues == null) {
            cachedDescriptorValues = new HashMap();
            cachedDescriptorValues.put(PREVIOUS_ATOMCONTAINER, container);
        } else if (cachedDescriptorValues.get(PREVIOUS_ATOMCONTAINER) != container) {
            cachedDescriptorValues.clear();
            cachedDescriptorValues.put(PREVIOUS_ATOMCONTAINER, container);
        }
        cachedDescriptorValues.put(bond, doubleResult);
    }
}
