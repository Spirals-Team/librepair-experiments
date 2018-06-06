package de.adorsys.multibanking.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document
public class KeyStoreEntity {

    @Id
    private String id;

    private String name;

    private String type;

    private byte[] keystore;

    private Map<String, KeyEntryAttributesEntity> entries;
}
