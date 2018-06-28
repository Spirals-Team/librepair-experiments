package  ru.job4j.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;





/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */

public class AccountBank {
    public double value;
    private String requisites;

    public AccountBank() {

    }

    public AccountBank(double value, String requisites) {
        this.value = value;
        this.requisites = requisites;
    }

    public String getRequisites() {
        return requisites;
    }

    /**
     * Метод, снимаюищий и начисляющий отправляемые деньги на счетах отправителя и получателя.
     * @param destination - ак получателя денег
     * @param amount сумма перечисляемых денег
     * @return false, если денег на счету отправителя меньше отпраляемой суммы, или получателя не существует в банковской системе.
     */
     boolean transfer(AccountBank destination, double amount) {
        boolean success = false;
        if (amount > 0 && amount < this.value && destination != null) {
            success = true;
            this.value -= amount;
            destination.value += amount;
        }
        return success;
    }
}
