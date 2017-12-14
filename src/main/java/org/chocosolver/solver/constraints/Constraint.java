/**
 * This file is part of choco-solver, http://choco-solver.org/
 *
 * Copyright (c) 2017, IMT Atlantique. All rights reserved.
 *
 * Licensed under the BSD 4-clause license.
 * See LICENSE file in the project root for full license information.
 */
package org.chocosolver.solver.constraints;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.reification.Opposite;
import org.chocosolver.solver.exception.SolverException;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.util.ESat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * A Constraint is basically a set of <code>Propagator</code>.
 * It can either be posted or reified
 *
 * @author Jean-Guillaume Fages
 * @author Xavier Lorca
 * @author Charles Prud'homme
 * @version major revision 13/01/2014
 * @see org.chocosolver.solver.variables.Variable
 * @see Propagator
 * @see org.chocosolver.solver.propagation.IPropagationEngine
 * @since 0.01
 */
public class Constraint {

    //***********************************************************************************
    // VARIABLES
    //***********************************************************************************

    /**
     * Status of this constraint wrt the model
     */
    public enum Status {
        /**
         * Indicate that this constraint is posted in the model
         */
        POSTED,
        /**
         * Indicate that this constraint is reified in the model
         */
        REIFIED,
        /**
         * Indicate that this constraint is not posted or reified yet
         */
        FREE
    }

    /**
     *  Propagators of the constraint (they will filter domains and eventually check solutions)
     */
    final protected Propagator[] propagators;

    /**
     * BoolVar that reifies this constraint, unique.
     */
    protected BoolVar boolReif;

    /**
     * Opposite constraint of this constraint, unique.
     */
    private Constraint opposite;

    /**
     * Status of this constraint in the model
     */
    private Status mStatus;

    /**
     * Index of this constraint in the model data structure
     */
    private int cidx;

    /**
     * Name of this constraint
     */
    private String name;

    //***********************************************************************************
    // CONSTRUCTOR
    //***********************************************************************************

    /**
     * Make a new constraint defined as a set of given propagators
     *
     * @param name        name of the constraint
     * @param propagators set of propagators defining the constraint
     */
    public Constraint(String name, Propagator... propagators) {
        if (propagators == null || propagators.length == 0) {
            throw new UnsupportedOperationException("cannot create a constraint without propagators ");
        }
        this.name = name;
        this.propagators = propagators;
        this.mStatus = Status.FREE;
        this.cidx = -1;
        for (Propagator propagator : propagators) {
            propagator.defineIn(this);
        }
        Model model = propagators[0].getModel();
        if(model.getSettings().checkDeclaredConstraints()) {
            Set<Constraint> instances = (Set<Constraint>) model.getHook("cinstances");
            if(instances == null){
                instances = new HashSet();
                model.addHook("cinstances", instances);
            }
            instances.add(this);
        }
    }

    //***********************************************************************************
    // METHODS
    //***********************************************************************************

    /**
     * Return an array which contains the propagators declared in <code>this</code>.
     *
     * @return an array of {@link Propagator}.
     */
    public Propagator[] getPropagators() {
        return propagators;
    }

    public Propagator getPropagator(int i) {
        return propagators[i];
    }

    /**
     * Test if this <code>Constraint</code> object is satisfied,
     * regarding its <code>Propagators</code> and its <code>Variable</code> current domains.
     * <p/>
     * This method is called on each solution as a checker when assertions are enabled (-ea in VM parameters)
     * It is also called for constraint reification (to state whether or not a constraint is satisfied)
     * <p/>
     * The method calls entailment checks of <code>this</code> propagators
     *
     * @return <code>ESat.FALSE</code> if the constraint cannot be satisfied (from domain consideration),
     * <code>ESat.TRUE</code> if whatever future decisions are, the constraint will be satisfied for sure (without propagating domain modifications)
     * <code>ESat.UNDIFINED</code> otherwise (more decisions/filtering must be made before concluding about constraint satisfaction)
     */
    public ESat isSatisfied() {
        int sat = 0;
        for (Propagator propagator : propagators) {
            ESat entail = propagator.isEntailed();
            if (entail.equals(ESat.FALSE)) {
                return entail;
            } else if (entail.equals(ESat.TRUE)) {
                sat++;
            }
        }
        if (sat == propagators.length) {
            return ESat.TRUE;
        } else {// No need to check if FALSE, must have been returned before
            return ESat.UNDEFINED;
        }
    }

    public String toString() {
        return name + " (" + Arrays.toString(propagators) + ")";
    }

    /**
     * @return true iff this constraint has been reified
     */
    public final boolean isReified() {
        return boolReif != null;
    }

