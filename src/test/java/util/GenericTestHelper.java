package util;

import com.meteoradesigner.HasId;
import com.meteoradesigner.repository.GenericAbstractCrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static java.lang.System.lineSeparator;
import static org.junit.Assert.assertEquals;

/**
 * This @code{GenericTestHelper} class represents test helper behaviour.
 */
public class GenericTestHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericTestHelper.class);

    /**
     * Tests @code{saveOne, findOne} methods.
     */
    public static <E extends HasId, ID extends Serializable> void cruTest(
            Collection<E[]> data,
            GenericAbstractCrudRepository<E, ID> repository,
            BiConsumer<E[], GenericAbstractCrudRepository<E, ID>> consumer
    ) {
        data.forEach(ar -> consumer.accept(ar, repository));
    }

    /**
     * Tests @code{deleteOneTest} method.
     */
    public static <E extends HasId, ID extends Serializable> void deleteTest(
            Collection<E[]> data,
            Map<String, GenericAbstractCrudRepository<?, ID>> repository,
            BiConsumer<E[], Map<String, GenericAbstractCrudRepository<?, ID>>> consumer
    ) {
        data.forEach(ar -> consumer.accept(ar, repository));
    }

    /**
     * Tests @code{findAll} method.
     */
    public static <E extends HasId, ID extends Serializable> void findAll(
            List<E> expected,
            GenericAbstractCrudRepository<E, ID> jpaRepository) {
        List<E> actual = jpaRepository.findAll();
        LOGGER.info(String.format("Actual=%s", actual), actual);
        assertEquals(
                String.format("FindAll failed:" + lineSeparator() + " expected=%s" +
                                      lineSeparator() + " actual= %s",
                              expected, actual),
                expected,
                actual);
    }
}