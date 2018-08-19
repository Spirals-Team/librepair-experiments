package it.unibz.inf.ontop.spec.mapping.transformer.impl;

/*
 * #%L
 * ontop-reformulation-core
 * %%
 * Copyright (C) 2009 - 2014 Free University of Bozen-Bolzano
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import it.unibz.inf.ontop.datalog.CQIE;
import it.unibz.inf.ontop.dbschema.*;
import it.unibz.inf.ontop.exception.OntopInternalBugException;
import it.unibz.inf.ontop.exception.UnknownDatatypeException;
import it.unibz.inf.ontop.model.term.*;
import it.unibz.inf.ontop.model.term.functionsymbol.BNodePredicate;
import it.unibz.inf.ontop.model.term.functionsymbol.Predicate;
import it.unibz.inf.ontop.model.term.functionsymbol.URITemplatePredicate;
import it.unibz.inf.ontop.model.term.impl.FunctionalTermImpl;
import it.unibz.inf.ontop.model.term.impl.ImmutabilityTools;
import it.unibz.inf.ontop.model.type.RDFDatatype;
import it.unibz.inf.ontop.model.type.TermType;
import it.unibz.inf.ontop.model.type.TypeFactory;
import it.unibz.inf.ontop.model.type.impl.TermTypeInferenceTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.IntStream;


public class MappingDataTypeCompletion {

    private final DBMetadata metadata;
    private final boolean defaultDatatypeInferred;

    private static final Logger log = LoggerFactory.getLogger(MappingDataTypeCompletion.class);
    private final Relation2Predicate relation2Predicate;
    private final TermFactory termFactory;
    private final TypeFactory typeFactory;
    private final TermTypeInferenceTools termTypeInferenceTools;
    private final ImmutabilityTools immutabilityTools;

    /**
     * Constructs a new mapping data type resolution.
     * If no datatype is defined, then we use database metadata for obtaining the table column definition as the
     * default data-type.
     * //TODO: rewrite in a Datalog-free fashion
     * @param metadata The database metadata.
     * @param relation2Predicate
     * @param termFactory
     * @param typeFactory
     * @param termTypeInferenceTools
     * @param immutabilityTools
     */
    public MappingDataTypeCompletion(DBMetadata metadata,
                                     boolean defaultDatatypeInferred, Relation2Predicate relation2Predicate,
                                     TermFactory termFactory, TypeFactory typeFactory,
                                     TermTypeInferenceTools termTypeInferenceTools,
                                     ImmutabilityTools immutabilityTools) {
        this.metadata = metadata;
        this.defaultDatatypeInferred = defaultDatatypeInferred;
        this.relation2Predicate = relation2Predicate;
        this.termFactory = termFactory;
        this.typeFactory = typeFactory;
        this.termTypeInferenceTools = termTypeInferenceTools;
        this.immutabilityTools = immutabilityTools;
    }

    public void insertDataTyping(CQIE rule) throws UnknownDatatypeException {
        Function atom = rule.getHead();
        Predicate predicate = atom.getFunctionSymbol();
        if (predicate.getArity() == 2) { // we check both for data and object property
            Term term = atom.getTerm(1); // the second argument only
            Map<String, List<IndexedPosition>> termOccurenceIndex = createIndex(rule.getBody());
            // Infer variable datatypes
            insertVariableDataTyping(term, atom, 1, termOccurenceIndex);
            // Infer operation datatypes from variable datatypes
            insertOperationDatatyping(term, atom, 1);
        }
    }

    /**
     * This method wraps the variable that holds data property values with a data type predicate.
     * It will replace the variable with a new function symbol and update the rule atom.
     * However, if the users already defined the data-type in the mapping, this method simply accepts the function symbol.
     */
    private void insertVariableDataTyping(Term term, Function atom, int position,
                                          Map<String, List<IndexedPosition>> termOccurenceIndex) throws UnknownDatatypeException {
        Predicate predicate = atom.getFunctionSymbol();

        if (term instanceof Function) {
            Function function = (Function) term;
            Predicate functionSymbol = function.getFunctionSymbol();
            if (function.isDataTypeFunction() ||
                    (functionSymbol instanceof URITemplatePredicate)
                    || (functionSymbol instanceof BNodePredicate)) {
                // NO-OP for already assigned datatypes, or object properties, or bnodes
            }
            else if (function.isOperation()) {
                for (int i = 0; i < function.getArity(); i++) {
                    insertVariableDataTyping(function.getTerm(i), function, i, termOccurenceIndex);
                }
            } else {
                throw new IllegalArgumentException("Unsupported subtype of: " + Function.class.getSimpleName());
            }
        } else if (term instanceof Variable) {
            Variable variable = (Variable) term;
            Term newTerm;
            RDFDatatype type = getDataType(termOccurenceIndex, variable);
            newTerm = termFactory.getTypedTerm(variable, type);
            log.info("Datatype "+type+" for the value " + variable + " of the property " + predicate + " has been " +
                    "inferred " +
                    "from the database");
            atom.setTerm(position, newTerm);
        } else if (term instanceof ValueConstant) {
            Term newTerm = termFactory.getTypedTerm(term, ((ValueConstant) term).getType());
            atom.setTerm(position, newTerm);
        } else {
            throw new IllegalArgumentException("Unsupported subtype of: " + Term.class.getSimpleName());
        }
    }

   /**
    * Following r2rml standard we do not infer the datatype for operation but we return the default value string
    */
    private void insertOperationDatatyping(Term term, Function atom, int position) throws UnknownDatatypeException {

        ImmutableTerm immutableTerm = immutabilityTools.convertIntoImmutableTerm(term);

        if (immutableTerm instanceof ImmutableFunctionalTerm) {
            ImmutableFunctionalTerm castTerm = (ImmutableFunctionalTerm) immutableTerm;
            if (castTerm.isOperation()) {

                Optional<TermType> inferredType = termTypeInferenceTools.inferType(castTerm);
                if(inferredType.isPresent()){
                    // delete explicit datatypes of the operands
                    deleteExplicitTypes(term, atom, position);
                    // insert the datatype of the evaluated operation
                    atom.setTerm(
                            position,
                            termFactory.getTypedTerm(
                                    term,
                                    // TODO: refactor this cast
                                    (RDFDatatype) inferredType.get()
                            ));
                }
                else
                    {


                    if (defaultDatatypeInferred) {

                        atom.setTerm(position, termFactory.getTypedTerm(term, typeFactory.getXsdStringDatatype()));
                    } else {
                        throw new UnknownDatatypeException("Impossible to determine the expected datatype for the operation " + castTerm + "\n" +
                                "Possible solutions: \n" +
                                "- Add an explicit datatype in the mapping \n" +
                                "- Add in the .properties file the setting: ontop.inferDefaultDatatype = true\n" +
                                " and we will infer the default datatype (xsd:string)"
                        );
                    }
                }
            }
        }
    }

    private void deleteExplicitTypes(Term term, Function atom, int position) {
        if(term instanceof Function){
            Function castTerm = (Function) term;
            IntStream.range(0, castTerm.getArity())
                    .forEach(i -> deleteExplicitTypes(
                            castTerm.getTerm(i),
                            castTerm,
                            i
                    ));
            if(castTerm.isDataTypeFunction()){
                atom.setTerm(position, castTerm.getTerm(0));
            }
        }
    }

    /**
     * returns COL_TYPE for one of the datatype ids
     *
     * @param termOccurenceIndex
     * @param variable
     * @return
     */
    private RDFDatatype getDataType(Map<String, List<IndexedPosition>> termOccurenceIndex, Variable variable) throws UnknownDatatypeException {


        List<IndexedPosition> list = termOccurenceIndex.get(variable.getName());
        if (list == null)
            throw new UnboundTargetVariableException(variable);

        // ROMAN (10 Oct 2015): this assumes the first occurrence is a database relation!
        //                      AND THAT THERE ARE NO CONSTANTS IN ARGUMENTS!
        IndexedPosition ip = list.get(0);

        RelationID tableId = relation2Predicate.createRelationFromPredicateName(metadata.getQuotedIDFactory(), ip.atom
                .getFunctionSymbol());
        RelationDefinition td = metadata.getRelation(tableId);
        
        
        Attribute attribute = td.getAttribute(ip.pos);
        Optional<RDFDatatype>  type;
        //we want to assign the default value or throw an exception when the type of the attribute is missing (case of view)
        if (attribute.getType() == 0){

            type = Optional.empty();
        }
        else{
            type = metadata.getTermType(attribute)
                    // TODO: refactor this (unsafe)!!!
                    .map(t -> (RDFDatatype) t);
        }

        if(defaultDatatypeInferred)
            return type.orElseGet(typeFactory::getXsdStringDatatype) ;
        else {
            return type.orElseThrow(() -> new UnknownDatatypeException("Impossible to determine the expected datatype for the column "+ variable+"\n" +
                    "Possible solutions: \n" +
                    "- Add an explicit datatype in the mapping \n" +
                    "- Add in the .properties file the setting: ontop.inferDefaultDatatype = true\n" +
                    " and we will infer the default datatype (xsd:string)"));

        }

    }

	private static class IndexedPosition {
        final Function atom;
        final int pos;

        IndexedPosition(Function atom, int pos) {
            this.atom = atom;
            this.pos = pos;
        }
    }

    private static Map<String, List<IndexedPosition>> createIndex(List<Function> body) {
        Map<String, List<IndexedPosition>> termOccurenceIndex = new HashMap<>();
        for (Function a : body) {
            List<Term> terms = a.getTerms();
            int i = 1; // position index
            for (Term t : terms) {
                if (t instanceof Variable) {
                    Variable var = (Variable) t;
                    List<IndexedPosition> aux = termOccurenceIndex.get(var.getName());
                    if (aux == null)
                        aux = new LinkedList<>();
                    aux.add(new IndexedPosition(a, i));
                    termOccurenceIndex.put(var.getName(), aux);
                    
                } else if (t instanceof FunctionalTermImpl) {
                    // NO-OP
                } else if (t instanceof ValueConstant) {
                    // NO-OP
                } else if (t instanceof URIConstant) {
                    // NO-OP
                }
                // fabad (4 Oct 2017) Quick fix if there are constants in arguments.
                // Increase i in all cases. If there are terms that are not variables
                // and i is not incremented then indexedPosition.pos contains a wrong
                // index that may points to terms that are not variables.
                i++; // increase the position index for the next variable
            }
        }
        return termOccurenceIndex;
    }

    /**
     * Should have been detected earlier!
     */
    private static class UnboundTargetVariableException extends OntopInternalBugException {

        protected UnboundTargetVariableException(Variable variable) {
            super("Unknown variable in the head of a mapping:" + variable + ". Should have been detected earlier !");
        }
    }

}

