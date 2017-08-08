package org.corfudb.runtime.collections;

import static org.assertj.core.api.Assertions.assertThat;
import com.google.common.reflect.TypeToken;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.assertj.core.data.MapEntry;
import org.corfudb.runtime.view.AbstractViewTest;
import org.junit.Test;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class CorfuTableTest extends AbstractViewTest {

    @RequiredArgsConstructor
    enum StringIndexers implements CorfuTable.IndexSpecification<String, String, String, String> {
        BY_VALUE((k,v) -> Collections.singleton(v)),
        BY_FIRST_LETTER((k, v) -> Collections.singleton(Character.toString(v.charAt(0))))
        ;

        @Getter
        final CorfuTable.IndexFunction<String, String, String> indexFunction;

        @Getter
        final CorfuTable.ProjectionFunction<String, String, String, String> projectionFunction
                = (i, s) -> s.map(entry -> entry.getValue());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void canReadFromEachIndex() {
        CorfuTable<String, String, StringIndexers, String>
                corfuTable = getDefaultRuntime().getObjectsView().build()
                    .setTypeToken(
                            new TypeToken<CorfuTable<String, String, StringIndexers, String>>() {})
                    .setArguments(StringIndexers.class)
                    .setStreamName("test")
                    .open();

        corfuTable.put("k1", "a");
        corfuTable.put("k2", "ab");
        corfuTable.put("k3", "b");

        assertThat(corfuTable.getByIndex(StringIndexers.BY_FIRST_LETTER, "a"))
                .containsExactly("a", "ab");

        assertThat(corfuTable.getByIndex(StringIndexers.BY_VALUE, "ab"))
                .containsExactly("ab");
    }

    @Test
    @SuppressWarnings("unchecked")
    public void emptyIndexesReturnEmptyValues() {
        CorfuTable<String, String, StringIndexers, String>
                corfuTable = getDefaultRuntime().getObjectsView().build()
                .setTypeToken(
                        new TypeToken<CorfuTable<String, String, StringIndexers, String>>() {})
                .setArguments(StringIndexers.class)
                .setStreamName("test")
                .open();


        assertThat(corfuTable.getByIndex(StringIndexers.BY_FIRST_LETTER, "a"))
                .isEmpty();

        assertThat(corfuTable.getByIndex(StringIndexers.BY_VALUE, "ab"))
                .isEmpty();
    }


    @Test
    @SuppressWarnings("unchecked")
    public void canReadWithoutIndexes() {
        CorfuTable<String, String, CorfuTable.NoSecondaryIndex, Void>
                corfuTable = getDefaultRuntime().getObjectsView().build()
                .setTypeToken(
                        new TypeToken<CorfuTable<String, String,
                                CorfuTable.NoSecondaryIndex, Void>>() {})
                .setArguments(StringIndexers.class)
                .setStreamName("test")
                .open();

        corfuTable.put("k1", "a");
        corfuTable.put("k2", "ab");
        corfuTable.put("k3", "b");

        assertThat(corfuTable)
                .containsExactly(MapEntry.entry("k1", "a"),
                                 MapEntry.entry("k2", "ab"),
                                 MapEntry.entry("k3", "b"));
    }
}
