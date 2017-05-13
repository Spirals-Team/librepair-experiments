package com.enioka.jqm.tools;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.enioka.jqm.api.JobRequest;
import com.enioka.jqm.test.helpers.CreationTools;
import com.enioka.jqm.test.helpers.TestHelpers;

public class SpringTest extends JqmBaseTest
{
    @Before
    public void prepare()
    {
        FileUtils.deleteQuietly(new File("./target/TEST.db"));
    }

    /**
     * No specific CL - isolated launch, using normal runners. Still, all launches work on the same database.
     */
    @Test
    public void testSimpleSingleLaunch()
    {
        CreationTools.createDatabaseProp("jdbc/spring_ds", "org.h2.Driver", "jdbc:h2:./target/TEST.db;DB_CLOSE_ON_EXIT=FALSE", "sa", "sa",
                cnx, "SELECT 1", null, true);
        CreationTools.createJobDef(null, true, "com.enioka.jqm.test.spring1.Application", null,
                "jqm-tests/jqm-test-spring-1/target/test.jar", TestHelpers.qVip, -1, "TestSpring1", null, null, null, null, null, false,
                cnx, null);

        addAndStartEngine();

        // First job creates the database, so let it finish (test artifact).
        JobRequest.create("TestSpring1", null).submit();
        TestHelpers.waitFor(1, 10000, cnx);

        JobRequest.create("TestSpring1", null).submit();
        JobRequest.create("TestSpring1", null).submit();

        TestHelpers.waitFor(3, 20000, cnx);

        Assert.assertEquals(3, TestHelpers.getOkCount(cnx));
        Assert.assertEquals(0, TestHelpers.getNonOkCount(cnx));
    }

    /**
     * Using the Spring runner and an XML job def. Actually uses test-spring-2.
     */
    @Test
    public void testSpringRunner()
    {
        try
        {
            XmlJobDefParser.parse("target/payloads/jqm-test-xml/xmlspring.xml", cnx);
        }
        catch (JqmEngineException e)
        {
            jqmlogger.error("could not parse XML", e);
            Assert.fail();
        }

        JobRequest.create("Job1", null).addParameter("key1", "value1").submit();
        addAndStartEngine();

        TestHelpers.waitFor(1, 10000, cnx);
        JobRequest.create("Job2", null).addParameter("key1", "value1").submit();

        TestHelpers.waitFor(2, 10000, cnx);
        Assert.assertEquals(2, TestHelpers.getOkCount(cnx));
        Assert.assertEquals(0, TestHelpers.getNonOkCount(cnx));
    }
}
