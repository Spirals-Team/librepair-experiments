package net.posesor.payments;

import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.stream.Stream;

public class PaymentTitleControllerShould {
    @Test
    public void returnExpectedValues() {
        val endpoint = new PaymentTitleController(Stream.of("term without similarity", "has hint inside", "hint is the first word"));

        val result = endpoint.searchAccountName("hint").getBody();

        Assertions.assertThat(result).containsExactly(
                new PaymentTitleController.SearchDtoModel("hint is the first word"),
                new PaymentTitleController.SearchDtoModel("has hint inside"));
    }
}