package it.unibz.inf.ontop.datalog.impl;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import fj.*;
import fj.data.List;
import fj.data.Set;
import fj.data.TreeMap;
import it.unibz.inf.ontop.datalog.*;
import it.unibz.inf.ontop.model.term.*;
import it.unibz.inf.ontop.model.term.Function;
import it.unibz.inf.ontop.model.term.functionsymbol.Predicate;
import it.unibz.inf.ontop.substitution.SubstitutionFactory;
import it.unibz.inf.ontop.substitution.Var2VarSubstitution;
import it.unibz.inf.ontop.substitution.impl.SubstitutionUtilities;

import java.util.*;

/**
 * Default implementation of PullOutEqualityNormalizer. Is Left-Join aware.
 *
 * Immutable class (instances have no attribute).
 *
 * Main challenge: putting the equalities at the "right" (good) place.
 * Rules for accepting/rejecting to move up boolean conditions:
 *   - Left of the LJ: ACCEPT. Why? If they appeared as ON conditions of the LJ, they would "filter" ONLY the right part,
 *                             NOT THE LEFT.
 *   - Right of the LJ: REJECT. Boolean conditions have to be used as ON conditions of the LOCAL LJ.
 *   - "Real" JOIN (joins between two tables): REJECT. Local ON conditions are (roughly) equivalent to the "global" WHERE
 *     conditions.
 *   - "Fake" JOIN (one data atoms and filter conditions). ACCEPT. Need a JOIN/LJ for being used as ON conditions.
 *                  If not blocked later, they will finish as WHERE conditions (atoms not embedded in a META-one).
 *
 *  TODO: create static "function" objects for performance improving
 *
 */
public class PullOutEqualityNormalizerImpl implements PullOutEqualityNormalizer {

    private final static Ord<Variable> VARIABLE_ORD = Ord.hashEqualsOrd();
    private final static List<P2<Variable, Constant>> EMPTY_VARIABLE_CONSTANT_LIST = List.nil();
    private final static List<P2<Variable, Variable>> EMPTY_VARIABLE_RENAMING_LIST = List.nil();
    private final static List<Function> EMPTY_ATOM_LIST = List.nil();
    private final Function trueEq;

    private final SubstitutionFactory substitutionFactory;
    private final TermFactory termFactory;
    private final DatalogFactory datalogFactory;
    private final DatalogTools datalogTools;
    private final SubstitutionUtilities substitutionUtilities;

    @Inject
    private PullOutEqualityNormalizerImpl(SubstitutionFactory substitutionFactory, TermFactory termFactory,
                                          DatalogFactory datalogFactory, DatalogTools datalogTools,
                                          SubstitutionUtilities substitutionUtilities) {
        this.substitutionFactory = substitutionFactory;
        this.termFactory = termFactory;
        this.substitutionUtilities = substitutionUtilities;
        ValueConstant valueTrue = termFactory.getBooleanConstant(true);
        this.trueEq = termFactory.getFunctionEQ(valueTrue, valueTrue);
        this.datalogFactory = datalogFactory;
        this.datalogTools = datalogTools;
    }


    /**
     * High-level method.
     *
     * Returns a new normalized rule.
     */
    @Override
    public CQIE normalizeByPullingOutEqualities(final CQIE initialRule) {
        CQIE newRule = initialRule.clone();

        // Mutable object
        final VariableDispatcher variableDispatcher = new VariableDispatcher(initialRule, termFactory);

        /**
         * Result for the top atoms of the rule.
         */
        PullOutEqLocalNormResult result = normalizeSameLevelAtoms(List.iterableList(newRule.getBody()), variableDispatcher);

        newRule.updateBody(new ArrayList(result.getAllAtoms().toCollection()));
        return newRule;
    }

