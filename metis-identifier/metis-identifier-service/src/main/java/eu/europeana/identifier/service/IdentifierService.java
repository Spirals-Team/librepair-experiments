package eu.europeana.identifier.service;

import eu.europeana.corelib.utils.EuropeanaUriUtils;
import eu.europeana.identifier.service.utils.IdentifierNormalizer;
import org.jibx.runtime.JiBXException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Class used to generate a Europeana identifier
 * Created by ymamakis on 2/8/16.
 */
@Service
public class IdentifierService {

    @Autowired
    private IdentifierNormalizer normalizer;

    /**
     * Generate identifier based on a collection identifier and a record identifier
     * @param collectionId The collection identifier
     * @param recordId The record identifier
     * @return A valid Europeana identifier
     */
    public String generateIdentifier(String collectionId, String recordId){
        return EuropeanaUriUtils.createSanitizedEuropeanaId(collectionId,recordId);
    }

    /**
     * Modify a single record identifiers to correctly link them
     * @param record The record to modify
     * @return The correctly linked record
     * @throws JiBXException 
     */
    public String fixIdentifiers(String record) throws JiBXException{
        return normalizer.normalize(record);
    }

    /**
     * Modify a record to append the correct Europeana Identifier
     * @param records The list of records to modify the identifiers
     * @return The correctly linked records
     * @throws JiBXException 
     */
    public List<String> fixIdentifiers(List<String> records)throws JiBXException{
        List<String> fixed = new ArrayList<>();
        for(String record:records){
            fixed.add(fixIdentifiers(record));
        }
        return fixed;
    }

}
