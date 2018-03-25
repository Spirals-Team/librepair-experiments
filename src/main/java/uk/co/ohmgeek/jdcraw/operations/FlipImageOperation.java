package uk.co.ohmgeek.jdcraw.operations;

import uk.co.ohmgeek.jdcraw.RAWOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryan on 29/06/17.
 */
public class FlipImageOperation implements RAWOperation {
    private FlipAngleEnum angle;

    /**
     * Create an operation for flipping an image
     * @param angle : The angle to flip the image by. It's specified as an Enum in FlipAngleEnum.
     */
    public FlipImageOperation(FlipAngleEnum angle) {
        this.angle = angle;
    }

    /**
     * This method gets the argument list for DCRaw based on the operation.
     * @return args : Argument list (of strings)
     */
    public List<String> getArgumentList() {
        List<String> args = new ArrayList<String>();
        args.add("-t");
        args.add(String.valueOf(angle.degrees()));
        return args;
    }
}
