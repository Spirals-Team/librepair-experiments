package acceptancetests;

import acceptancetests.thens.Then;
import acceptancetests.whens.ANewProductIsAdded;
import acceptancetests.whens.StockIsAdded;
import acceptancetests.whens.Whens;
import com.googlecode.yatspec.junit.SpecResultListener;
import com.googlecode.yatspec.junit.WithCustomResultListeners;
import com.googlecode.yatspec.plugin.sequencediagram.SequenceDiagramGenerator;
import com.googlecode.yatspec.plugin.sequencediagram.SvgWrapper;
import com.googlecode.yatspec.rendering.html.DontHighlightRenderer;
import com.googlecode.yatspec.rendering.html.HtmlResultRenderer;
import com.googlecode.yatspec.state.givenwhenthen.TestState;
import hanfak.shopofhan.application.crosscutting.ProductRepository;
import hanfak.shopofhan.infrastructure.properties.Settings;
import hanfak.shopofhan.wiring.ShopOfHan;
import org.junit.After;
import org.junit.Before;
import testinfrastructure.TestStockRepository;
import testinfrastructure.TestWiring;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static testinfrastructure.TestWiring.ENVIRONMENT;

public abstract class AcceptanceTest extends TestState implements WithCustomResultListeners {
    public static final String APPLICATION_NAME = "Shop Of Han app";
    private final ShopOfHan shopOfHan = new ShopOfHan();
    protected final acceptancetests.TestState testState = new acceptancetests.TestState();

    protected final Whens weMake = new Whens(testState); // TODO naming of these
    protected final ANewProductIsAdded aNewProductIsAdded = new ANewProductIsAdded(testState);
    protected final StockIsAdded stockIsAdded = new StockIsAdded(testState);

    protected final Then then = new Then(testState, capturedInputAndOutputs);
    private static final TestWiring TEST_WIRING = new TestWiring();
    public static final ProductRepository productRepository = TEST_WIRING.productRepository();
    public static final TestStockRepository stockRepository = new TestStockRepository();

    @Before
    public void setUp() throws Exception {
        resetDatabaseContents();
        shopOfHan.startWebServer(loadTestSettings(), new TestWiring());
    }

    @After
    public void tearDown() throws Exception {
        shopOfHan.stopWebServer();
        capturedInputAndOutputs.add("Sequence Diagram", generateSequenceDiagram());
    }

    @Override
    public Iterable<SpecResultListener> getResultListeners() throws Exception {
        return singletonList(new HtmlResultRenderer()
                .withCustomHeaderContent(SequenceDiagramGenerator.getHeaderContentForModalWindows())
                .withCustomRenderer(SvgWrapper.class, new DontHighlightRenderer<>()));
    }

    //Need to show theBodyIs of response and queries of request in diagrams
    private SvgWrapper generateSequenceDiagram() {
        return new SequenceDiagramGenerator().generateSequenceDiagram(new ByNamingConventionMessageProducer().messages(capturedInputAndOutputs));
    }

    private Settings loadTestSettings() {
        return TEST_WIRING.settings();
    }

// TODO reset primings for all tests in @Before
    public void resetDatabaseContents() throws IOException {
        //https://stackoverflow.com/questions/10929369/how-to-execute-multiple-sql-statements-from-java
        // get each command, store as  separate sql stmt and use batch command in isDeleted
        List<String> sqlCommands = Arrays.stream(readSqlPriming().split("\n\n")).collect(Collectors.toList());
        executeSQL(sqlCommands.get(0));
        executeSQL(sqlCommands.get(1));
        executeSQL(sqlCommands.get(2));
        executeSQL(sqlCommands.get(3));
        executeSQL(sqlCommands.get(4));
        executeSQL(sqlCommands.get(5));
    }

    private String readSqlPriming() throws IOException {
        //TODO how to read file from path
        return new String(Files.readAllBytes(Paths.get("ShopOfHanSQL/commands_01.sql")));
    }

    @SuppressWarnings("ConstantConditions")
    private void executeSQL(String sql) {
        if (ENVIRONMENT.equals("test")) {
            return;
        }
        try (Connection connection = TEST_WIRING.databaseConnectionManager().getDBConnection().get();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            if (statement.execute()) {
                throw new IllegalArgumentException(statement.toString());
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
