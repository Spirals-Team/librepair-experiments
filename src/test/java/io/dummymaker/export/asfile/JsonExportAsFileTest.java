package io.dummymaker.export.asfile;

import io.dummymaker.data.Dummy;
import io.dummymaker.export.Format;
import io.dummymaker.export.IExporter;
import io.dummymaker.export.impl.JsonExporter;
import io.dummymaker.export.naming.IStrategy;
import io.dummymaker.export.naming.Strategies;
import io.dummymaker.export.validators.JsonValidator;
import io.dummymaker.factory.IProduceFactory;
import io.dummymaker.factory.impl.GenProduceFactory;
import org.junit.Test;

import java.util.List;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 20.08.2017
 */
public class JsonExportAsFileTest extends FileExportAssert {

    private final IProduceFactory produceFactory = new GenProduceFactory();

    private final JsonValidator validation = new JsonValidator();

    private final Format format = Format.JSON;

    public JsonExportAsFileTest() {
        super(new JsonExporter().withPretty().withPath(null).withStrategy(null).withPath("            "),
                new JsonValidator(), Format.JSON, 5, 14);
    }

    @Test
    public void exportListOfDummiesWithNamingStrategy() throws Exception {
        final IStrategy strategy = Strategies.UNDERSCORED_UPPER_CASE.getStrategy();

        final List<Dummy> dummy = produceFactory.produce(Dummy.class, 2);
        final String filename = Dummy.class.getSimpleName() + format.getExtension();
        final IExporter exporter = new JsonExporter().withStrategy(strategy).withPretty().withStrategy(null);

        final boolean exportResult = exporter.export(dummy);
        assertTrue(exportResult);
        setFilenameToBeRemoved(filename);

        final String dummyAsString = readDummyFromFile(filename);
        assertNotNull(dummyAsString);
        assertFalse(dummyAsString.isEmpty());

        final String[] jsonArray = dummyAsString.split("\n");
        assertEquals(14, jsonArray.length);

        validation.isTwoDummiesValidWithNamingStrategy(jsonArray, strategy);
    }
}
