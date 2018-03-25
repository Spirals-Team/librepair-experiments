package uk.co.ohmgeek.jdcraw.operations;

import uk.co.ohmgeek.jdcraw.RAWOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan on 30/06/17.
 */
public class SetWBCustomOperation implements RAWOperation {
    private double mul0;
    private double mul1;
    private double mul2;
    private double mul3;

    /**
     * Create an operation to create a custom white balance setting.
     * @param mul0 : multiplier 0
     * @param mul1 : multiplier 1
     * @param mul2 : multiplier 2
     * @param mul3 : multiplier 3
     */
    public SetWBCustomOperation(double mul0, double mul1, double mul2, double mul3) throws NegativeWhiteBalanceException {
        this.mul0 = mul0;
        this.mul1 = mul1;
        this.mul2 = mul2;
        this.mul3 = mul3;

        if(mul0 < 0 | mul1 < 0 | mul2 < 0 | mul3 < 0) {
            throw new NegativeWhiteBalanceException();
        }


    }

    /**
     * Get the argument list passed to the dcraw argument list
     * @return arguments : the argument list of strings
     */
    public List<String> getArgumentList() {
        List<String> arguments = new ArrayList<String>();

        // add the -r flag
        arguments.add("-r");

        // then add the multiplier values
        arguments.add(String.valueOf(mul0));
        arguments.add(String.valueOf(mul1));
        arguments.add(String.valueOf(mul2));
        arguments.add(String.valueOf(mul3));

        return arguments;
    }
}
