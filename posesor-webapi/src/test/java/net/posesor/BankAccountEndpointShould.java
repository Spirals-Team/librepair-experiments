package net.posesor;

import lombok.val;
import net.posesor.statements.BankAccountEndpoint;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.stream.Stream;

/**
 * Unit tests for BankAccountController
 */
public final class BankAccountEndpointShould {

    /**
     * Checks in simple scenario if controller returns ekspected
     * list if bank accounts based shouldFail their similarity with 'hint'
     * <p>
     * Logically search should return:
     * * the first are bank accounts names where they are starting with the hint
     * * next are bank accounts where hint the begin of some words in the account
     * * the last group contains bank account where provided hint exists inside of one of words.
     *
     * @throws Exception ignored
     */
    @Test
    public void returnExpectedItems() throws Exception {
        val item1 = new BankAccountEndpoint.SearchDtoModel("term without similarity", "subject1");
        val item2 = new BankAccountEndpoint
                .SearchDtoModel("has hint inside", "subject2");
        val item3 = new BankAccountEndpoint
                .SearchDtoModel("hint is the first word", "subject3");
        val item4 = new BankAccountEndpoint
                .SearchDtoModel("a-hint is inside a word", "subject3");
        val endpoint = new BankAccountEndpoint(Stream.of(item1, item2, item3, item4));

        val result = endpoint.searchAccountName("hint").getBody();

        Assertions.assertThat(result).containsExactly(item3, item2, item4);
    }
}