    /**
     * Builds out a ExtractEqNormResult by aggregating the results of atoms at one given level
     *    (top level, inside a Join, a LJ).
     *
     */
    private PullOutEqLocalNormResult normalizeSameLevelAtoms(final List<Function> initialAtoms,
                                                               final VariableDispatcher variableDispatcher) {

        /**
         * First, it normalizes the data and composite atoms.
         */
        PullOutEqLocalNormResult mainAtomsResult = normalizeDataAndCompositeAtoms(initialAtoms, variableDispatcher);

        /**
         * Applies the substitution resulting from the data and composite atoms to the other atoms (filter, group atoms).
         */
        final Var2VarSubstitution substitution = mainAtomsResult.getVar2VarSubstitution();
        List<Function> otherAtoms = initialAtoms
                .filter(atom -> !datalogTools.isDataOrLeftJoinOrJoinAtom(atom))
                .map(atom -> {
                    Function newAtom = (Function) atom.clone();
                    // SIDE-EFFECT on the newly created object
                    substitutionUtilities.applySubstitution(newAtom, substitution);
                    return newAtom;
        });

        /**
         * Splits all the atoms into a non-pushable and a pushable group.
         */
        P2<List<Function>, List<Function>> otherAtomsP2 = splitPushableAtoms(otherAtoms);
        List<Function> nonPushableAtoms = mainAtomsResult.getNonPushableAtoms().append(otherAtomsP2._1());
        List<Function> pushableAtoms = mainAtomsResult.getPushableBoolAtoms().append(otherAtomsP2._2());

        return new PullOutEqLocalNormResult(nonPushableAtoms, pushableAtoms, substitution);
    }

    /**
     * Normalizes the data and composite atoms and merges them into a PullOutEqLocalNormResult.
     */
    private PullOutEqLocalNormResult normalizeDataAndCompositeAtoms(final List<Function> sameLevelAtoms,
                                                                           final VariableDispatcher variableDispatcher) {

        /**
         * Normalizes the data atoms.
         */
        P3<List<Function>, List<Function>, Var2VarSubstitution> dataAtomResults = normalizeDataAtoms(sameLevelAtoms, variableDispatcher);
        List<Function> firstNonPushableAtoms = dataAtomResults._1();
        List<Function> firstPushableAtoms = dataAtomResults._2();
        Var2VarSubstitution dataAtomSubstitution = dataAtomResults._3();

        /**
         * Normalizes the composite atoms.
         */
        List<PullOutEqLocalNormResult> compositeAtomResults = sameLevelAtoms
                .filter(datalogTools::isLeftJoinOrJoinAtom)
                .map(atom -> normalizeCompositeAtom(atom, variableDispatcher));

        List<Function> secondNonPushableAtoms = compositeAtomResults
                .bind(PullOutEqLocalNormResult::getNonPushableAtoms);

        List<Function> secondPushableAtoms = compositeAtomResults
                .bind(PullOutEqLocalNormResult::getPushableBoolAtoms);

        /**
         * Merges all the substitutions (one per composite atom and one for all data atoms) into one.
         *
         * Additional equalities might be produced during this process.
         */
        List<Var2VarSubstitution> substitutionsToMerge = compositeAtomResults
                .map(PullOutEqLocalNormResult::getVar2VarSubstitution).snoc(dataAtomSubstitution);

        P2<Var2VarSubstitution, List<Function>> substitutionResult = mergeSubstitutions(substitutionsToMerge);
        Var2VarSubstitution mergedSubstitution = substitutionResult._1();
        List<Function> additionalEqualities = substitutionResult._2();


        /**
         * Groups the non-pushable and pushable atoms.
         */
        List<Function> nonPushableAtoms = firstNonPushableAtoms.append(secondNonPushableAtoms);
        List<Function> pushableAtoms = firstPushableAtoms.append(secondPushableAtoms).append(additionalEqualities);

        return new PullOutEqLocalNormResult(nonPushableAtoms, pushableAtoms, mergedSubstitution);
    }

