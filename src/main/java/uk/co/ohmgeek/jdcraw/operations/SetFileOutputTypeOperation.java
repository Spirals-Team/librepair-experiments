package uk.co.ohmgeek.jdcraw.operations;

import uk.co.ohmgeek.jdcraw.RAWOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan on 29/06/17.
 */
public class SetFileOutputTypeOperation implements RAWOperation {
    private FileOutputTypeEnum type;

    /**
     * Set File Output Type, being either Tiff, or PGM/PPM/PAM (abbreviated as PNM)
     * @param t : the file type to use
     */
    public SetFileOutputTypeOperation(FileOutputTypeEnum t) {
        this.type = t;
    }

    /**
     * Get argument list for dcraw.
     * @return args : The argument list (of string).
     */
    public List<String> getArgumentList() {
        List<String> args = new ArrayList<String>();
        if(type == FileOutputTypeEnum.TIFF) {
            args.add("-T"); //only add the Tiff argument if we want tiff. otherwise, default to PNM.
        }
        return args;
    }
}
