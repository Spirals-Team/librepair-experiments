package io.descoped.client.api.test.example;

import io.descoped.client.api.builder.APIClient;
import org.junit.Test;

public class StatBankTest {

    @Test
    public void should_retrieve_domestic_animals_from_StatBank() {
         APIClient.builder()
                .worker("retrieveDomesticAmimals")
                    .operation(HttpPostDomesticAnimalsOperation.class)
                    .outcome(HttpPostDomesticAnimalsOutcome.class)
                    .done()
                .execute()
        ;

    }
}


