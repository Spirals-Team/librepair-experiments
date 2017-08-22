/* Copyright (C) 2008 Miguel Rojas <miguelrojasch@users.sf.net>
 *
 *  Contact: cdk-devel@lists.sourceforge.net
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2.1
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.openscience.cdk.reaction.type;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.aromaticity.Aromaticity;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IReaction;
import org.openscience.cdk.interfaces.IReactionSet;
import org.openscience.cdk.interfaces.IRing;
import org.openscience.cdk.interfaces.IRingSet;
import org.openscience.cdk.reaction.IReactionProcess;
import org.openscience.cdk.reaction.ReactionEngine;
import org.openscience.cdk.reaction.ReactionSpecification;
import org.openscience.cdk.reaction.mechanism.RadicalSiteRearrangementMechanism;
import org.openscience.cdk.reaction.type.parameters.IParameterReact;
import org.openscience.cdk.reaction.type.parameters.SetReactionCenter;
import org.openscience.cdk.ringsearch.AllRingsFinder;
import org.openscience.cdk.tools.HOSECodeGenerator;
import org.openscience.cdk.tools.ILoggingTool;
import org.openscience.cdk.tools.LoggingToolFactory;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * This reaction could be represented as [A*]-(C)_4-C5[R] =&gt; A([R])-(C_4)-[C5*]. Due to
 * the single electron of atom A the R is moved.</p>
 * <p>It is processed by the RadicalSiteRearrangementMechanism class</p>
 *
 * <pre>
 *  IAtomContainerSet setOfReactants = DefaultChemObjectBuilder.getInstance().newAtomContainerSet();
 *  setOfReactants.addAtomContainer(new AtomContainer());
 *  IReactionProcess type = new RadicalSiteRrDeltaReaction();
 *  Object[] params = {Boolean.FALSE};
    type.setParameters(params);
 *  IReactionSet setOfReactions = type.initiate(setOfReactants, null);
 *  </pre>
 *
 * <p>We have the possibility to localize the reactive center. Good method if you
 * want to localize the reaction in a fixed point</p>
 * <pre>atoms[0].setFlag(CDKConstants.REACTIVE_CENTER,true);</pre>
 * <p>Moreover you must put the parameter Boolean.TRUE</p>
 * <p>If the reactive center is not localized then the reaction process will
 * try to find automatically the possible reactive center.</p>
 *
 *
 * @author         Miguel Rojas
 *
 * @cdk.created    2006-10-20
 * @cdk.module     reaction
 * @cdk.githash
 *
 * @see RadicalSiteRearrangementMechanism
 **/
public class RadicalSiteRrDeltaReaction extends ReactionEngine implements IReactionProcess {

    private static ILoggingTool logger = LoggingToolFactory.createLoggingTool(RadicalSiteRrDeltaReaction.class);

    /**
     * Constructor of the RadicalSiteRrDeltaReaction object
     *
     */
    public RadicalSiteRrDeltaReaction() {}

    /**
     *  Gets the specification attribute of the RadicalSiteRrDeltaReaction object
     *
     *@return    The specification value
     */
    @Override
    public ReactionSpecification getSpecification() {
        return new ReactionSpecification(
                "http://almost.cubic.uni-koeln.de/jrg/Members/mrc/reactionDict/reactionDict#RadicalSiteRrDelta", this
                        .getClass().getName(), "$Id$", "The Chemistry Development Kit");
    }

