package uk.co.ohmgeek.jdcraw;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This builds the string to be parsed by the ProcessBuilder. It combines options, filename and the renderer path.
 * Created by ryan on 29/06/17.
 */
public class OptionStringBuilder {
    /**
     * Build up the process string of an object.
     * @param renderedArgs : the arguments regarding the property
     * @param file : the filename to render
     * @return args : the full argument string to build a process with.
     */
    // todo make this less dependent on dcraw (so we can choose what renderer to use, in case a better one is available)
    public static List<String> build(List<String> renderedArgs, File file) {
        List<String> args = new ArrayList<String>();

        // first, add the name of the application
        args.add("dcraw"); //todo make this more portable (based on system)
        // then, add the list of arguments for the application (from options)
        args.addAll(renderedArgs);

        // finally, add the file to render properly.
        args.add(file.getPath());
        // return the list.
        return args;
    }
}
