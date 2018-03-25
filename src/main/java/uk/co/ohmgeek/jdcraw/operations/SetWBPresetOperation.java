package uk.co.ohmgeek.jdcraw.operations;


import uk.co.ohmgeek.jdcraw.RAWOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * An operation to set white balance using a specific preset
 * Created by ryan on 30/06/17.
 */
public class SetWBPresetOperation implements RAWOperation {

    private WBPreset preset;

    /**
     * Set the white balance to a preset
     *
     * @param preset : the preset
     */
    public SetWBPresetOperation(WBPreset preset) {

        this.preset = preset;
    }

    /**
     * Get the argument list for DCRaw to use
     *
     * @return args : the argument list (of string).
     */
    public List<String> getArgumentList() {
        List<String> args = new ArrayList<String>();
        if (preset == WBPreset.CAMERA) {
            args.add("-w"); // use camera white balance
        }
        return args;
    }
}