    /**
     *  Initiate process.
     *  It is needed to call the addExplicitHydrogensToSatisfyValency
     *  from the class tools.HydrogenAdder.
     *
     *
     *@exception  CDKException  Description of the Exception

     * @param  reactants         reactants of the reaction.
    * @param  agents            agents of the reaction (Must be in this case null).
     */
    @Override
    public IReactionSet initiate(IAtomContainerSet reactants, IAtomContainerSet agents) throws CDKException {

        logger.debug("initiate reaction: RadicalSiteRrDeltaReaction");

        if (reactants.getAtomContainerCount() != 1) {
            throw new CDKException("RadicalSiteRrDeltaReaction only expects one reactant");
        }
        if (agents != null) {
            throw new CDKException("RadicalSiteRrDeltaReaction don't expects agents");
        }

        IReactionSet setOfReactions = reactants.getBuilder().newInstance(IReactionSet.class);
        IAtomContainer reactant = reactants.getAtomContainer(0);

        AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(reactant);
        Aromaticity.cdkLegacy().apply(reactant);
        AllRingsFinder arf = new AllRingsFinder();
        IRingSet ringSet = arf.findAllRings(reactant);
        for (int ir = 0; ir < ringSet.getAtomContainerCount(); ir++) {
            IRing ring = (IRing) ringSet.getAtomContainer(ir);
            for (int jr = 0; jr < ring.getAtomCount(); jr++) {
                IAtom aring = ring.getAtom(jr);
                aring.setFlag(CDKConstants.ISINRING, true);
            }
        }
        /*
         * if the parameter hasActiveCenter is not fixed yet, set the active
         * centers
         */
        IParameterReact ipr = super.getParameterClass(SetReactionCenter.class);
        if (ipr != null && !ipr.isSetParameter()) setActiveCenters(reactant);

        HOSECodeGenerator hcg = new HOSECodeGenerator();
        Iterator<IAtom> atomis = reactant.atoms().iterator();
        while (atomis.hasNext()) {
            IAtom atomi = atomis.next();
            if (atomi.getFlag(CDKConstants.REACTIVE_CENTER) && reactant.getConnectedSingleElectronsCount(atomi) == 1) {

                hcg.getSpheres(reactant, atomi, 3, true);
                List<IAtom> atom1s = hcg.getNodesInSphere(3);

                hcg.getSpheres(reactant, atomi, 4, true);
                Iterator<IAtom> atomls = hcg.getNodesInSphere(4).iterator();
                while (atomls.hasNext()) {
                    IAtom atoml = atomls.next();
                    if (atoml != null && atoml.getFlag(CDKConstants.REACTIVE_CENTER)
                            && !atoml.getFlag(CDKConstants.ISINRING)
                            && (atoml.getFormalCharge() == CDKConstants.UNSET ? 0 : atoml.getFormalCharge()) == 0
                            && !atoml.equals("H") && reactant.getMaximumBondOrder(atoml) == IBond.Order.SINGLE) {

                        Iterator<IAtom> atomRs = reactant.getConnectedAtomsList(atoml).iterator();
                        while (atomRs.hasNext()) {
                            IAtom atomR = atomRs.next();
                            if (atom1s.contains(atomR)) continue;
                            if (reactant.getBond(atomR, atoml).getFlag(CDKConstants.REACTIVE_CENTER)
                                    && atomR.getFlag(CDKConstants.REACTIVE_CENTER)
                                    && (atomR.getFormalCharge() == CDKConstants.UNSET ? 0 : atomR.getFormalCharge()) == 0) {

                                ArrayList<IAtom> atomList = new ArrayList<IAtom>();
                                atomList.add(atomR);
                                atomList.add(atomi);
                                atomList.add(atoml);
                                ArrayList<IBond> bondList = new ArrayList<IBond>();
                                bondList.add(reactant.getBond(atomR, atoml));

                                IAtomContainerSet moleculeSet = reactant.getBuilder().newInstance(
                                        IAtomContainerSet.class);
                                moleculeSet.addAtomContainer(reactant);
                                IReaction reaction = mechanism.initiate(moleculeSet, atomList, bondList);
                                if (reaction == null)
                                    continue;
                                else
                                    setOfReactions.addReaction(reaction);

                            }

                        }

                    }
                }
            }
        }
        return setOfReactions;
    }

    /**
     * set the active center for this molecule.
     * The active center will be those which correspond with [A*]-(C)_2-C3[R]
     * <pre>
     * C: Atom with single electron
     * C5: Atom with the R to move
     *  </pre>
     *
     * @param reactant The molecule to set the activity
     * @throws CDKException
     */
    private void setActiveCenters(IAtomContainer reactant) throws CDKException {
        HOSECodeGenerator hcg = new HOSECodeGenerator();
        Iterator<IAtom> atomis = reactant.atoms().iterator();
        while (atomis.hasNext()) {
            IAtom atomi = atomis.next();
            if (reactant.getConnectedSingleElectronsCount(atomi) == 1) {

                hcg.getSpheres(reactant, atomi, 3, true);
                List<IAtom> atom1s = hcg.getNodesInSphere(3);

                hcg.getSpheres(reactant, atomi, 4, true);
                Iterator<IAtom> atomls = hcg.getNodesInSphere(4).iterator();
                while (atomls.hasNext()) {
                    IAtom atoml = atomls.next();
                    if (atoml != null && !atoml.getFlag(CDKConstants.ISINRING)
                            && (atoml.getFormalCharge() == CDKConstants.UNSET ? 0 : atoml.getFormalCharge()) == 0
                            && !atoml.equals("H") && reactant.getMaximumBondOrder(atoml) == IBond.Order.SINGLE) {

                        Iterator<IAtom> atomRs = reactant.getConnectedAtomsList(atoml).iterator();
                        while (atomRs.hasNext()) {
                            IAtom atomR = atomRs.next();
                            if (atom1s.contains(atomR)) continue;
                            if ((atomR.getFormalCharge() == CDKConstants.UNSET ? 0 : atomR.getFormalCharge()) == 0) {

                                atomi.setFlag(CDKConstants.REACTIVE_CENTER, true);
                                atoml.setFlag(CDKConstants.REACTIVE_CENTER, true);
                                atomR.setFlag(CDKConstants.REACTIVE_CENTER, true);
                                reactant.getBond(atomR, atoml).setFlag(CDKConstants.REACTIVE_CENTER, true);
                            }
                        }
                    }
                }
            }
        }
    }
}
