package uk.co.ohmgeek.jdcraw.operations;

import uk.co.ohmgeek.jdcraw.RAWOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Set the brightness level (custom)
 * Created by ryan on 29/06/17.
 */
public class SetBrightnessOperation implements RAWOperation {
    private double brightnessLevel;

    /**
     * SetColorSpaceOperation to set the brightness
     * @param brightnessLevel : the brightness level
     */
    public SetBrightnessOperation(double brightnessLevel) throws NegativeBrightnessException {
        if(brightnessLevel < 0) {
            throw new NegativeBrightnessException();
        }
        this.brightnessLevel = brightnessLevel;
    }

    /**
     * Return an argument list for brightness (for dcraw to use)
     * @return args : An argument list (of string)
     */
    public List<String> getArgumentList() {
        List<String> args = new ArrayList<String>();
        args.add("-b"); // indicate brightness
        args.add(String.valueOf(brightnessLevel)); // then add the brightness level
        return args;
    }
}
