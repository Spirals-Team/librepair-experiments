package com.enioka.jqm.tools;

import org.junit.Assert;
import org.junit.Test;

import com.enioka.jqm.api.JobRequest;
import com.enioka.jqm.api.JqmClientFactory;
import com.enioka.jqm.model.JobDef;
import com.enioka.jqm.test.helpers.CreationTools;
import com.enioka.jqm.test.helpers.TestHelpers;

public class ExternalTest extends JqmBaseTest
{
    @Test
    public void testExternalLaunch() throws Exception
    {
        int jdId = CreationTools.createJobDef(null, true, "App", null, "jqm-tests/jqm-test-datetimemaven/target/test.jar", TestHelpers.qVip,
                42, "MarsuApplication", null, "Franquin", "ModuleMachin", "other", "other", true, cnx);
        JobDef.setExternal(cnx, jdId);
        cnx.commit();
        JobRequest.create("MarsuApplication", "TestUser").submit();

        addAndStartEngine();
        TestHelpers.waitFor(1, 20000, cnx);

        Assert.assertEquals(1, TestHelpers.getOkCount(cnx));
        Assert.assertEquals(0, TestHelpers.getNonOkCount(cnx));
    }

    @Test
    public void testExternalKill() throws Exception
    {
        Helpers.setSingleParam("internalPollingPeriodMs", "100", cnx);

        int i = JqmSimpleTest.create(cnx, "pyl.KillMeNot").setExternal().expectNonOk(0).expectOk(0).run(this);
        TestHelpers.waitForRunning(1, 20000, cnx);

        JqmClientFactory.getClient().killJob(i);
        TestHelpers.waitFor(1, 20000, cnx);
        Assert.assertEquals(0, TestHelpers.getOkCount(cnx));
        Assert.assertEquals(1, TestHelpers.getNonOkCount(cnx));
    }
}