    /**
     * Reifies the constraint with a boolean variable
     * If the reified boolean variable already exists, an additional (equality) constraint is automatically posted.
     *
     * @param bool the variable to reify with
     */
    public void reifyWith(BoolVar bool) {
        Model s = propagators[0].getModel();
        getOpposite();
        if (boolReif == null) {
            boolReif = bool;
            assert opposite.boolReif == null;
            opposite.boolReif = this.boolReif.not();
            if(boolReif.isInstantiatedTo(1)){
                this.post();
            }else if(boolReif.isInstantiatedTo(0)){
                this.opposite.post();
            }else {
                new ReificationConstraint(boolReif, this, opposite).post();
            }
        } else if (bool != boolReif) {
            s.arithm(bool, "=", boolReif).post();
        }
    }

    /**
     * Get/make the boolean variable indicating whether the constraint is satisfied or not
     * This should not be posted.
     * @return the boolean reifying the constraint
     */
    public final BoolVar reify() {
        if (boolReif == null) {
            Model model = propagators[0].getModel();
            reifyWith(model.boolVar(model.generateName("REIF_")));
        }
        return boolReif;
    }

    /**
     * Posts the constraint to its model so that the constraint must be satisfied.
     * This should not be reified.
     */
    public final void post() {
        propagators[0].getModel().post(this);
    }

    /**
     * For internal usage only, declare the status of this constraint in the model
     * and, if need be, its position in the constraint list.
     * @param aStatus status of this constraint in the model
     * @param idx position of this constraint in the constraint list.
     * @throws SolverException if the constraint a incoherent status is declared
     */
    public final void declareAs(Status aStatus, int idx) throws SolverException {
        checkNewStatus(aStatus);
        mStatus = aStatus;
        cidx = idx;
    }

    /**
     * Check if the new status is not in conflict with the current one
     * @param aStatus new status of the constraint
     * @throws SolverException if the constraint a incoherent status is declared
     */
    public final void checkNewStatus(Status aStatus) throws SolverException{
        switch (mStatus) {
            default:
            case FREE:
                if(aStatus == Status.FREE){
                    throw new SolverException("Try to remove a constraint which is not known from the model.");
                }
                break;
            case POSTED:
                switch (aStatus) {
                    case POSTED:
                        throw new SolverException("Try to post a constraint which is already posted in the model.");
                    case REIFIED:
                        throw new SolverException("Try to post a constraint which is already reified in the model.");
                    default:
                        break;
                }
                break;
            case REIFIED:
                switch (aStatus) {
                    case POSTED:
                        throw new SolverException("Try to reify a constraint which is already posted in the model.");
                    case REIFIED:
                        throw new SolverException("Try to reify a constraint which is already reified in the model.");
                    default:
                        break;
                }
                break;
        }
    }

    /**
     * @return the {@link Status} of this constraint
     */
    public final Status getStatus() {
        return mStatus;
    }

    /**
     * @return the position of this constraint in the model
     */
    public int getCidxInModel() {
        return cidx;
    }

    /**
     * Get the opposite constraint of this constraint.
     * At first call, it creates the opposite constraint,
     * links them together (the opposite constraint of this opposite constraint is this constraint)
     * and returns the opposite.
     * Next calls will return the previously created opposite constraint.
     * In other words, there can be only one opposite per instance of constraint.
     * The default opposite constraint does not filter domains but fails if this constraint is satisfied.
     *
     * @return the opposite constraint of this
     */
    public Constraint getOpposite() {
        if (opposite == null) {
            setOpposite(makeOpposite());
        }
        return opposite;
    }

    protected void setOpposite(Constraint opp){
        opposite = opp;
        opposite.opposite = this;
    }

    /**
     * Make the opposite constraint of this.
     * BEWARE: this method should never be called by the user
     * but it can be overridden to provide better constraint negations
     */
    protected Constraint makeOpposite() {
        return new Opposite(this);
    }

    /**
     * Changes the name of <code>this</code> constraint
     *
     * @param newName the name of the constraint
     */
    public void setName(String newName) {
        name = newName;
    }

    /**
     * @return the name of <code>this</code> constraint
     */
    public String getName() {
        return name;
    }

    /**
     * @return the maximum priority of a propagator of this constraint
     */
    public PropagatorPriority computeMaxPriority() {
        int priority = 1;
        for (Propagator p : propagators) {
            priority = Math.max(priority, p.getPriority().priority);
        }
        return PropagatorPriority.get(priority);
    }

    /**
     * Creates a new constraint with all propagators of toMerge
     * @param name name of the new constraint
     * @param toMerge a set of constraints to merge in this
     * @return a new constraint with all propagators of toMerge
     */
    public static Constraint merge(String name, Constraint... toMerge) {
        ArrayList<Propagator> props = new ArrayList<>();
        for (Constraint c : toMerge) {
            Collections.addAll(props, c.getPropagators());
        }
        return new Constraint(name, props.toArray(new Propagator[props.size()]));
    }
}
