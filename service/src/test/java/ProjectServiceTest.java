import com.gdc.aerodev.service.specific.ProjectService;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.Rule;
import org.junit.Test;

public class ProjectServiceTest {

    private String tableName = "project_test";

    @Rule
    PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("project-service"));

    @Test
    public void testCreateProject(){

    }

    private ProjectService getService(){
        return new ProjectService(db.getTestDatabase(), tableName);
    }
}