    /**
     * Normalizes the data atoms among same level atoms.
     *
     * Returns the normalized data atoms, the pushable atoms produced (equalities) and the produced substitution.
     */
    private P3<List<Function>, List<Function>, Var2VarSubstitution> normalizeDataAtoms(final List<Function> sameLevelAtoms,
                                                                                       final VariableDispatcher variableDispatcher) {
        /**
         * Normalizes all the data atoms.
         */
        List<Function> dataAtoms = sameLevelAtoms
                .filter(Function::isDataFunction);

        List<P3<Term, List<P2<Variable, Variable>>, List<P2<Variable, Constant>>>> atomResults = dataAtoms
                // Uses the fact atoms are encoded as functional terms
                .map(atom -> normalizeFunctionalTermInDataAtom(atom, variableDispatcher));

        List<Function> normalizedDataAtoms = atomResults
                .map(triple -> (Function) triple._1());
        // Variable-Variable equalities
        List<P2<Variable, Variable>> variableRenamings = atomResults
                .bind(P3.<Term, List<P2<Variable, Variable>>, List<P2<Variable, Constant>>>__2());

        /**
         * Merges the variable renamings into a substitution and a list of variable-to-variable equalities.
         */
        P2<Var2VarSubstitution, List<Function>> renamingResult = mergeVariableRenamings(variableRenamings);
        Var2VarSubstitution substitution = renamingResult._1() ;
        List<Function> var2varEqualities = renamingResult._2();

        /**
         * Constructs variable-constant equalities.
         */
        List<P2<Variable,Constant>> varConstantPairs  = atomResults.bind(
                P3.<Term, List<P2<Variable, Variable>>, List<P2<Variable, Constant>>>__3());
        List<Function> varConstantEqualities = generateVariableConstantEqualities(varConstantPairs);

        /**
         * All the produced equalities form the pushable atoms.
         */
        List<Function> pushableAtoms = var2varEqualities.append(varConstantEqualities);

        return P.p(normalizedDataAtoms, pushableAtoms, substitution);
    }

    /**
     * Normalizes a left-join or join meta-atom.
     */
    private PullOutEqLocalNormResult normalizeCompositeAtom(Function atom, VariableDispatcher variableDispatcher) {
        /**
         * Meta-atoms (algebra)
         */
        if (atom.isAlgebraFunction()) {
            Predicate functionSymbol = atom.getFunctionSymbol();
            if (functionSymbol.equals(datalogFactory.getSparqlLeftJoinPredicate())) {
                return normalizeLeftJoin(atom, variableDispatcher);
            } else if (functionSymbol.equals(datalogFactory.getSparqlJoinPredicate())) {
                return normalizeJoin(atom, variableDispatcher);
            }
        }

        throw new IllegalArgumentException("A composite (join, left join) atom was expected, not " + atom);
    }

    /**
     * Normalizes a Left-join meta-atom.
     *
     * Currently works with the arbitrary n-ary.
     * Could be simplified when the 3-arity of LJ will be enforced.
     *
     * Blocks pushable atoms from the right part --> they remain local.
     *
     */
    private PullOutEqLocalNormResult normalizeLeftJoin(final Function leftJoinMetaAtom,
                                                              final VariableDispatcher variableDispatcher) {
        /**
         * Splits the left and the right atoms.
         * This could be simplified once the 3-arity will be enforced.
         *
         */
        final P2<List<Function>, List<Function>> splittedAtoms = splitLeftJoinSubAtoms(leftJoinMetaAtom);
        final List<Function> initialLeftAtoms = splittedAtoms._1();
        final List<Function> initialRightAtoms = splittedAtoms._2();

        /**
         * Normalizes the left and the right parts separately.
         */
        PullOutEqLocalNormResult leftNormalizationResults = normalizeSameLevelAtoms(initialLeftAtoms, variableDispatcher);
        PullOutEqLocalNormResult rightNormalizationResults = normalizeSameLevelAtoms(initialRightAtoms, variableDispatcher);

        /**
         * Merges the substitutions produced by the left and right parts into one substitution.
         * Variable-to-variable equalities might be produced during this process.
         */
        List<Var2VarSubstitution> substitutionsToMerge = List.<Var2VarSubstitution>nil()
                .snoc(leftNormalizationResults.getVar2VarSubstitution())
                .snoc(rightNormalizationResults.getVar2VarSubstitution());
        P2<Var2VarSubstitution, List<Function>> substitutionResult = mergeSubstitutions(substitutionsToMerge);

        Var2VarSubstitution mergedSubstitution = substitutionResult._1();
        List<Function> joiningEqualities = substitutionResult._2();

        /**
         * Builds the normalized left-join meta atom.
         *
         * Currently separates the left and the right part by the atom EQ(t,t).
         * Only one atom is presumed on the left part.
         *
         * This part would have to be updated if we want to enforce the 3-arity of a LJ
         * (currently not respected).
         * --> Joining conditions would have to be fold into a AND(...) boolean expression.
         *
         */
        List<Function> remainingLJAtoms = leftNormalizationResults.getNonPushableAtoms().snoc(trueEq).
                append(rightNormalizationResults.getAllAtoms()).append(joiningEqualities);
        // TODO: add a proper method in the data factory
        Function normalizedLeftJoinAtom = termFactory.getFunction(
                datalogFactory.getSparqlLeftJoinPredicate(),
                new ArrayList<Term>(remainingLJAtoms.toCollection()));

        /**
         * The only pushable atoms are those of the left part.
         */
        List<Function> pushedAtoms = leftNormalizationResults.getPushableBoolAtoms();

        return new PullOutEqLocalNormResult(List.cons(normalizedLeftJoinAtom, EMPTY_ATOM_LIST), pushedAtoms, mergedSubstitution);
    }

