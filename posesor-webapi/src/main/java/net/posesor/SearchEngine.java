package net.posesor;

import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.val;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Utility class to query source stream with provided hint.
 */
public final class SearchEngine {

    // utility class doesn't require construction.
    private SearchEngine() {
    }

    /**
     * Filters source stream where extracted source item name matches provided {@code hint}.
     *
     * @param source              source stream of data which need to be reduced to
     * @param hintedValue converted from source data to strim used to match with hint
     * @param hint                used to query source items 'similar' to hint
     * @param <TPayload>          type of source items.
     * @return filtered items where extracted name is similar to provided hint.
     */
    public static <TPayload> Stream<TPayload> filterToSearchDtoModel(
            Stream<TPayload> source,
            Function<TPayload, String> hintedValue,
            String hint) {

        val loweredHint = Optional.of(hint).orElse("").toLowerCase();
        val hintLength = loweredHint.length();

        return source
                .map((TPayload it) -> {
                    val itName = hintedValue.apply(it);
                    if (Strings.isNullOrEmpty(itName)) return Optional.<PayloadAndWeight<TPayload>>empty();

                    int itWeight;
                    val loweredName = itName.toLowerCase();

                    if (loweredName.length() >= hintLength && loweredHint.equals(loweredName.substring(0, hintLength)))
                        itWeight = 1;
                    else if (loweredName.contains(" " + loweredHint)) itWeight = 2;
                    else if (loweredName.contains(loweredHint)) itWeight = 3;
                    else itWeight = 0;

                    return itWeight == 0
                            ? Optional.<PayloadAndWeight<TPayload>>empty()
                            : Optional.of(new PayloadAndWeight<>(it, itWeight));
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted(Comparator.comparingInt(it -> it.weight))
                .map(it -> it.payload);
    }

    @AllArgsConstructor
    private static class PayloadAndWeight<T> {
        private T payload;
        private int weight;
    }
}
