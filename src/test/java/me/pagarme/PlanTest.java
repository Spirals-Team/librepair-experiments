package me.pagarme;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import me.pagar.model.Plan;
import me.pagarme.factory.PlanFactory;

public class PlanTest extends BaseTest {

    private PlanFactory planFactory = new PlanFactory();

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testCreatePlan() throws Throwable {
        Plan plan = planFactory.create();
        plan.save();
        Assert.assertNotNull(plan.getId());
        Assert.assertEquals(Integer.valueOf(PlanFactory.DEFAULT_AMOUNT), plan.getAmount());
        Assert.assertEquals(PlanFactory.DEFAULT_NAME, plan.getName());
        Assert.assertEquals(Integer.valueOf(PlanFactory.DEFAULT_DAYS), plan.getDays());
        Assert.assertEquals(Integer.valueOf(PlanFactory.DEFAULT_CHARGES), plan.getCharges());
        Assert.assertEquals(Integer.valueOf(PlanFactory.DEFAULT_INSTALLMENTS), plan.getInstallments());
        Assert.assertEquals(Integer.valueOf(PlanFactory.DEFAULT_TRIAL_DAYS), plan.getTrialDays());
        Assert.assertEquals(Integer.valueOf(PlanFactory.DEFAULT_INVOICE_REMINDER), plan.getInvoiceReminder());
        Assert.assertNotNull(plan.getCreatedAt());
    }

    @Test
    public void testFindPlan() throws Throwable {
        Plan plan = planFactory.create();
        plan.save();

        Plan searchPlan = new Plan();
        searchPlan.find(plan.getId());

        Assert.assertEquals(searchPlan.getId(), plan.getId());
    }

    /*
    @Test
    public void testListPlan() throws Throwable {
        Plan plan1 = planFactory.create();
        plan1.save();

        Plan plan2 = planFactory.create();
        plan2.save();

        //There's a delay between the response and the db insertion...
        Thread.sleep(2000);
        Collection<Plan> plans = new Plan().findCollection(10, 1);
        Assert.assertEquals(2, plans.size());
    }
    */

}