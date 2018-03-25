package uk.co.ohmgeek.jdcraw;

import org.apache.commons.io.FilenameUtils;
import uk.co.ohmgeek.jdcraw.operations.SetFileOutputTypeOperation;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The uk.co.ohmgeek.jdcraw.DCRawManager Class manages DCRaw operations.
 * It's the key handler used to interface with the library.
 * Created by ryan on 29/06/17.
 */
public class DCRawManager {
    private File file;
    private List<RAWOperation> operationList;
    public DCRawManager(File fileToProcess) {
        operationList = new ArrayList<RAWOperation>();

        this.file = fileToProcess;

    }

    /**
     * Process the image, with operations specified.
     * @return destinationPath : the path to the destination as a string
     * @throws IOException : error executing dcraw
     */
    public String process() throws IOException {

        ProcessBuilder dcrawProcessBuilder = new ProcessBuilder();
        dcrawProcessBuilder.directory(this.file.getParentFile());

        dcrawProcessBuilder.command(getCMDToExecute());
        System.out.println(getCMDToExecute());
        // start running the render process
        Process dcrawProcess = dcrawProcessBuilder.start();

        //todo take this and create an exception if there is no error stream. This is a DCRaw Error.
        //        BufferedReader reader = new BufferedReader(new InputStreamReader(dcrawProcess.getErrorStream()));
//        System.out.println(reader.readLine());
        return getDestination(); //return the path of the destination.
    }

    public List<String> getCMDToExecute() {
        final String EXECUTABLE_CMD = "dcraw"; //todo change this to be customisable (OS based)

        List<String> fullCMD = new ArrayList<String>();

        //add the executable as the first instruction.
        fullCMD.add(EXECUTABLE_CMD);

        // now go through all operation list commands, adding args.
        for(RAWOperation op : operationList) {
            fullCMD.addAll(op.getArgumentList());
        }

        //finally add the file to read
        fullCMD.add(this.file.getAbsolutePath());
        // return the constructed command
        return fullCMD;
    }

    /**
     * Get the destination path of the image.
     * @return path : string.
     */
    private String getDestination() {
        // first, get the path without extension
        File cwd = new File(".");

        String outputFilename = FilenameUtils.removeExtension(this.file.getPath());



        boolean isTiff = false;
        // look through operations, and look at property of SetFileOutputTypeOperation instance (if there is one).
        //todo optimise for multiple arguments that are the same.
        for (RAWOperation op: operationList) {
            isTiff = op instanceof SetFileOutputTypeOperation && op.getArgumentList().contains("-T");
        }

        //choose the correct extension
        if(isTiff) {
            outputFilename = outputFilename.concat(".tiff");
        } else {
            outputFilename = outputFilename.concat(".ppm");
        }

        // now get the result.
        return outputFilename;
    }
    /**
     * Add an operation to be made by DCRaw
     * @param op : an instance of RawOperation to be added to the operation list.
     */
    public void addOperation(RAWOperation op) {
        operationList.add(op);
    }
}
