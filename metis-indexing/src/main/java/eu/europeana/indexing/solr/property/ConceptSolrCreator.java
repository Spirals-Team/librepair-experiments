package eu.europeana.indexing.solr.property;

import org.apache.solr.common.SolrInputDocument;
import eu.europeana.corelib.definitions.edm.entity.Concept;
import eu.europeana.indexing.solr.EdmLabel;

/**
 * Property Solr Creator for 'skos:Concept' tags.
 * 
 * @author Yorgos.Mamakis@ europeana.eu
 *
 */
public class ConceptSolrCreator implements PropertySolrCreator<Concept> {

  @Override
  public void addToDocument(SolrInputDocument doc, Concept concept) {
    SolrPropertyUtils.addValue(doc, EdmLabel.SKOS_CONCEPT, concept.getAbout());
    SolrPropertyUtils.addValues(doc, EdmLabel.CC_SKOS_PREF_LABEL, concept.getPrefLabel());
    SolrPropertyUtils.addValues(doc, EdmLabel.CC_SKOS_ALT_LABEL, concept.getAltLabel());
    SolrPropertyUtils.addValues(doc, EdmLabel.CC_SKOS_HIDDEN_LABEL, concept.getHiddenLabel());
    SolrPropertyUtils.addValues(doc, EdmLabel.CC_SKOS_NOTE, concept.getNote());
    SolrPropertyUtils.addValues(doc, EdmLabel.CC_SKOS_NOTATIONS, concept.getNotation());
    SolrPropertyUtils.addValues(doc, EdmLabel.CC_SKOS_BROADER, concept.getBroader());
    SolrPropertyUtils.addValues(doc, EdmLabel.CC_SKOS_BROADMATCH, concept.getBroadMatch());
    SolrPropertyUtils.addValues(doc, EdmLabel.CC_SKOS_NARROWER, concept.getNarrower());
    SolrPropertyUtils.addValues(doc, EdmLabel.CC_SKOS_NARROWMATCH, concept.getNarrowMatch());
    SolrPropertyUtils.addValues(doc, EdmLabel.CC_SKOS_RELATED, concept.getRelated());
    SolrPropertyUtils.addValues(doc, EdmLabel.CC_SKOS_RELATEDMATCH, concept.getRelatedMatch());
    SolrPropertyUtils.addValues(doc, EdmLabel.CC_SKOS_EXACTMATCH, concept.getExactMatch());
    SolrPropertyUtils.addValues(doc, EdmLabel.CC_SKOS_CLOSEMATCH, concept.getCloseMatch());
    SolrPropertyUtils.addValues(doc, EdmLabel.CC_SKOS_INSCHEME, concept.getInScheme());
  }
}
