package model.jobs;

import model.Rental;
import model.states.rental.ConfirmedByTheOwnerST;
import org.quartz.*;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class VerifyTenantConfirmationJob implements Job{

    public static final String RENTAL = "rental";

    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        Rental rental = (Rental) dataMap.get(RENTAL);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(rental.getBeginRentalTime()==null &&
                rental.getState().getClass().equals(ConfirmedByTheOwnerST.class)) {
            rental.getState().tenantUserConfirmated(rental);
        }

        dataMap.put(RENTAL, rental);
        //context.setResult(rental.getState());
    }
}