    /**
     * Normalizes a Join meta-atom.
     *
     * If is a real join (join between two data/composite atoms), blocks the join conditions.
     * Otherwise, propagates the pushable atoms and replaces its data/composite sub-atom.
     *
     * The 3-arity is respected for real joins. This involves Join and boolean folding.
     */
    private PullOutEqLocalNormResult normalizeJoin(final Function joinMetaAtom,
                                                          final VariableDispatcher variableDispatcher) {
        List<Function> subAtoms = List.iterableList((java.util.List<Function>)(java.util.List<?>) joinMetaAtom.getTerms());
        PullOutEqLocalNormResult normalizationResult = normalizeSameLevelAtoms(subAtoms, variableDispatcher);

        /**
         * Real join
         */
        if (datalogTools.isRealJoin(subAtoms)) {

            /**
             * Folds the joining conditions (they will remain in the JOIN meta-atom, they are not pushed)
             * and finally folds the Join meta-atom to respected its 3-arity.
             */
            Function joiningCondition = datalogTools.foldBooleanConditions(normalizationResult.getPushableBoolAtoms());
            Function normalizedJoinMetaAtom = datalogTools.foldJoin(normalizationResult.getNonPushableAtoms(),joiningCondition);

            /**
             * A real JOIN is blocking --> no pushable boolean atom.
             */
            return new PullOutEqLocalNormResult(List.cons(normalizedJoinMetaAtom, EMPTY_ATOM_LIST), EMPTY_ATOM_LIST,
                    normalizationResult.getVar2VarSubstitution());
        }
        /**
         * Fake join: no need to create a new result
         * because only have one data/join/LJ atom that will remain (filter conditions are pushed)
         *
         */
        else {
            return normalizationResult;
        }
    }

    /**
     * Normalizes a Term found in a data atom.
     *
     * According to this concrete type, delegates the normalization to a sub-method.
     *
     * Returns
     *   - the normalized term,
     *   - a list of variable-to-variable renamings,
     *   - a list of variable-to-constant pairs.
     *
     * TODO: This would be much nicer with as a Visitor.
     */
    private  P3<Term, List<P2<Variable, Variable>>, List<P2<Variable, Constant>>> normalizeTermInDataAtom(
            Term term, VariableDispatcher variableDispatcher) {
        if (term instanceof Variable) {
            return normalizeVariableInDataAtom((Variable) term, variableDispatcher);
        }
        else if (term instanceof Constant) {
            return normalizeConstantInDataAtom((Constant) term, variableDispatcher);
        }
        else if (term instanceof Function) {
            return normalizeFunctionalTermInDataAtom((Function) term, variableDispatcher);
        }
        else {
            throw new IllegalArgumentException("Unexpected term inside a data atom: " + term);
        }
    }

    /**
     * Normalizes a Variable found in a data atom by renaming it.
     *
     * Renaming is delegated to the variable dispatcher.
     * The latter guarantees the variable with be used ONLY ONCE in a data atom of the body of the rule.
     *
     *  Returns
     *   - the new variable,
     *   - a list composed of the produced variable-to-variable renaming,
     *   - an empty list of variable-to-constant pairs.
     *
     */
    private static P3<Term, List<P2<Variable, Variable>>, List<P2<Variable, Constant>>> normalizeVariableInDataAtom(
            Variable previousVariable, VariableDispatcher variableDispatcher) {

        Variable newVariable = variableDispatcher.renameDataAtomVariable(previousVariable);
        List<P2<Variable, Variable>> variableRenamings = List.cons(P.p(previousVariable, newVariable), EMPTY_VARIABLE_RENAMING_LIST);

        return P.p((Term) newVariable, variableRenamings, EMPTY_VARIABLE_CONSTANT_LIST);
    }

