package edu.stanford.bmir.protege.web.server.hierarchy;


import edu.stanford.bmir.protege.web.server.inject.project.RootOntology;
import edu.stanford.bmir.protege.web.shared.inject.ProjectSingleton;
import org.semanticweb.owlapi.model.*;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 23-Jan-2007<br><br>
 */
@ProjectSingleton
public class OWLDataPropertyHierarchyProvider extends AbstractOWLPropertyHierarchyProvider<OWLDataRange, OWLDataPropertyExpression, OWLDataProperty> {

    @Inject
    public OWLDataPropertyHierarchyProvider(@RootOntology OWLOntology rootOntology, @DataPropertyHierarchyRoot OWLDataProperty root) {
        super(rootOntology, root);
    }


    protected Set<OWLDataProperty> getPropertiesReferencedInChange(List<? extends OWLOntologyChange> changes) {
        Set<OWLDataProperty> result = new HashSet<OWLDataProperty>();
        for (OWLOntologyChange change : changes) {
            if (change.isAxiomChange()) {
                for (OWLEntity entity : change.getSignature()) {
                    if (entity.isOWLDataProperty()) {
                        result.add(entity.asOWLDataProperty());
                    }
                }
            }
        }
        return result;
    }


    /**
     * Gets the relevant properties in the specified ontology that are contained
     * within the property hierarchy.  For example, for an object property hierarchy
     * this would constitute the set of referenced object properties in the specified
     * ontology.
     * @param ont The ontology
     */
    protected Set<? extends OWLDataProperty> getReferencedProperties(OWLOntology ont) {
        return ont.getDataPropertiesInSignature();
    }


    protected boolean containsReference(OWLOntology ont, OWLDataProperty prop) {
        return ont.containsDataPropertyInSignature(prop.getIRI());
    }


    protected Set<? extends OWLSubPropertyAxiom<OWLDataPropertyExpression>> getSubPropertyAxiomForRHS(
            OWLDataProperty prop, OWLOntology ont) {
        return ont.getDataSubPropertyAxiomsForSuperProperty(prop);
    }

}
