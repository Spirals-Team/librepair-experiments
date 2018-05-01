import model.CurrentAccount;
import model.builder.CurrentAccountBuilder;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.plaf.ColorUIResource;

/**
 * Created by mariano on 01/04/18.
 */
public class CurrentAccountTest {

    @Test
    public void currentAccountConstructorTest(){
        CurrentAccount account = new CurrentAccountBuilder().setId("1").build();
        Assert.assertTrue(account.getId().equals("1") && account.getCredits().equals(0) && account.getClass().equals(CurrentAccount.class));
    }
    @Test
    public void chargeTenCreditsTest(){
        CurrentAccount currentAccount = new CurrentAccountBuilder().setId("11").build();
        Integer tenCredits = 10;
        currentAccount.addCredit(tenCredits);
        Assert.assertTrue(currentAccount.getCredits() == tenCredits);
    }


    @Test
    public void substractFiveCreditsToTenCreditsTest(){
        CurrentAccount currentAccount = new CurrentAccountBuilder().setId("22").build();
        Integer tenCredits = 10;
        Integer fiveCredits = 5;
        currentAccount.addCredit(tenCredits);
        currentAccount.subtractCredit(fiveCredits);
        Assert.assertTrue(currentAccount.getCredits() == fiveCredits);
    }


}