    /**
     * Normalizes a Constant found in a data atom by replacing it by a variable and creating a variable-to-constant pair.
     *
     * Returns
     *   - the new variable,
     *   - an empty list of variable-to-variable renamings,
     *   - a list composed of the created variable-to-constant pair.
     */
    private static P3<Term, List<P2<Variable, Variable>>, List<P2<Variable, Constant>>> normalizeConstantInDataAtom(
            Constant constant, VariableDispatcher variableDispatcher) {
        Variable newVariable = variableDispatcher.generateNewVariable();

        List<P2<Variable, Constant>> variableConstantPairs = List.cons(P.p(newVariable, constant), EMPTY_VARIABLE_CONSTANT_LIST);

        return  P.p((Term) newVariable, EMPTY_VARIABLE_RENAMING_LIST, variableConstantPairs);
    }


    /**
     * Normalizes a functional term found in a data atom or that is the data atom itself (trick!).
     *
     * Basically, normalizes all its sub-atoms (may be recursive) and rebuilds an updated functional term.
     *
     * All the variable-to-variable renamings and variable-to-constant pairs are concatenated
     * into their two respective lists.
     *
     *
     * Returns
     *   - the normalized functional term,
     *   - a list of variable-to-variable renamings,
     *   - a list of variable-to-constant pairs.
     */
    private P3<Term, List<P2<Variable, Variable>>, List<P2<Variable, Constant>>> normalizeFunctionalTermInDataAtom(
                        Function functionalTerm, final VariableDispatcher variableDispatcher) {

        /**
         * Normalizes sub-terms.
         */
        List<P3<Term, List<P2<Variable, Variable>>, List<P2<Variable, Constant>>>> subTermResults =
                List.iterableList(functionalTerm.getTerms())
                        .map(term -> normalizeTermInDataAtom(term, variableDispatcher));

        /**
         * Retrieves normalized sub-terms.
         */
        List<Term> newSubTerms = subTermResults.map(P3::_1);
        Function newFunctionalTerm = datalogTools.constructNewFunction(functionalTerm.getFunctionSymbol(), newSubTerms);

        /**
         * Concatenates variable-to-variable renamings and variable-to-constant pairs.
         */
        List<P2<Variable, Variable>> variableRenamings = subTermResults.bind(P3::_2);
        List<P2<Variable, Constant>> variableConstantPairs = subTermResults.bind(P3::_3);


        return P.p((Term) newFunctionalTerm, variableRenamings, variableConstantPairs);
    }

    /**
     * Merges a list of substitution into one and also returns a list of generated variable-to-variable equalities.
     *
     * See mergeVariableRenamings for further details.
     *
     */
    private P2<Var2VarSubstitution, List<Function>> mergeSubstitutions(List<Var2VarSubstitution> substitutionsToMerge) {

        /**
         * Transforms the substitutions into list of variable-to-variable pairs and concatenates them.
         */
        List<P2<Variable, Variable>> renamingPairs = substitutionsToMerge
                // Transforms the map of the substitution in a list of pairs
                .bind(substitution -> TreeMap.fromMutableMap(VARIABLE_ORD, substitution.getImmutableMap())
                        .toStream().toList());

        /**
         * Merges renaming variable-to-variable pairs into a substitution
         * and retrieves generated variable-to-variable equalities.
         */
        return mergeVariableRenamings(renamingPairs);
    }

