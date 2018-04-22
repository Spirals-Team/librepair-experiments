package model.jobs;

import model.Rental;
import model.states.rental.ConfirmedByTheTenantST;
import org.quartz.*;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class VerifyOwnerConfirmationJob  implements Job{

    public static final String RENTAL = "rental";

    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        Rental rental = (Rental) dataMap.get(RENTAL);

        try {
            Thread.sleep(9000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(rental.getState().getClass().equals(ConfirmedByTheTenantST.class)) {
            rental.getState().ownerUserConfirmated(rental);
        }

        dataMap.put(RENTAL, rental);
    }
}
