package eu.europeana.enrichment.service.wikidata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.xml.bind.JAXBException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import eu.europeana.enrichment.service.EntityConverterUtils;
import eu.europeana.enrichment.service.exception.WikidataAccessException;
import eu.europeana.enrichment.service.exception.ZohoAccessException;

/**
 * Test utils class for entity converter.
 * 
 * @author GrafR
 *
 */
// @Ignore
public class EntityConverterUtilsTest { 

  final String WIKIDATA_TEST_OUTPUT_FILE = "test.out";
  final String TEST_WIKIDATA_ORGANIZATION_ID = "193563";
  final String TEST_WIKIDATA_URL = "http://www.wikidata.org/entity/Q" + TEST_WIKIDATA_ORGANIZATION_ID;
  final String TEST_ACRONYM = "BNF";
  final String TEST_COUNTRY = "FR";  
  final String TEST_LABEL_FR = "BnF";  
  final String TEST_LABEL_FR2 = "BnF2";  
  final String TEST_LABEL_EN = "British library";
  final String TEST_LABEL_IT = "Bologna library";
  
  Map<String, List<String>> prefLabel = null;
  Map<String, List<String>> addPrefLabel = null;
  Map<String, List<String>> altLabel = null;
  
  EntityConverterUtils entityConverterUtils = new EntityConverterUtils();
  
  public EntityConverterUtils getEntityConverterUtils() {
    return entityConverterUtils;
  }

  final Logger LOGGER = LoggerFactory.getLogger(getClass());

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {}

  private void init() {
    prefLabel = new HashMap<String, List<String>>();
    List<String> prefLabelValues = new ArrayList<String>();
    prefLabelValues.add(TEST_LABEL_FR);
    prefLabel.put(Locale.FRENCH.getLanguage(), prefLabelValues);

    addPrefLabel = new HashMap<String, List<String>>();
    List<String> addPrefLabelValues = new ArrayList<String>();
    addPrefLabelValues.add(TEST_LABEL_EN);
    addPrefLabel.put(Locale.ENGLISH.getLanguage(), addPrefLabelValues);
    List<String> addPrefLabelValues2 = new ArrayList<String>();
    addPrefLabelValues2.add(TEST_LABEL_FR2);
    addPrefLabel.put(Locale.FRENCH.getLanguage(), addPrefLabelValues2);
    
    altLabel = new HashMap<String, List<String>>();
    List<String> addAltLabelValues = new ArrayList<String>();
    addAltLabelValues.add(TEST_LABEL_FR);
    altLabel.put(Locale.FRENCH.getLanguage(), addAltLabelValues);
    List<String> addAltLabelValues2 = new ArrayList<String>();
    addAltLabelValues2.add(TEST_LABEL_IT);
    altLabel.put(Locale.ITALIAN.getLanguage(), addAltLabelValues2);    
  }
  
  @Test
  public void mergeTest() throws WikidataAccessException, ZohoAccessException,
      ParseException, JAXBException, IOException {

    init();
    
    /** check two equal prefLabels */
    boolean compareEqualMaps = getEntityConverterUtils().isEqualLanguageListMap(prefLabel, prefLabel);
    assertTrue(compareEqualMaps == true);
    
    /** merge two equal prefLabels */
    Map<String, List<String>> mergedFromEqualMap = getEntityConverterUtils().mergeLanguageListMap(
        prefLabel, prefLabel);
    assertNotNull(mergedFromEqualMap);
    
    /** check unequal prefLabels */
    boolean compareDifferentMaps = getEntityConverterUtils().isEqualLanguageListMap(prefLabel, addPrefLabel);
    assertTrue(compareDifferentMaps == false);

    /** extract not equal values */
    Map<String, List<String>> diffMap = getEntityConverterUtils().extractValuesNotIncludedInBaseMap(
        prefLabel, addPrefLabel);
    assertNotNull(diffMap);
    assertTrue(diffMap.size() == 2);
    assertEquals(diffMap.get(Locale.ENGLISH.getLanguage()).get(0), TEST_LABEL_EN);
    assertEquals(diffMap.get(Locale.FRENCH.getLanguage()).get(0), TEST_LABEL_FR2);

    /** add additional value for duplicate check */
    List<String> listValues = diffMap.get(Locale.FRENCH.getLanguage());
    listValues.add(TEST_LABEL_FR);
    diffMap.put(Locale.FRENCH.getLanguage(), listValues);
    
    /** merge unequal prefLabels to an altLabel and check resulting altLabel */
    Map<String, List<String>> mergedMap = getEntityConverterUtils().mergeLanguageListMap(altLabel, diffMap);
    assertNotNull(mergedMap);
    assertTrue(mergedMap.size() == 3);
    assertEquals(mergedMap.get(Locale.ENGLISH.getLanguage()).get(0), TEST_LABEL_EN);
    assertTrue(mergedMap.get(Locale.FRENCH.getLanguage()).contains(TEST_LABEL_FR));
    assertTrue(mergedMap.get(Locale.FRENCH.getLanguage()).contains(TEST_LABEL_FR2));
    assertEquals(mergedMap.get(Locale.ITALIAN.getLanguage()).get(0), TEST_LABEL_IT);
    
    /** merge unequal string arrays and remove duplicates */
    String[] base = {"a","b","c"};
    String[] add = {"d","b"};    
    String[] mergedArray = getEntityConverterUtils().concatenateStringArrays(base, add);
    assertNotNull(mergedArray);
    assertTrue(mergedArray.length == 4);    
  }

}