    /**
     * Merges renaming variable-to-variable pairs into a substitution and also returns
     *     a list of generated variable-to-variable equalities.
     *
     * Since a variable-to-variable substitution maps a variable to ONLY one variable, merging
     * involves selecting the target variable among a set.
     *
     * The first variable (lowest) is selecting according a hash ordering.
     */
    private P2<Var2VarSubstitution, List<Function>> mergeVariableRenamings(
            List<P2<Variable, Variable>> renamingPairs) {
        /**
         * Groups pairs according to the initial variable
         */
        TreeMap<Variable, Set<Variable>> commonMap = renamingPairs.groupBy(P2.<Variable, Variable>__1(),
                P2.<Variable, Variable>__2()).
                /**
                 * and converts the list of target variables into a set.
                 */
                map(equivalentVariables -> Set.iterableSet(VARIABLE_ORD, equivalentVariables));

        /**
         * Generates equalities between the target variables
         */
        List<Function> newEqualities = commonMap.values()
                .bind(this::generateVariableEqualities);

        /**
         * Selects the target variables for the one-to-one substitution map.
         *
         * Selection consists in taking the first element of set.
         */
        TreeMap<Variable, Variable> mergedMap = commonMap
                .map(variables -> variables.toList().head());
        Var2VarSubstitution mergedSubstitution = substitutionFactory.getVar2VarSubstitution(
                ImmutableMap.copyOf(mergedMap.toMutableMap()));

        return P.p(mergedSubstitution, newEqualities);
    }

    /**
     * Converts the variable-to-constant pairs into a list of equalities.
     */
    private List<Function> generateVariableConstantEqualities(List<P2<Variable, Constant>> varConstantPairs) {
        return varConstantPairs
                .map((F<P2<Variable, Constant>, Function>) pair -> termFactory.getFunctionEQ(pair._1(), pair._2()));
    }

    /**
     * Converts the variable-to-variable pairs into a list of equalities.
     */
    private List<Function> generateVariableEqualities(Set<Variable> equivalentVariables) {
        List<Variable> variableList = equivalentVariables.toList();
        List<P2<Variable, Variable>> variablePairs = variableList.zip(variableList.tail());
        return variablePairs
                .map((F<P2<Variable, Variable>, Function>) pair -> termFactory.getFunctionEQ(pair._1(), pair._2()));
    }


    /**
     * HEURISTIC for split the left join sub atoms.
     *
     * Left: left of the SECOND data atom.
     * Right: the rest.
     *
     * Will cause problem if the left part is supposed to have multiple data/composite atoms.
     * However, if the 3-arity of the LJ is respected and a JOIN is used for the left part, no problem.
     *
     */
    private P2<List<Function>, List<Function>> splitLeftJoinSubAtoms(Function leftJoinMetaAtom) {
        List<Function> subAtoms = List.iterableList(
                (java.util.List<Function>) (java.util.List<?>) leftJoinMetaAtom.getTerms());
        return splitLeftJoinSubAtoms(subAtoms);
    }

    @Override
    public P2<List<Function>, List<Function>> splitLeftJoinSubAtoms(List<Function> ljSubAtoms) {

        // TODO: make it static (performance improvement).
        F<Function, Boolean> isNotDataOrCompositeAtomFct = atom -> !(datalogTools.isDataOrLeftJoinOrJoinAtom(atom));

        /**
         * Left: left of the first data/composite atom (usually empty).
         *
         * The first data/composite atom is thus the first element of the right list.
         */
        P2<List<Function>, List<Function>> firstDataAtomSplit = ljSubAtoms.span(isNotDataOrCompositeAtomFct);
        Function firstDataAtom = firstDataAtomSplit._2().head();

        /**
         * Left: left of the second data/composite atom starting just after the first data/composite atom.
         *
         * Right: right part of the left join (includes the joining conditions, no problem).
         */
        P2<List<Function>, List<Function>> secondDataAtomSplit = firstDataAtomSplit._2().tail().span(
                isNotDataOrCompositeAtomFct);

        List<Function> leftAtoms = firstDataAtomSplit._1().snoc(firstDataAtom).append(secondDataAtomSplit._1());
        List<Function> rightAtoms = secondDataAtomSplit._2();

        return P.p(leftAtoms, rightAtoms);
    }

    /**
     * Only boolean atoms are pushable.
     */
    private static P2<List<Function>,List<Function>> splitPushableAtoms(List<Function> atoms) {
        List<Function> nonPushableAtoms = atoms
                .filter(atom -> !isPushable(atom));
        List<Function> pushableAtoms = atoms.filter(PullOutEqualityNormalizerImpl::isPushable);

        return P.p(nonPushableAtoms, pushableAtoms);
    }

    /**
     * Only boolean atoms are pushable.
     */
    private static boolean isPushable(Function atom) {
        if (atom.isOperation())
            return true;
        return false;
    }
}
